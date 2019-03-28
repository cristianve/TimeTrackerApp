package com.example.a97cve.timetrackerv7.mActivitys;

/**
 * Class DatosActividades
 * Atributos de datos
 * necesarios para generar
 * una actividad.
 * Atributos actividad
 */
public class DatosActividades {

    private int id;
    private String Titulo;
    private String Descripcion;
    private String FechaInit;
    private String FechaFin;
    private String Duracion;
    private int Imagen;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public DatosActividades(String titulo, String descripcion, String fechaInit, String fechaFin, String duracion, int imagen) {
        Titulo = titulo;
        Descripcion = descripcion;
        FechaInit = fechaInit;
        FechaFin = fechaFin;
        Duracion = duracion;
        Imagen = imagen;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getFechaInit() {
        return FechaInit;
    }

    public void setFechaInit(String fechaInit) {
        FechaInit = fechaInit;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }

    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String duracion) {
        Duracion = duracion;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
