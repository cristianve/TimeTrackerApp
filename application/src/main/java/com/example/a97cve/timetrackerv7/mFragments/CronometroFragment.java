package com.example.a97cve.timetrackerv7.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a97cve.timetrackerv7.Core.Actividad;
import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.Core.Reloj;
import com.example.a97cve.timetrackerv7.Core.Tarea;
import com.example.a97cve.timetrackerv7.R;
import com.example.a97cve.timetrackerv7.mActivitys.AdaptadorTarea;
import com.example.a97cve.timetrackerv7.mActivitys.AddFABActivity;
import com.example.a97cve.timetrackerv7.mActivitys.DatosActividades;
import com.example.a97cve.timetrackerv7.mActivitys.GetProyectoRaiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Class CronometroFragment
 * Clase encargagada gestionar
 * el fragmento del tab
 * Cronometro. Encargado
 * de listar todas las
 * Tareas con Start/Stop
 */
public class CronometroFragment extends Fragment {

    private Collection<Proyecto> lSubProyectos = new ArrayList<Proyecto>();
    private Collection<Tarea> listaTareas = new ArrayList<Tarea>();
    private List<Tarea> listaTareasNoCollection = new ArrayList<Tarea>();

    ListView listView;
    ArrayList<DatosActividades> listaTareas2 = new ArrayList<DatosActividades>();
    Proyecto rootProyecto;

    /**
     * Devuelve lista de tareas que existen en nuestro arbol
     * @param p
     */
    public void getActividadesRecursivo(final Actividad p) {
        Object[] lista;
        Collection<Tarea> subTareas = p.getSubTareas();
        if (subTareas != null) {
            lista = subTareas.toArray();
            for (int i = 0; i < subTareas.size(); i++) {
                Tarea t = (Tarea) lista[i];
                listaTareas.add(t);
                listaTareasNoCollection.add(t);
                getActividadesRecursivo(t);
            }
        }

        if (p.getClass() == Proyecto.class) {
            Proyecto proyecto = (Proyecto) p;
            Collection<Proyecto> subProyectos
                    = proyecto.getSubProyectos();
            lista = subProyectos.toArray();
            for (int i = 0; i < subProyectos.size(); i++) {
                Proyecto pr = (Proyecto) lista[i];
                int contador = 1 + i;
                if (proyecto.getNum() != null) {
                    String primerIndice = proyecto.getNum();
                    String segundoIndice = "" + contador++;
                    pr.setNum("" + primerIndice + "." + ""
                            + segundoIndice);
                }
                lSubProyectos.add(pr);
                getActividadesRecursivo(pr);
            }
        }
    }

    /**
     * Mete la lista de tareas en formato para el adapter
     */
    private void cargarListaTareas() {

        Proyecto proyecto = (Proyecto) rootProyecto;

        listaTareas = new ArrayList<>();
        listaTareasNoCollection = new ArrayList<>();
        getActividadesRecursivo(rootProyecto);

        Object[] lista = listaTareas.toArray();
        listaTareas2 = new ArrayList<DatosActividades>();

        for (int i = 0; i < listaTareas.size(); i++) {

            Tarea p1 = (Tarea) lista[i];
            DatosActividades datosProyecto = new DatosActividades(p1.getNombre(),p1.getDescripcion(), p1.getTiempoInicioFormatado(), p1.getTiempoFinalFormatado(), p1.getIntervalo(), R.drawable.ic_play);
            listaTareas2.add(datosProyecto);
        }

    }

    /**
     * DEVUELVE LA VISTA DEL FRAGMENTO CRONOMETRO
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle rootProyect = getArguments();
        if (rootProyect != null) {
            rootProyecto = (Proyecto) rootProyect.get("root");
        }

        getActividadesRecursivo(rootProyecto);

        return inflater.inflate(R.layout.fragment_tab2_cronometro,container,false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler refreshHandler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                onResume();
                refreshHandler.postDelayed(this,    2 * 1000);
            }
        };
        refreshHandler.postDelayed(runnable,  2 * 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        /**FAButton para añadir nueva tarea ( Actividad AddFABActivity).*/
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent añadirActividad = new Intent(getActivity().getApplication(), AddFABActivity.class);
               añadirActividad.putExtra("proyecto", rootProyecto);
               startActivityForResult(añadirActividad, 1);
           }
       });

        /**AdaptadorTarea adapta los datos para añadirlos a la listView (lista Tarea).*/
        cargarListaTareas();

        AdaptadorTarea adapter = new AdaptadorTarea(getActivity(), listaTareas2, listaTareasNoCollection);
        listView = (ListView) getView().findViewById(R.id.listViewTarea);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            rootProyecto = GetProyectoRaiz.getP();
            onResume();
        }
    }
}






