package com.example.a97cve.timetrackerv7.mActivitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a97cve.timetrackerv7.R;

/**
 * Actividad para ver la informacion
 * detallada de una actividad
 */
public class InfoActivity1 extends AppCompatActivity {


    private String tipo = "";
    private String nombre = "";
    private String descripcion = "";
    private String ubicacion = "";
    private String inicio = "";
    private String fin = "";
    private String duracion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        Bundle bundleRootProyecto = getIntent().getExtras();

        if (bundleRootProyecto != null) {
            tipo = bundleRootProyecto.getString("Tipo");
            nombre = bundleRootProyecto.getString("Nombre");
            descripcion = bundleRootProyecto.getString("Descripcion");
            ubicacion = bundleRootProyecto.getString("Ubicacion");
            inicio = bundleRootProyecto.getString("TiempoInicio");
            fin = bundleRootProyecto.getString("TiempoFin");
            duracion = bundleRootProyecto.getString("Duracion");

        }

        TextView tipo1 = (TextView) findViewById(R.id.tipoActividad);
        TextView nombre1 = (TextView) findViewById(R.id.nombre);
        TextView descripcion1 = (TextView) findViewById(R.id.descripcion);
        TextView ubicacion1 = (TextView) findViewById(R.id.ubicacion);
        TextView inicio1 = (TextView) findViewById(R.id.tiempoInicio);
        TextView fin1 = (TextView) findViewById(R.id.tiempoFinal);
        TextView duracion1 = (TextView) findViewById(R.id.duracion);

        tipo1.setText(tipo);
        nombre1.setText(nombre);
        descripcion1.setText(descripcion);
        ubicacion1.setText(ubicacion);
        inicio1.setText(inicio);
        fin1.setText(inicio);
        duracion1.setText(duracion);

        Switch switchInternet = (Switch) findViewById(R.id.switch3);
        switchInternet.setClickable(false);
        Switch switchInternet2 = (Switch) findViewById(R.id.switch4);
        switchInternet2.setClickable(false);

        Button btnVolver = (Button) findViewById(R.id.btnVolver);


        //CIERRA LA ACTIVIDAD SIN GUARDAR
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

