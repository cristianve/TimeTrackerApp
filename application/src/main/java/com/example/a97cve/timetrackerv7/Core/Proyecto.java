package com.example.a97cve.timetrackerv7.Core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Class Proyecto
 * Clase que extendie de Actividad e
 * implementa y gestiona una Collection
 * de proyectos y puede añadir Tareas
 */

public class Proyecto extends Actividad implements Serializable{
    private Collection<Proyecto> subProyectos = new ArrayList<Proyecto>();
    public Proyecto() {
        subTareas = new ArrayList<>();
    }

    public Tarea crearTarea(final String nombre, final String descripcion) {
        Tarea tarea = new Tarea(nombre, descripcion);
        subTareas.add(tarea);
        return tarea;
    }

    public Proyecto crearSubProyecto(final String nombre, final String desc) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(desc);
        subProyectos.add(proyecto);
        return proyecto;
    }


    public void addSubProyecto(Proyecto proyectoActualizado) {
        if (proyectoActualizado != null) {
            int pos = subProyectos.toArray().length + 1;
            for (int i = 0; i < subProyectos.toArray().length; i++) {

                Proyecto p1 = (Proyecto) subProyectos.toArray()[i];
                if (p1.getNombre().equals(proyectoActualizado.getNombre())) {
                    pos = i;
                }
            }
            if(pos<=subProyectos.toArray().length){
                //subProyectos.toArray()[pos]= proyectoActualizado;
                Object[] sP = subProyectos.toArray();
                sP[pos] = proyectoActualizado;
                Collection<Proyecto> res = new ArrayList<>();
                for(int i = 0; i<sP.length;i++)
                {
                    res.add((Proyecto) sP[i]);
                }
                subProyectos = res;
                //subProyectos.remove(subProyectos.toArray()[pos]);
                //subProyectos.add(proyectoActualizado);
            }
        }
    }



    /**.
     * Devuelve el tiempo total del proyecto (suma de los intervalos
     * de cada una de las tareas y subproyectos que contiene)
     *
     * @return tiempo
     */
    @Override
    public long calcularTiempo() {
        //TODO
        long sumaTiempo = 0;

        for (int i = 0; i < subTareas.size(); i++) {
            Tarea tarea = (Tarea) subTareas.toArray()[i];
            sumaTiempo += tarea.calcularTiempo();
        }

        for (int p = 0; p < subProyectos.size(); p++) {
            Proyecto proyecto = (Proyecto) subProyectos.toArray()[p];
            sumaTiempo += proyecto.calcularTiempo();
        }
        return sumaTiempo;
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
        String res = Long.toString(horas) + ":"
                +
                Long.toString(minutos) + ":" + Long.toString(segundos);
        return res;
    }

    /**.
     * Obtiene de manera recursiva el tiempo incicial del proyecto
     * @return tiempoInicial: GregorianCalendar con la fecha
     * mas antigua obtenida
     */
    public Date getTiempoInicio() {
        Date tiempoInicial = null;
        Date tiempoInicial2;

        if (subTareas.size() >= 1) {
            for (int j = 0; j < subTareas.size(); j++) {
                Tarea t = (Tarea) subTareas.toArray()[j];
                tiempoInicial2 = t.getTiempoInicio();
                if (tiempoInicial == null) {
                    tiempoInicial = tiempoInicial2;
                } else {
                    if (tiempoInicial2 != null) {
                        if (tiempoInicial2.before(tiempoInicial)) {
                            tiempoInicial = tiempoInicial2;
                        }
                    }
                }
            }
        }

        if (subProyectos.size() >= 1) {
            Object[] subProyectosAux = subProyectos.toArray();

            for (int i = 0; i < subProyectosAux.length; i++) {
                Proyecto proyecto = (Proyecto) subProyectosAux[i];
                tiempoInicial2 = proyecto.getTiempoInicio();
                if (tiempoInicial == null) {
                    tiempoInicial = tiempoInicial2;
                } else {
                    if (tiempoInicial2 != null) {
                        if (tiempoInicial2.before(tiempoInicial)) {
                            tiempoInicial = tiempoInicial2;
                        }
                    }
                }
            }
        }
        return tiempoInicial;
    }

    /**
     * Formatea el tiempoIncio de milisegundos a formato HH:mm:ss.
     *
     * @return String formatado en HH:mm:ss
     */
    @Override
    public String getTiempoInicioFormatado() {
        Date calendario = getTiempoInicio();
        String fechaFormatada = "                    ";
        if (calendario != null) {
            DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
            fechaFormatada = dff.format(calendario.getTime());
        }

        return fechaFormatada;
    }
    /**.
     * Obtiene de manera recursiva el tiempo final del proyecto
     * @return tiempoFinal: GregorianCalendar con la fecha mas reciente obtenida
     */
    public Date getTiempoFinal() {
        Date tiempoFinal = null;
        Date tiempoFinal2;

        if (subTareas.size() >= 1) {
            for (int j = 0; j < subTareas.size(); j++) {
                Tarea t = (Tarea) subTareas.toArray()[j];
                tiempoFinal2 = t.getTiempoFinal();
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

        if (subProyectos.size() >= 1) {
            Object[] subProyectosAux = subProyectos.toArray();

            for (int i = 0; i < subProyectosAux.length; i++) {
                Proyecto proyecto = (Proyecto) subProyectosAux[i];
                tiempoFinal2 = proyecto.getTiempoFinal();
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
        String fechaFormatada = "                    ";
        if (calendario != null) {
            DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
            fechaFormatada = dff.format(calendario.getTime());
        }
        return fechaFormatada;
    }

    public Collection<Proyecto> getSubProyectos() {
        return subProyectos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public void setNum(final String num) {
        this.num = num;
    }

    public String getNum() {
        return this.num;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Collection<Tarea> getSubTareas() {
        return subTareas;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
