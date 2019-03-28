package com.example.a97cve.timetrackerv7.Core;
import java.util.Date;

public class IDetallado extends Informe {
    /**
     * Class IDetallado
     * Clase que extiende de Informe
     * encargada de generar informe
     * detallado y llamar al visitor
     * dependiendo del formato
     */
    @Override
    void crearInforme(final Formato f,
                      final Date ini, final Date fin, final Proyecto p) {
        Titulo titulo = new Titulo("Detallado");
        Subtitulo subtitulo = new Subtitulo(ini, fin, p);
        Taulas taula = new Taulas(ini, fin, p);
        f.visit(titulo);
        f.visit(subtitulo);
        f.visit(taula);
    }
}
