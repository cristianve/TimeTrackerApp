package com.example.a97cve.timetrackerv7.mActivitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.Core.Tarea;
import com.example.a97cve.timetrackerv7.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class ExplorerNextActivity
 * Actividad que se abre automaticamente
 * cuando exploramos un proyectoRaiz.
 * Contiene el path por el cual avanza
 * el usuario.
 */
public class ExplorerNextActivity extends AppCompatActivity {

    String path="/";
    ArrayList<DatosActividades> listaActual = new ArrayList<DatosActividades>();
    Proyecto proyectoActual;

    /**.
     * Carga lista de actividades del proyecto en que estamos
     */
    public void cargarListaActual(){


        listaActual = new ArrayList<DatosActividades>(); //ACTUALIZAR

        Proyecto proyecto = (Proyecto) proyectoActual;
        Collection<Proyecto> subProyectos = proyecto.getSubProyectos();
        Object[] lista = subProyectos.toArray();



        for (int i = 0; i < proyecto.getSubProyectos().size(); i++) {


            Proyecto p1 = (Proyecto) lista[i];
            DatosActividades datosProyecto = new DatosActividades(p1.getNombre(),p1.getDescripcion(), p1.getTiempoInicioFormatado(), p1.getTiempoFinalFormatado(), p1.getIntervalo(), R.drawable.ic_explorer);
            listaActual.add(datosProyecto);
        }

        Collection<Tarea> subTareas = proyecto.getSubTareas();


        Object[] listaTareas = subTareas.toArray();

        for (int i = 0; i < proyecto.getSubTareas().size(); i++) {

            Tarea p1 = (Tarea) listaTareas[i];
            DatosActividades datosProyecto = new DatosActividades(p1.getNombre(),p1.getDescripcion(), p1.getTiempoInicioFormatado(), p1.getTiempoFinalFormatado(), p1.getIntervalo(), R.drawable.ic_informe);
            listaActual.add(datosProyecto);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        TextView ruta = (TextView) findViewById(R.id.textRuta) ;
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            path = bundle.getString("ruta");
            proyectoActual= (Proyecto)bundle.get("root");
            ruta.setText(path);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**.
         * FAButton para añadir nueva tarea ( Actividad AddFABActivity)
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent añadirActividad = new Intent(getApplication().getApplicationContext(), AddFABActivity.class);
                añadirActividad.putExtra("proyecto", proyectoActual);
                startActivityForResult(añadirActividad, 1);
            }
        });


        /**.
         * Button que nos permite volver a la actividad anterior
         */
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent= new Intent();
                resultIntent.putExtra("proyectoActual",proyectoActual);
                setResult(RESULT_FIRST_USER,resultIntent);
                finish();
            }
        });

        cargarListaActual();

        /**.
         * AdaptadorProyecto adapta los datos para añadirlos a la listView (lista Proyectos)
         */

        AdaptadorProyecto listViewAdapter = new AdaptadorProyecto(getApplication(), listaActual);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listViewAdapter);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String tipo="";
                if(listaActual.get(position).getImagen()==R.drawable.ic_explorer){
                    tipo= "Proyecto";
                }else
                    tipo = "Tarea";

                Intent añadirActividad = new Intent(getApplication().getApplicationContext(), InfoActivity1.class);
                //añadirActividad.putExtra("proyecto", proyectoActual);

                añadirActividad.putExtra("Tipo",tipo);
                añadirActividad.putExtra("Nombre",listaActual.get(position).getTitulo());
                añadirActividad.putExtra("Descripcion",listaActual.get(position).getDescripcion());
                añadirActividad.putExtra("Ubicacion",path + "/" + listaActual.get(position).getTitulo());
                añadirActividad.putExtra("TiempoInicio",listaActual.get(position).getFechaInit());
                añadirActividad.putExtra("TiempoFinal",listaActual.get(position).getFechaFin());
                añadirActividad.putExtra("Duracion",listaActual.get(position).getDuracion());
                startActivity(añadirActividad);

                return  true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(proyectoActual.getSubProyectos().toArray().length > position){

                    Intent next = new Intent(ExplorerNextActivity.this, ExplorerNextActivity.class);
                    next.putExtra("ruta",path + "/" + listaActual.get(position).getTitulo());
                    next.putExtra("root", (Proyecto) proyectoActual.getSubProyectos().toArray()[position]);
                    startActivityForResult(next, 1);
                }
            }
        });

    }

    /**
     * Toma valores despues de startActivityForResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) { // Please, use a final int instead of hardcoded int value
            if (resultCode == Activity.RESULT_OK) {

                proyectoActual = (Proyecto) data.getExtras().get("proyectoActualizado");
            }
            if (resultCode == Activity.RESULT_FIRST_USER) {
                Proyecto subProyectoActualizado = (Proyecto) data.getExtras().get("proyectoActual");
                proyectoActual.addSubProyecto(subProyectoActualizado);
            }

        }
    }
}
