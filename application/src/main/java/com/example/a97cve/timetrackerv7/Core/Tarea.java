package com.example.a97cve.timetrackerv7.Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * Class Tarea
 * Extiende de la classe Actividad
 * Gestiona una Collecion de intervalos
 * Los cuales se pueden inicializar y finalizar
 * y recoger sus respectivas fechas.
 */
public class Tarea extends Actividad {
    private Collection<Intervalo> intervalos;
    private Intervalo intervaloActual;
    private boolean activo = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(Tarea.class);

    /**
     * Realizar las comprobaciones basicas de todas
     * las tareas: que tenga nombre y descripcion
     * Salta excepcion assert si no es correcto.
     * @return Correcto
     */

    private Boolean invariant() {
        assert nombre != null : "Tarea no tiene nombre";
        assert descripcion != null : "Tarea no tiene descripcion";
        return true;
    }

    /**
     * Devuelve el tiempo total del proyecto (suma de los intervalos
     * de cada una de las tareas y subproyectos que contiene).
     * @param n nombre Tarea
     * @param desc descripcion
     */
    public Tarea(final String n, final String desc) {
        assert n != null : "nombre incorrecto";
        assert desc != null : "descripcion incorrecta";
        intervalos = new ArrayList<Intervalo>();
        setNombre(n);
        setDescripcion(desc);
        LOGGER.info("[" + super.nombre
                + "]Tarea.tarea()");
        //CONSTRUCOR TAREA
        assert invariant();
    }

    /**
     * Crea un nuevo intervalo ý empieza a ser notificado.
     */
    public void iniciarIntervalo() {
        //LOGGER
        activo = true;
        LOGGER.info("[" + this.getNombre() + "]Tarea.IniciarIntervalo");
        intervaloActual = new Intervalo();
        intervalos.add(intervaloActual);
        assert intervaloActual != null : " Intervalo no inicializado";
        assert this.getTiempoInicio() != null : "Tiempo inicio no valido";
        assert intervalos.size() > 0 : "Intervalo no se añade bien a la lista";
    }

    public Tarea crearTarea(final String nombre, final String descripcion) {
        Tarea tarea = new Tarea(nombre, descripcion);
        subTareas.add(tarea);
        return tarea;
    }

    /**
     * Llama a la funcion deleteObserver para que
     * el intervalo deje de ser notificado.
     */
    public void acabarIntervalo() {
        activo = false;
        assert intervaloActual != null : "Intervalo Actual no existe";
        //LOGGER
        LOGGER.info("[" + this.getNombre() + "] Tarea.acabarIntervalo()");
        Reloj.getInstancia().deleteObserver(intervaloActual);
        intervaloActual = null;
        assert intervaloActual == null : "Intervalo Actual no cambiado";
    }


    /**
     * Obtiene de manera recursiva el tiempo
     * incicial del proyecto.
     * @return tiempoInicial: Date
     * con la fecha mas antigua obtenida
     */
    public Date getTiempoInicio() {
        //LOGGER
        //LOGGER.info("[" + this.nombre + "] Tarea.getTiempoInicio()");
        Date tiempoInicial = null;
        Date tiempoInicial2;
        assert tiempoInicial == null
                : "Inicialitzacion de variables de tiempo incorrecta";
        if (intervalos.size() >= 1) {
            Intervalo intervaloPrimero = (Intervalo) intervalos.toArray()[0];
            tiempoInicial = intervaloPrimero.getFechaInicio();
        }
        if (subTareas != null) {
            Object[] tareas = subTareas.toArray();

            for (int i = 0; i < tareas.length; i++) {
                Tarea tarea = (Tarea) tareas[i];
                tiempoInicial2 = tarea.getTiempoInicio();
                if (tiempoInicial2 != null) {
                    if (tiempoInicial2.before(tiempoInicial)) {
                        tiempoInicial = tiempoInicial2;
                    }
                }
            }
        }
        return tiempoInicial;
    }
    /**
     * Formatea el tiempoIncio de milisegundos a formato HH:mm:ss.
     * @return String formatado en HH:mm:ss
     */
    @Override
    public String getTiempoInicioFormatado() {
        Date calendario = getTiempoInicio();
        String fechaFormatada = "";
        if (calendario != null) {
            DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
            fechaFormatada = dff.format(calendario.getTime());
        }
        assert fechaFormatada != null : "Fecha no formatada";
        return fechaFormatada;
    }
    /**
     * Obtiene de manera recursiva el tiempo final del proyecto.
     * @return tiempoFinal: GregorianCalendar con la fecha mas reciente obtenida
     */
    public Date getTiempoFinal() {
        Date tiempoFinal = new Date(0);
        Date tiempoFinal3 = new Date(0);
        Date tiempoReferencia = new Date(0);
        assert tiempoFinal != null && tiempoReferencia
                != null && tiempoReferencia != null
                : "Inicialitzacion de variables de tiempo incorrecta";
        if (intervalos.size() >= 1) {
            for (int i = 0; i < intervalos.size(); i++) {
                Intervalo intervalo = (Intervalo) intervalos.toArray()[i];
                if(intervalo!=null){
                    tiempoFinal = intervalo.getFechaFin();
                    if (tiempoFinal.after(tiempoReferencia)) {
                        tiempoReferencia = tiempoFinal;
                        tiempoFinal3 = tiempoFinal;
                    }}
            }
        }

        if (subTareas != null) {
            Object[] tareas = subTareas.toArray();
            Date tiempoFinal2;

            for (int i = 0; i < tareas.length; i++) {
                Tarea tarea = (Tarea) tareas[i];
                tiempoFinal2 = tarea.getTiempoFinal();
                if (tiempoFinal == null) {
                    tiempoFinal = tiempoFinal2;
                } else {
                    if (tiempoFinal2 != null) {
                        if (tiempoFinal2.after(tiempoFinal)) {
                            tiempoFinal = tiempoFinal2;
                        }
                    }
                }

            }
        }
        if (tiempoFinal3.after(tiempoFinal)) {
            tiempoFinal = tiempoFinal3;
        }
        assert tiempoFinal != null : "tiempo final con valor no posible";
        return tiempoFinal;
    }
    /**
     * Formatea el tiempoFinal de milisegundos a formato HH:mm:ss.
     *
     * @return String formatado en HH:mm:ss
     */
    @Override
    public String getTiempoFinalFormatado() {
        Date calendario = getTiempoFinal();
        //assert calendario!=null : "Tiempo final nulo";
        String fechaFormatada = "";
        if (calendario != null) {
            DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
            fechaFormatada = dff.format(calendario.getTime());
        }
        assert fechaFormatada != "" : "Fecha no formatada";
        return fechaFormatada;
    }

    /**
     * Devuelve el tiempo total del proyecto (suma de los intervalos
     * de cada una de las tareas y subproyectos que contiene).
     * @return tiempo
     */

    @Override
    public long calcularTiempo() {
        long suma = 0;
        if (intervalos.size() >= 1) {
            for (int i = 0; i < intervalos.size(); i++) {
                Intervalo intervalo = (Intervalo) intervalos.toArray()[i];
                suma += intervalo.getTiempoIntervalo();
            }
        }
        if (subTareas != null) {
            for (int j = 0; j < subTareas.size(); j++) {
                Tarea t = (Tarea) subTareas.toArray()[j];
                suma += t.calcularTiempo();
            }
        }
        assert suma >= 0 : "Valor no posible de tiempo";
        return suma;
    }

    /**
     * Formatea el resultado de milisegundos a formato HH:mm:ss.
     *
     * @return String formatado en HH:mm:ss
     */
    @Override
    public String getIntervalo() {
        final int segPorMin = 60;
        final int msPorSeg = 1000;
        long tiempoIntervalo = calcularTiempo();
        long segundos = tiempoIntervalo / msPorSeg;
        long minutos = segundos / segPorMin;
        long horas = minutos / segPorMin;
        segundos = segundos % segPorMin;
        minutos = minutos % segPorMin;
        //assert tiempoIntervalo>0: "Intervalo sin tiempo";
        return Long.toString(horas)
                + ":" + Long.toString(minutos)
                + ":" + Long.toString(segundos);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Collection<Intervalo> getIntervalos() {
        return intervalos;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Collection<Tarea> getSubTareas() {
        return subTareas;
    }

    public void setNum(final String num) {
        this.num = num;
    }

    public String getNum() {
        return this.num;
    }

    public boolean isActivo() {
        return activo;
    }
}
