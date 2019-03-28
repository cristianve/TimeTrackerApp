package com.example.a97cve.timetrackerv7.mActivitys;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a97cve.timetrackerv7.Core.Tarea;
import com.example.a97cve.timetrackerv7.R;

import java.util.List;

/**
 * Class AdaptadorTarea
 * Gestiona los valores de
 * Tarea para adaptarlos a la
 * vista con sus respectivos
 * valores
 */
public class AdaptadorTarea extends BaseAdapter{

    private static LayoutInflater inflater = null;
    Context contexto;
    List<DatosActividades> ListaObjetos;
    List<Tarea> ListaTareas;

    public AdaptadorTarea(Context contexto, List<DatosActividades> listaObjetos, List<Tarea> listaTareas) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
        ListaTareas = listaTareas;
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
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RecyclerView.ViewHolder viewHolder;
        View vista = convertView;
        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista = inflater.inflate(R.layout.cronometro_itemlistview2, null);
        final int pos = position;

        TextView titulo = (TextView) vista.findViewById(R.id.textNombreTarea);
        final TextView duracion = (TextView) vista.findViewById(R.id.textDuracion);
        final ImageButton imagen = (ImageButton) vista.findViewById(R.id.imageButtonTarea);

        titulo.setText(ListaObjetos.get(position).getTitulo());
        duracion.setText("Duracion: " + ListaObjetos.get(position).getDuracion());

        Tarea t =  ListaTareas.get(pos);
        if(!t.isActivo())
        {
            //imagen.setImageResource(ListaObjetos.get(position).getImagen());
            imagen.setImageResource(R.drawable.ic_play);
            imagen.setTag("play");
        }
        else
        {
            imagen.setImageResource(R.drawable.ic_stop);
            imagen.setTag("stop");
        }

        //Onclick Boton play/pausa de la tarea
        //Cambia el boton a play/pausa y inicia/para la tarea
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Tarea t =  ListaTareas.get(pos);

                if (imagen.getTag().equals("play"))
                {
                    Toast.makeText(contexto, "Play", Toast.LENGTH_SHORT).show();
                    t.iniciarIntervalo();
                    imagen.setImageResource(R.drawable.ic_stop);
                    imagen.setTag("pause");
                    imagen.invalidate();
                }
                else if (imagen.getTag().equals("stop"))
                {
                    Toast.makeText(contexto, "Stop", Toast.LENGTH_SHORT).show();
                    t.acabarIntervalo();
                    imagen.setImageResource(R.drawable.ic_play);
                    imagen.setTag("play");
                    duracion.setText("Duracion: " + t.getIntervalo());
                    duracion.invalidate();
                    imagen.invalidate();
                }
            }
        });
        return vista;
    }
}


