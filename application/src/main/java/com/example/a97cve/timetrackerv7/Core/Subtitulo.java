package com.example.a97cve.timetrackerv7.Core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Class Subtitulo
 * Clase que extiende de elemento
 * encarga de recoger los datos
 * de Periode y Proyecto y pasarlos
 * a el elemeto que le llama
 */
public class Subtitulo extends Elemento {
    private Proyecto actividadPadre;

    private Collection<Proyecto> arrelProyectos = new ArrayList<Proyecto>();

    private ArrayList taulaPeriode = new ArrayList(); //(0,0);//(3,3)
    private ArrayList taulaProjecte = new ArrayList(); //(0,5);//(n,5)
    private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");

    private Date iniciInforme = null;
    private Date finInforme = null;
    private Date actual = new Date();

    Subtitulo(final Date ini, final Date fin, final Proyecto p) {
        this.iniciInforme = ini;
        this.finInforme = fin;
        this.actual = new Date();
        this.actividadPadre =  p;
        this.addPeriode();
        this.addProjectes();
    }

    @Override
    void accept(final Formato f) {
        f.visit(this);
    }
    /**.
     * Carga todos los Projectos padre
     * para guardarlos en taulaProjectos
     */
    private void addProjectes() {
        //FILA 1 HEADER
        ArrayList<String> fila1 = new ArrayList<String>();
        fila1.add("No.");
        fila1.add("Projecte");
        fila1.add("Data d'inici");
        fila1.add("Data final");
        fila1.add("Temps total");
        taulaProjecte.add(fila1);
        Proyecto proyecto = (Proyecto) actividadPadre;
        Collection<Proyecto> subProyectos = proyecto.getSubProyectos();
        Object[] lista = subProyectos.toArray();
        Collection datosProjecte = new ArrayList();
        int contador = 1;
        for (int i = 0; i < proyecto.getSubProyectos().size(); i++) {
            Proyecto p1 = (Proyecto) lista[i];
            arrelProyectos.add(p1);
            datosProjecte = new ArrayList();
            String aux = "" + contador++;
            datosProjecte.add(aux);
            p1.setNum(aux);
            datosProjecte.add(p1.getNombre());
            datosProjecte.add(p1.getTiempoInicioFormatado());
            datosProjecte.add(p1.getTiempoFinalFormatado());
            datosProjecte.add(p1.getIntervalo());
            taulaProjecte.add(datosProjecte);
        }

    }
    /**.
     * Carga todos los Periodos
     * introducidos por el Cliente
     */
    private void addPeriode() {
        //FILA 1 HEADER
        ArrayList fila1 = new ArrayList<String>();
        fila1.add("Data");
        fila1.add(" ");
        //FILA 2 INIT PERIODE
        ArrayList fila2 = new ArrayList();
        fila2.add("Desde");
        String diaInici = dff.format(iniciInforme.getTime());
        fila2.add(diaInici);
        //FILA 3 FIN PERIODE
        ArrayList fila3 = new ArrayList();
        fila3.add("Fins a");
        String diaFin = dff.format(finInforme.getTime());
        fila3.add(diaFin);
        //FILA 4 ACTUAL PERIODE
        ArrayList fila4 = new ArrayList();
        fila4.add("Data de generacio de l'informe");
        String diaActual = dff.format(actual.getTime());
        fila4.add(diaActual);
        //AnADIMOS LAS FILAS = 4
        taulaPeriode.add(fila1);
        taulaPeriode.add(fila2);
        taulaPeriode.add(fila3);
        taulaPeriode.add(fila4);
    }



    public ArrayList getTaulaPeriode() {
        return taulaPeriode;
    }



    public void setTaulaPeriode(final ArrayList tPeriode) {
        this.taulaPeriode = tPeriode;
    }



    public ArrayList getTaulaProjecte() {
        return taulaProjecte;
    }



    public void setTaulaProjecte(final ArrayList tProjecte) {
        this.taulaProjecte = tProjecte;
    }

}
