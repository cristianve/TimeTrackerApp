package com.example.a97cve.timetrackerv7.Core;

import java.util.Date;

/**
 * Class Informe
 * Clase abstracta con la estructura
 * para generar un informe.
 */
public abstract class Informe {
    abstract void crearInforme(Formato f, Date ini, Date fin, Proyecto p);
}
