package com.example.a97cve.timetrackerv7.Core;

/**
 * Class GeneradorTicks
 * Clase que extiende de Thread
 * encarga de gestionar los tick
 * de la clase Reloj.
 */
public class GeneradorTicks extends Thread {
    private Reloj hora;
    private int intervalo;


    /**
     * El generador de ticks se crea en un thread
     * para que funcione de forma intependiente.
     * Habra un tick cada intervalo de milisegundos.
     *
     * @param reloj
     * @param i
     */
    public GeneradorTicks(final Reloj reloj, final int i) {
        setDaemon(true);
        this.hora = reloj;
        this.intervalo = i;
        start();
    }

    /**.
     * Llama la funcion tick de reloj para que se
     * actualize cada intervalo de tiempo
     */
    public void run() {
        while (true) {
            try {
                sleep(intervalo); //milisegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // no podemos permitir que nadie cambie la hora
            // por eso creamos una función tick()
            // en el reloj que se encarga de hacerlo
            hora.tick();
        }
    }

}
