package com.example.a97cve.timetrackerv7.Core;

/**
 * Class Formato
 * Clase abstracta encarga de visitar
 * el elemento segun el tipo de formato
 */
public abstract class Formato {
    public abstract void visit(Titulo titulo);
    public abstract void visit(Subtitulo docPart);
    public abstract void visit(Taulas taula);
}
