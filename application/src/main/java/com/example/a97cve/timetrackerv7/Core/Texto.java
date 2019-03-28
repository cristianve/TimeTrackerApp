package com.example.a97cve.timetrackerv7.Core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Class Texto
 * Clase que extiende de formato
 * encargada de crear y gestionar
 * el texto
 *
 */

public class Texto extends Formato {
    private String txtGlobal = "";
    public void guardar() throws IOException {
        File file = new File("testInforme.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(txtGlobal);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Añade los datos al fichero segun
     * el tipo de Titulo
     * @param e
     */
    @Override
    public void visit(final Titulo e) { //TITULO TEXTO
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("Informe " + e.getTitulo());
        txtGlobal += "Informe " + e.getTitulo() + "\r\n";
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
    }
    /**
     * Añade los datos al fichero segun
     * el tipo de Subtitulo
     * @param docPart
     */
    @Override
    public void visit(final Subtitulo docPart) { //PERIODE-PROJECTES TEXTO
        System.out.println("Periode");
        txtGlobal += "Periode" + "\r\n";
        for (int i = 0; i < docPart.getTaulaPeriode().size(); i++) {
            txtGlobal += docPart.getTaulaPeriode().get(i) + "\r\n";
            System.out.println(docPart.getTaulaPeriode().get(i));
        }
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
        System.out.println("Projecte arrel");
        txtGlobal += "Projecte arrel" + "\r\n";
        for (int i = 0; i < docPart.getTaulaProjecte().size(); i++) {
            Object tablaP = docPart.getTaulaProjecte().get(i);
            System.out.println(tablaP);
            txtGlobal += "" + tablaP + "\r\n";
        }
    }
    /**
     * Añade los datos al fichero segun
     * el tipo de Taulas
     * @param taula
     */
    @Override
    public void visit(final Taulas taula) { //SUBPROJECTES-TASQUES-INTERVALS
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("Subprojectes");
        txtGlobal += "Subprojectes" + "\r\n";
        System.out.println("S'inclouen en la seguent taula "
                +
                "nomes els subprojectes que tinguin alguna "
                +
                "tasca amb algun interval dins del periode.\n");
        txtGlobal += "S'inclouen en la seguent taula nomes els "
                +
                "subprojectes que tinguin alguna tasca amb "
                +
                "algun interval dins del periode." + "\r\n";
        for (int i = 0; i < taula.getTaulaSubProyectes().size(); i++) {
            Object tabla = taula.getTaulaSubProyectes().get(i);
            txtGlobal += "" + tabla + "\r\n";
            System.out.println(tabla);
        }
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("Tasques");
        txtGlobal += "Tasques" + "\r\n";
        System.out.println("S'inclouen en la seguen taula la"
                +
                "durada de totes tasques i el projecte"
                +
                "al qual pertanyen.\n");
        txtGlobal += "S'inclouen en la seguen taula la durada"
                +
                "de totes tasques i el projecte al qual"
                +
                "pertanyen.\n" + "\r\n";
        for (int i = 0; i < taula.getTaulaTasques().size(); i++) {
            Object t = taula.getTaulaTasques().get(i);
            txtGlobal += "" + t + "\r\n";
            System.out.println(t);
        }
        System.out.println("-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------");
        txtGlobal += "-----------------------------"
                +
                "------------------------------"
                +
                "------------------------------"
                +
                "-------------" + "\r\n";
        System.out.println("Intervals");
        txtGlobal += "Intervals" + "\r\n";
        System.out.println("Sinclouen en la seguent taula el temps "
                +
                "dinici, final i durada de tots els "
                +
                "intervals entre la data inicial i"
                +
                "final especificades, i la tasca i"
                +
                "projecte al qual pertanyen.\n");
        for (int i = 0; i < taula.getTaulaInterval().size(); i++) {
            Object t = taula.getTaulaInterval().get(i);
            txtGlobal += "" + t + "\r\n";
            System.out.println(t);
        }
    }
}
