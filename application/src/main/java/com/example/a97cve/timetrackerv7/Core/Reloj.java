package com.example.a97cve.timetrackerv7.Core;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
/**
 * Class Reloj
 * Clase que aplica el patron Singelton
 * y Observable para gestionar los observadores
 * y enviar instancias de la hora.
 */
public final class   Reloj extends Observable {
    private Date hora;
    private static Reloj instancia = null;
    private Reloj() {
        this.hora = new Date();
    }

    /**.
     * Devuelve la instancia unica de reloj
     * @return
     */
    public static Reloj getInstancia() {
        if (instancia == null) {
            instancia = new Reloj();
        }
        return instancia;
    }

    /**
     * Actualiza la hora y notifica a los observers.
     */
    public void tick() {
        this.hora = new Date();
        setChanged();
        notifyObservers(this);
    }

    public Date getHora() {
        return this.hora;
    }

    /**
     * Añade un observador.
     */
    @Override
    public synchronized void addObserver(final Observer o) {
        super.addObserver(o);
    }
    /**
     * Borra un observador.
     */
    @Override
    public synchronized void deleteObserver(final Observer o) {
        super.deleteObserver(o);
    }
    /**
     * Notifica a los observadores.
     */
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
    /**
     * Notifica a los observadores y les pasa un objecto
     * @arg
     */
    @Override
    public void notifyObservers(final Object arg) {
        super.notifyObservers(arg);
    }
    /**
     * Borra a los observadores.
     */
    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }
    /**
     * Notifica que si el estado ha cambiado
     * @boolean
     */
    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }
    /**
     * Devuelve el numero de observadores
     * @int
     */
    @Override
    public synchronized int countObservers() {
        return super.countObservers();
    }
}
