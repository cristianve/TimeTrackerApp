package com.example.a97cve.timetrackerv7.Core;
/**
 * Class Titulo
 * Clase que extiende de elemento
 * encarga de coger el nombre del
 * tipo de informe y pasarlos
 * a el elemeto que le llama
 */
public class Titulo extends Elemento {


    public String getTitulo() {
        return titulo;
    }



    public void setTitulo(final String t) {
        this.titulo = t;
    }

    private String titulo;
    Titulo(final String t) {
        this.titulo = t;
    }


    @Override
    void accept(final Formato f) {
        // TODO Auto-generated method stub
        f.visit(this);
    }
}
