package com.example.a97cve.timetrackerv7.mFragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.Core.Tarea;
import com.example.a97cve.timetrackerv7.R;
import com.example.a97cve.timetrackerv7.mActivitys.AdaptadorProyecto;
import com.example.a97cve.timetrackerv7.mActivitys.AddFABActivity;
import com.example.a97cve.timetrackerv7.mActivitys.DatosActividades;
import com.example.a97cve.timetrackerv7.mActivitys.ExplorerNextActivity;

import com.example.a97cve.timetrackerv7.mActivitys.GetProyectoRaiz;
import com.example.a97cve.timetrackerv7.mActivitys.InfoActivity1;
import com.example.a97cve.timetrackerv7.mActivitys.MainActivity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class ExplorerFragment
 * Fragmento que se abre como
 * default sino es la primera
 * vez. Contiene un listado
 * de los Proyecto Raiz
 */
public class ExplorerFragment extends Fragment {

    Proyecto rootProyecto = new Proyecto();
    ArrayList<DatosActividades> listaSubProyectos = new ArrayList<DatosActividades>();
    ListView listView;


    /**Adaptamos los datos y los guardamos en listaSubProyectos.*/
    private void cargarListaSubProyectosRaiz() {

        Proyecto proyecto = (Proyecto) rootProyecto;
        Collection<Proyecto> subProyectos = proyecto.getSubProyectos();
        Object[] lista = subProyectos.toArray();
        listaSubProyectos = new ArrayList<DatosActividades>();
        for (int i = 0; i < proyecto.getSubProyectos().size(); i++) {
            Proyecto p1 = (Proyecto) lista[i];
            DatosActividades datosProyecto = new DatosActividades(p1.getNombre(),p1.getDescripcion(), p1.getTiempoInicioFormatado(), p1.getTiempoFinalFormatado(), p1.getIntervalo(), R.drawable.ic_explorer);
            listaSubProyectos.add(datosProyecto);
        }
    }


    //DEVUELVE LA VISTA DEL FRAGMENTO EXPLORER
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle rootProyect = getArguments();
        if (rootProyect != null) {
            rootProyecto = (Proyecto) rootProyect.get("root");
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1_explorer, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        /**FAButton para añadir nueva tarea ( Actividad AddFABActivity)*/
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent añadirActividad = new Intent(getActivity().getApplication(), AddFABActivity.class);
                añadirActividad.putExtra("proyecto", rootProyecto);
                startActivityForResult(añadirActividad, 1);
            }
        });

        /**Carga la lista de SubProyectos*/
            cargarListaSubProyectosRaiz();
            GetProyectoRaiz.setP(rootProyecto);


        /**AdaptadorProyecto adapta los datos para añadirlos a la listView (lista ProyectoRaiz)*/


        listView = (ListView) getView().findViewById(R.id.listViewRaiz);
        AdaptadorProyecto adapter = new AdaptadorProyecto(getActivity().getApplicationContext(), listaSubProyectos);
        listView.setAdapter(adapter);


        /**Listener cuando se seleciona un proyecto de la lista. Genera una NextActividad pasandole el path*/

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String tipo="";
                if(listaSubProyectos.get(position).getImagen()==R.drawable.ic_explorer){
                    tipo= "Proyecto";
                }else
                    tipo = "Tarea";


                Intent añadirActividad = new Intent(getActivity(), InfoActivity1.class);
                //añadirActividad.putExtra("proyecto", proyectoActual);

                añadirActividad.putExtra("Tipo",tipo);
                añadirActividad.putExtra("Nombre",listaSubProyectos.get(position).getTitulo());
                añadirActividad.putExtra("Descripcion",listaSubProyectos.get(position).getDescripcion());
                añadirActividad.putExtra("Ubicacion", "/" + listaSubProyectos.get(position).getTitulo());
                añadirActividad.putExtra("TiempoInicio",listaSubProyectos.get(position).getFechaInit());
                añadirActividad.putExtra("TiempoFinal",listaSubProyectos.get(position).getFechaFin());
                añadirActividad.putExtra("Duracion",listaSubProyectos.get(position).getDuracion());
                startActivity(añadirActividad);
                return  true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent crear = new Intent(getActivity().getApplication(), ExplorerNextActivity.class);
                crear.putExtra("ruta", "/" + listaSubProyectos.get(position).getTitulo());
                crear.putExtra("root",(Proyecto)rootProyecto.getSubProyectos().toArray()[position]);
                startActivityForResult(crear, 1);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // Please, use a final int instead of hardcoded int value
            if (resultCode == Activity.RESULT_OK) {

                rootProyecto = (Proyecto) data.getExtras().get("proyectoActualizado");
            }
            if (resultCode == Activity.RESULT_FIRST_USER) {
                Proyecto subProyectoActualizado = (Proyecto) data.getExtras().get("proyectoActual");
                rootProyecto.addSubProyecto(subProyectoActualizado);
            }
        }
    }
}
