package com.example.a97cve.timetrackerv7.Core;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;


/**
 * Class Impresor
 * Clase que implementa el
 * patron Observer para printar
 * el arbol recursivamente
 * por consola
 */
public class Impresor implements Observer {

    private Actividad actividadPadre;
    public Impresor(final Actividad aPadre) {
        this.actividadPadre = aPadre;
        Reloj.getInstancia().addObserver(this);
    }

    /**.
     * Printa la estructura de la tabla y llama
     * a la funcion recursiva de printado de activdades
     */
    public void printa() {
        System.out.println("Nombre      Tiempo inicio"
                +
                "Tiempo final       Duracion (hh:mm:ss)");
        System.out.println("-------+-----------------------+"
                +
                "---------------------+----------------------\n");
        printadoActividadesRecursivo(actividadPadre);
    }

    /**.
     * Recorre desde el proyecto padre todas la
     * actividades recursivamente y las printa
     * @param p actividad que se printara con sus tiempos
     */
    private void printadoActividadesRecursivo(final Actividad p) {
        Object[] lista;
        System.out.println(p.getNombre() + "\t" + p.getTiempoInicioFormatado()
                +
                "\t" + p.getTiempoFinalFormatado() + "\t" + p.getIntervalo());

        Collection<Tarea> subTareas = p.getSubTareas();
        if (subTareas != null) {
            lista = subTareas.toArray();
            for (int i = 0; i < subTareas.size(); i++) {
                Tarea t = (Tarea) lista[i];
                printadoActividadesRecursivo(t);
            }
        }

        if (p.getClass() == Proyecto.class) {
            Proyecto proyecto = (Proyecto) p;
            Collection<Proyecto> subProyectos = proyecto.getSubProyectos();
            lista = subProyectos.toArray();
            for (int i = 0; i < subProyectos.size(); i++) {
                Proyecto pr = (Proyecto) lista[i];
                printadoActividadesRecursivo(pr);
            }
        }

    }

    @Override
    public void update(final Observable o, final Object arg) {
        printa();
    }
}
