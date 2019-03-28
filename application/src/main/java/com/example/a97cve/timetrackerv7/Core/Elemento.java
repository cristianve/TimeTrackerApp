package com.example.a97cve.timetrackerv7.Core;

/**
 * Class Elemento
 * Clase abstracta encarga de aceptar
 * el elemento segun el tipo de formato
 */
public abstract class Elemento  {
    abstract void accept(Formato f);
}