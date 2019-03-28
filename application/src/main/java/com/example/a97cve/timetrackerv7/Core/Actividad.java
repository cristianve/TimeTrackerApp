package com.example.a97cve.timetrackerv7.Core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Class Actividad
 * Una classe abstracta que define
 * los atributos y meotodos que se
 * implementaran en las clases que extienden.
 */
public abstract class Actividad implements Serializable {

    protected String nombre;
    protected String descripcion;
    protected String num;
    Collection<Tarea> subTareas;

    public abstract String getNombre();

    abstract  String getDescripcion();

    abstract void setNombre(String n);

    abstract void setDescripcion(String d);

    public abstract Collection<Tarea> getSubTareas();

    abstract long calcularTiempo();

    public abstract String getTiempoInicioFormatado();

    public abstract String getTiempoFinalFormatado();

    public abstract String getIntervalo();
    abstract String getNum();
    abstract void setNum(String numero);

    public abstract Tarea crearTarea(String nombre, String Descripcion);
}
