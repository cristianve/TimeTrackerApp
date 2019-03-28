package com.example.a97cve.timetrackerv7.Core;
import paginaweb.PaginaWeb;


/**
 * Class Html
 * Clase que extiende de formato
 * encargada de crear y gestionar la pagina
 * web dependiendo de quien la visite
 */

public class Html extends Formato {
    private Proyecto actividadPadre;
    private PaginaWeb webGeneral = new PaginaWeb();

    public void printarPagina() {
        webGeneral.escriuPagina();
    }


    /**
     * Añade la cabezera segun
     * el tipo de Titulo
     * @param e
     */
    @Override
    public void visit(final Titulo e) {

        //TITULO DOCUMENTO
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader(e.getTitulo(), 1, true);
        webGeneral.afegeixLiniaSeparacio();
    }

    /**
     * Añade la cabezera segun
     * el tipo de Subtitulo
     * @param docPart
     */
    @Override
    public void visit(final Subtitulo docPart) {
        //SUBTITULO INFORME HTML DETALLADO
        //TAULAPERIODE
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader("Periode", 2, false);
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixTaula(docPart.getTaulaPeriode(), true, true);
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader("Projectes arrel", 2, false);
        webGeneral.afegeixSaltDeLinia();
        boolean b = true;
        webGeneral.afegeixTaula(docPart.getTaulaProjecte(), b, false);
        webGeneral.afegeixSaltDeLinia();
    }
    /**
     * Añade la cabezera segun
     * el tipo de Taulas
     * @param taula
     */
    @Override
    public void visit(final Taulas taula) {
        //SUBPROJECTES
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader("Subprojectes", 2, false);
        webGeneral.afegeixSaltDeLinia();
        String lineaLarga = "Sinclouen en la seguent taula nomes els ";
        lineaLarga += "subprojectes que tinguin alguna tasca amb algun";
        lineaLarga += " interval dins del periode.";
        webGeneral.afegeixTextNormal(lineaLarga);
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixSaltDeLinia();
        Boolean b = true;
        webGeneral.afegeixTaula(taula.getTaulaSubProyectes(), b, false);
        //TAREAS
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader("Tasques", 2, false);
        lineaLarga = "Sinclouen en la seguent taula la durada de ";
        lineaLarga += "totes tasques i el projecte al qual pertanyen.";
        webGeneral.afegeixTextNormal(lineaLarga);
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixTaula(taula.getTaulaTasques(), true, false);
        //INTERVALOS
        webGeneral.afegeixLiniaSeparacio();
        webGeneral.afegeixHeader("Intervalos", 2, false);
        lineaLarga = "Sinclouen en la seguent taula el temps dinici,";
        lineaLarga += "final i durada de tots els intervals entre la ";
        lineaLarga += "data inicial i final especificades, i la tasca";
        lineaLarga += " i projecte al qual pertanyen.";
        webGeneral.afegeixTextNormal(lineaLarga);
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixSaltDeLinia();
        webGeneral.afegeixTaula(taula.getTaulaInterval(), true, false);
    }
}
