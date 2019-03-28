package com.example.a97cve.timetrackerv7.mActivitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a97cve.timetrackerv7.Core.Proyecto;

/**
   * Class GetProyectoRaiz
   * Clase que permite guardar y recibir
   * el proyecto raiz de forma estatica
   */
public class GetProyectoRaiz extends AppCompatActivity {

    static Proyecto pRoot;

    public static Proyecto getP()
    {
        return pRoot;
    }
    public static void setP(Proyecto p)
    {
        pRoot = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}