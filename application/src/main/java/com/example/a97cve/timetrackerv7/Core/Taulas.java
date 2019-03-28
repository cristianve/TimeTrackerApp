package com.example.a97cve.timetrackerv7.Core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Class Taulas
 * Clase que extiende de elemento
 * encarga de recoger los datos
 * de subproyectos, tareas y intervalos.
 * y devolverselo al elemento que les llama
 */
public class Taulas extends Elemento {

    private Proyecto actividadPadre;
    //COLLECTIONS
    private Collection<Proyecto> arrelProyectos = new ArrayList<Proyecto>();
    //INFORME DETALLADO
    private ArrayList taulaSubProyectes = new ArrayList();
    private ArrayList taulaTasques = new ArrayList();
    private ArrayList taulaInterval = new ArrayList();
    //COLLECTIONS
    private Collection<Proyecto> lSubProyectos
            = new ArrayList<Proyecto>();
    private Collection<Tarea> listaTareas = new ArrayList<Tarea>();
    //Formato Horas
    private DateFormat hf = new SimpleDateFormat("HH:mm:ss");
    //Formato DIAS
    private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
    //HORAS PERIODE STRING I DATE
    private Date iniciInforme = null;
    private String sInici;
    private Date finInforme = null;
    private String sFin;
    private Date actual = new Date();
    private String sDate;

    Taulas(final Date ini, final Date fin, final Proyecto p) {
        this.iniciInforme = ini;
        this.finInforme = fin;
        this.actual = new Date();
        this.actividadPadre =  p;
        this.getActividadesRecursivo(actividadPadre);
        this.addSubProyecte();
        this.addTareas();
        this.addIntervals();
    }

    public String getIntervalo(final long duracion) {
        final int segPorMin = 60;
        final int msPorSeg = 1000;
        long tiempoIntervalo = duracion;
        long segundos = tiempoIntervalo / msPorSeg;
        long minutos = segundos / segPorMin;
        long horas = minutos / segPorMin;
        segundos = segundos % segPorMin;
        minutos = minutos % segPorMin;
        String res = Long.toString(horas) + ":"
                + Long.toString(minutos)
                + ":" + Long.toString(segundos);
        return res;
    }

    /**.
     * Funcion encargada de recorer recursivamente
     * el arbol de proyectos y guardar Proyectos
     * y tareas
     */
    public void getActividadesRecursivo(final Actividad p) {
        Object[] lista;
        Collection<Tarea> subTareas = p.getSubTareas();
        if (subTareas != null) {
            lista = subTareas.toArray();
            for (int i = 0; i < subTareas.size(); i++) {
                Tarea t = (Tarea) lista[i];
                int contador = 1 + i;
                String primerIndice = p.getNum();
                String segundoIndice = "" + contador++;
                t.setNum("" + primerIndice);
                listaTareas.add(t);
                getActividadesRecursivo(t);
            }
        }

        if (p.getClass() == Proyecto.class) {
            Proyecto proyecto = (Proyecto) p;
            Collection<Proyecto> subProyectos
                    = proyecto.getSubProyectos();
            lista = subProyectos.toArray();
            for (int i = 0; i < subProyectos.size(); i++) {
                Proyecto pr = (Proyecto) lista[i];
                int contador = 1 + i;
                if (proyecto.getNum() != null) {
                    String primerIndice = proyecto.getNum();
                    String segundoIndice = "" + contador++;
                    pr.setNum("" + primerIndice + "." + ""
                            + segundoIndice);
                }
                lSubProyectos.add(pr);
                getActividadesRecursivo(pr);
            }
        }
    }

    @Override
    void accept(final Formato f) {
        f.visit(this);
    }

    /**.
     * Carga todos los Intervalos
     * para guardarlos en taulaIntervalos
     */
    private void addIntervals() {
        //FILA 1 HEADER
        ArrayList<String> fila1 = new ArrayList<String>();
        fila1.add("No.(sub) Projecte");
        fila1.add("Tasca");
        fila1.add("Interval");
        fila1.add("Data d'inici");
        fila1.add("Data final");
        fila1.add("Durada");
        taulaInterval.add(fila1);
        //1-AJUSTAS LA DATA DE INICIO A DATA PERIODE INI
        // -- si fin intervalo>= inicio Informe => inicio intervalo
        //2-COMPARAS QUE INICI<FINAL
        //CARGAR DATOS INTERVALOS
        Collection<Intervalo> datosIntervalos2
                = new ArrayList<Intervalo>();
        for (int i = 0; i < listaTareas.size(); i++) {
            Object[] lista =  listaTareas.toArray();
            Tarea t1 = (Tarea) lista[i];
            datosIntervalos2 = t1.getIntervalos();
            int contador = 1;
            //datosIntervalos = new ArrayList();
            for (int j = 0; j < datosIntervalos2.size(); j++) {
                Collection datosIntervalos = new ArrayList();
                Object[] aux = datosIntervalos2.toArray();
                Intervalo i1 = (Intervalo)aux[j];
                String init = null;
                String finit = null;
                long duracion = 0;
                //+ 500
                if (i1.getFechaInicio().getTime()
                        >= finInforme.getTime()) {
                    //FUERA IZQUIERDA
                    System.out.println("FUERA DERECHA");
                } else if ((i1.getFechaInicio().getTime()
                        <= finInforme.getTime())
                        && (iniciInforme.getTime()
                        > i1.getFechaFin().getTime())) {
                    //FUERA IZQUIERDA
                    System.out.println("FUERA IZQUIERDA");
                } else if ((iniciInforme.getTime()
                        >= i1.getFechaInicio()
                        .getTime())
                        && (finInforme.getTime()
                        >= i1.getFechaFin().
                        getTime())) {
                    System.out.println(
                            "FUERA IZQUIERDA"
                                    + "I DENTRO INFORME"
                                    + t1.getNombre());
                    //FUERA IZQUIERDA I DENTRO INFORME
                    init = dff.format(
                            iniciInforme.getTime());
                    finit = i1
                            .getFechaFinInterForm();
                    duracion = i1.getFechaFin().getTime()
                            - iniciInforme.getTime();
                } else if ((iniciInforme.getTime()
                        >= i1.getFechaInicio()
                        .getTime())
                        && (finInforme.getTime()
                        <= i1.getFechaFin()
                        .getTime())) {
                    System.out.println("FUERA IZQUIERDA "
                            + "I FUERA DERECHA "
                            + t1.getNombre());
                    //FUERA IZQUIERDA I FUERA DERECHA
                    init = dff.format(iniciInforme
                            .getTime());
                    finit = dff.format(finInforme
                            .getTime());
                    duracion = finInforme.getTime()
                            - iniciInforme
                            .getTime();
                } else if ((iniciInforme.getTime()
                        <= i1.getFechaInicio()
                        .getTime())
                        && (finInforme.getTime()
                        >= i1.getFechaFin()
                        .getTime())) {
                    System.out.println("DENTRO"
                            + t1.getNombre());
                    //DENTRO
                    init = i1.getFechaInicioInterForm();
                    finit = i1.getFechaFinInterForm();
                    duracion = i1.getFechaFin()
                            .getTime()
                            - i1.getFechaInicio()
                            .getTime();
                } else if ((iniciInforme.getTime()
                        <= i1.getFechaInicio()
                        .getTime())
                        && (finInforme.getTime()
                        < i1.getFechaFin().getTime())) {
                    System.out.println("DENTRO Y FUERA "
                            + "DERECHA "
                            + t1.getNombre());
                    //DENTRO Y FUERA DERECHA
                    init = i1.getFechaInicioInterForm();
                    finit = dff.format(finInforme
                            .getTime());
                    duracion = finInforme.getTime()
                            - i1.getFechaInicio()
                            .getTime();
                }
                if ((init != null) && (finit != null)) {
                    /// GUARDAR DATOS
                    datosIntervalos.add(t1.getNum());
                    datosIntervalos.add(t1
                            .getNombre()); //TASC
                    if (t1.getIntervalos().size() > 1) {
                        datosIntervalos
                                .add(""
                                        + contador++);
                        //INTERVALO
                    } else {
                        datosIntervalos.add(1);
                        //INTERVALO
                    }

                    //INICIO i1.getInicio()
                    // FECHA INICIO TAREA
                    datosIntervalos.add(init);
                    //FIN	i2.getFinal()
                    datosIntervalos.add(finit);
                    datosIntervalos
                            .add(this
                                    .getIntervalo(
                                            duracion));
                    taulaInterval.add(datosIntervalos);
                }
            }
        }
    }

    /**.
     * Carga todos las Tareas
     * para guardarlos en taulaTareas
     */
    private void addTareas() {
        ArrayList fila1 = new ArrayList();
        fila1.add("No.(sub) Projecte");
        fila1.add("Tasca");
        fila1.add("Data d'inici");
        fila1.add("Data final");
        fila1.add("Temps total");
        taulaTasques.add(fila1);
        //CARGAR DATOS TASQUES
        Collection datosTasques = new ArrayList();
        for (int i = 0; i < listaTareas.size(); i++) {
            Tarea t1 = (Tarea) listaTareas
                    .toArray()[i];
            datosTasques = new ArrayList();
            datosTasques.add(t1.getNum());
            datosTasques.add(t1
                    .getNombre());
            datosTasques.add(t1
                    .getTiempoInicioFormatado());
            datosTasques.add(t1
                    .getTiempoFinalFormatado());
            datosTasques
                    .add(t1.getIntervalo());
            taulaTasques.add(datosTasques);
        }
    }

    /**.
     * Carga todos las SubProyectos
     * para guardarlos en taulaTareas
     */
    private void addSubProyecte() {
        //FILA 1 HEADER
        ArrayList<String> fila1 = new ArrayList<String>();
        fila1.add("No.");
        fila1.add("Projecte");
        fila1.add("Data d'inici");
        fila1.add("Data final");
        fila1.add("Temps total");
        taulaSubProyectes.add(fila1);
        //INICIAMOS LISTASUBRPOYECTOS LISTATAREAS
        //this.getActividadesRecursivo(actividadPadre); //CONSTRUCTOR
        //TRATAMOS LOS DATOS DE LA LISTASUBPROYECTOS
        Collection datosSubProyecte = new ArrayList();
        int contador = 1;
        for (int i = 0; i < lSubProyectos.size(); i++) {
            Proyecto p1 = (Proyecto) lSubProyectos.toArray()[i];
            int indiceProjecto = 0;
            if (!arrelProyectos.contains(p1)) {
                if (p1.getNum().length() > 1) {
                    String init = null;
                    String finit = null;
                    long duracion = 0;
                    if ((iniciInforme.getTime()
                            >= p1.getTiempoInicio()
                            .getTime())
                            && (finInforme.getTime()
                            >= p1.getTiempoFinal()
                            .getTime())) {
                        //FUERA IZQ I DENTRO INFORME
                        init = dff.format(iniciInforme
                                .getTime());
                        finit = p1.getTiempoFinalFormatado();
                        duracion = p1.getTiempoFinal()
                                .getTime()
                                - iniciInforme
                                .getTime();
                    } else if ((iniciInforme.getTime()
                            >= p1.getTiempoInicio()
                            .getTime())
                            && (finInforme.getTime()
                            <= p1.getTiempoFinal()
                            .getTime())) {
                        //FUERA IZQ I FUERA DERECH
                        init = dff.format(
                                iniciInforme
                                        .getTime());
                        finit = dff.format(
                                finInforme
                                        .getTime());
                        duracion = finInforme.getTime()
                                - iniciInforme
                                .getTime();
                    } else if ((iniciInforme.getTime()
                            <= p1.getTiempoInicio()
                            .getTime())
                            && (finInforme.getTime()
                            >= p1.getTiempoFinal()
                            .getTime())) {
                        init = p1.getTiempoInicioFormatado(); //DENTRO
                        finit = p1.getTiempoFinalFormatado();
                        duracion = p1.getTiempoFinal().getTime() - p1.getTiempoInicio().getTime();
                    } else if ((iniciInforme.getTime()
                            <= p1.getTiempoInicio()
                            .getTime())
                            && (finInforme
                            .getTime()
                            <= p1.getTiempoFinal()
                            .getTime())) {
                        init = p1.getTiempoInicioFormatado(); //DENTRO Y FUERA DERECHA
                        finit = dff.format(finInforme.getTime());
                        duracion = finInforme.getTime() - p1.getTiempoInicio().getTime();
                    }
                    if ((init != null) && (finit != null)) {
                        datosSubProyecte
                                = new ArrayList();
                        datosSubProyecte
                                .add(p1.getNum());
                        datosSubProyecte
                                .add(p1.getNombre());
                        datosSubProyecte.add(init);
                        datosSubProyecte.add(finit);
                        datosSubProyecte
                                .add(this
                                        .getIntervalo(
                                                duracion));
                        taulaSubProyectes.add(datosSubProyecte);
                    }
                    //FIN
                }
            }
        }
    }




    public ArrayList getTaulaSubProyectes() {
        return taulaSubProyectes;
    }


    public void setTaulaSubProyectes(final ArrayList tSubProyectes) {
        this.taulaSubProyectes = tSubProyectes;
    }

    public ArrayList getTaulaTasques() {
        return taulaTasques;
    }


    public void setTaulaTasques(final ArrayList tTasques) {
        this.taulaTasques = tTasques;
    }


    public ArrayList getTaulaInterval() {
        return taulaInterval;
    }

    public void setTaulaInterval(final ArrayList tInterval) {
        this.taulaInterval = tInterval;
    }



}
