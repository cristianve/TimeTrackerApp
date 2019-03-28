package com.example.a97cve.timetrackerv7.mFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.R;
import com.example.a97cve.timetrackerv7.mActivitys.GetProyectoRaiz;

/**
 * Class ConfigFragment
 * Clase encargagada gestionar
 * el fragmento del tab
 * Configuracion.
 */
public class ConfigFragment extends Fragment {

    @Nullable
    @Override
    //DEVUELVE LA VISTA DEL FRAGMENTO CONFIG
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab4_config,container,false);
        //rootProyecto = GetProyectoRaiz.getP();
    }
    /*@Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (!visible) {*/
            /*Proyecto rootProyecto = GetProyectoRaiz.getP();
            GetProyectoRaiz.setP(rootProyecto);*/
        /*}
    }*/

}
