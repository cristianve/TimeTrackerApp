package com.example.a97cve.timetrackerv7.mActivitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a97cve.timetrackerv7.R;

import java.util.List;

/**
 * Class AdaptadorProyecto
 * Gestiona los valores de
 * Tarea para adaptarlos a la
 * vista con sus respectivos
 * valores
 */
public class AdaptadorProyecto extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;
    List<DatosActividades> ListaObjetos;

    public AdaptadorProyecto(Context contexto, List<DatosActividades> listaObjetos) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
    }


    @Override
    public int getCount() {
        return ListaObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ListaObjetos.get(position).getId();
    }

    /**
     * Adapta los componentes del layout con los componentes cargados
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;
        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista = inflater.inflate(R.layout.explorer_itemlistview,null);


        ImageView imagen = (ImageView) vista.findViewById(R.id.imageListView);
        TextView titulo = (TextView) vista.findViewById(R.id.textNombre);
        TextView fechaInit = (TextView) vista.findViewById(R.id.textFechaInit);
        TextView fechaFin = (TextView) vista.findViewById(R.id.textFechaFin);
        TextView duracion = (TextView) vista.findViewById(R.id.textDuracion);


        titulo.setText(ListaObjetos.get(position).getTitulo());
        fechaInit.setText("Fecha Inicio: "+ ListaObjetos.get(position).getFechaInit());
        fechaFin.setText("Fecha Fin: "+ ListaObjetos.get(position).getFechaFin());
        duracion.setText("Duracion: "+ ListaObjetos.get(position).getDuracion());
        imagen.setImageResource(ListaObjetos.get(position).getImagen());

        return vista;


    }
}
