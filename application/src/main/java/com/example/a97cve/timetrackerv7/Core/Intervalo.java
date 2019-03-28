package com.example.a97cve.timetrackerv7.Core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Class Intervalo
 * Clase que implementa el patron Observer
 * y serializabe para poder observar el
 * reloj y gestionar los diferentes intervalos
 */
public class Intervalo implements Observer, Serializable{
    private Date fechaInicio;
    private Date fechaFin;
    private long tiempoIntervalo; //fecha fin - fecha inicio

    public Intervalo() {
        this.fechaInicio = Reloj.getInstancia().getHora();
        Reloj.getInstancia().addObserver(this);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaInicioInterForm() {
        Date calendario = this.fechaInicio;
        String fechaFormatada = "";
        if (calendario != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
            fechaFormatada = df.format(calendario.getTime());
        }
        return fechaFormatada;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getFechaFinInterForm() {
        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss");
        return dff.format(fechaFin);
    }

    public long getTiempoIntervalo() {
        return tiempoIntervalo;
    }

    public String getIntervalo() {
        final int segPorMin = 60;
        final int msPorSeg = 1000;
        long tIntervalo = this.tiempoIntervalo;
        long segundos = tIntervalo / msPorSeg;
        long minutos = segundos / segPorMin;
        long horas = minutos / segPorMin;
        segundos = segundos % segPorMin;
        minutos = minutos % segPorMin;
        String res = Long.toString(horas) + ":"
                + Long.toString(minutos) + ":" + Long.toString(segundos);
        return res;
    }


    public void setFechaInicio(final Date fInicio) {
        this.fechaInicio = fInicio;
    }


    public void setFechaFin(final Date fFin) {
        this.fechaFin = fFin;
    }


    public void setTiempoIntervalo(final long tIntervalo) {
        this.tiempoIntervalo = tIntervalo;
    }


    /**
     * Actualiza la hora fin del intervalo.
     * Se llama desde el Observable.
     * Calcula el tiempo de intervalo.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(final Observable o, final Object arg) {
        Reloj nuevoReloj = (Reloj) arg;
        this.fechaFin = nuevoReloj.getHora();
        this.tiempoIntervalo = this.fechaFin.getTime()
                - this.fechaInicio.getTime();
    }
}
