package com.example.a97cve.timetrackerv7.Core;

import java.util.ArrayList;
/**
 * Class Taula
 * Clase para crear y gestionar una tabla de  ArrayList
 *
 */
public class Taula {

    private int nfiles;

    public int getNfiles() {
        return nfiles;
    }

    protected void setNfiles(final int numfiles) {
        this.nfiles = numfiles;
    }

    private int ncolumnes;

    public int getNcolumnes() {
        return ncolumnes;
    }

    protected void setNcolumnes(final int numcolumnes) {
        this.ncolumnes = numcolumnes;
    }

    private ArrayList taula = null;

    public ArrayList getTaula() {
        return taula;
    }

    public void setTaula(final ArrayList t) {
        this.taula = t;
    }

    public Taula(final int numfiles, final int numcolumnes) {
        setNfiles(numfiles);
        setNcolumnes(numcolumnes);
        ArrayList t = new ArrayList();
        for (int i = 0; i < numfiles; i++) {
            ArrayList fila = new ArrayList();
            for (int j = 0; j < numcolumnes; j++) {
                fila.add(null);
            }
            t.add(fila);
        }
        setTaula(t);
    }

    public void afegeixFila() {
        int numcolumnes = getNcolumnes();
        ArrayList fila = new ArrayList();
        for (int j = 0; j < numcolumnes; j++) {
            fila.add(null);
        }
        getTaula().add(fila);
        setNfiles(getNfiles() + 1);
    }

    public void afegeixFila(final String string) {
        getTaula().add(string);
        setNfiles(getNfiles() + 1);
    }

    // numerem de 1 ... n i no de 0 ... n-1
    public void setPosicio(final int fila,
                           final int columna, final String str) {
        ((ArrayList) getTaula().get(fila - 1)).set(columna - 1, str);
    }

    public String getPosicio(final int fila, final int columna) {
        ArrayList aux = (ArrayList) getTaula().get(fila - 1);
        return (String) aux.get(columna - 1);
    }

    public void imprimeix() {
        System.out.println(this.getTaula());
    }


}