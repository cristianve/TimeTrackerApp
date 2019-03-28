package com.example.a97cve.timetrackerv7.Core;

import java.util.Date;

/**
 * Class IBreve
 * Clase que extiende de Informe
 * encargada de generar informe
 * breve y llamar al visitor
 * dependiendo del formato
 */
public class IBreve extends Informe {

    @Override
    void crearInforme(final Formato f,
                      final Date ini, final Date fin, final Proyecto p) {
        Titulo t = new Titulo("Breve");
        Subtitulo s = new Subtitulo(ini, fin, p);
        f.visit(t);
        f.visit(s);
    }
}
