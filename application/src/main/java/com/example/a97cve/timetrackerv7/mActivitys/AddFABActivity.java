package com.example.a97cve.timetrackerv7.mActivitys;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a97cve.timetrackerv7.Core.Actividad;
import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class AddFABActivity
 * Actividad para gestionar
 * la creacion de actividades
 * nuevas ( Proyectos, Tareas
 * Intervalos)
 */
public class AddFABActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Boolean limite=false;
    Boolean programada=false;
    Actividad rootProyecto ;
    EditText eTextLimite;
    EditText eTextIni;
    EditText eTextFin;
    Switch sLimite;
    Switch sProgramada;
    TextView tareaTitulo;
    TextView tareaTitulo2;
    TextView tareasSubtitulo;
    TextView tareasSubtitulo1;
    TextView tareasSubtitulo2;

    DateFormat format = new SimpleDateFormat("d/M/yyyy,HH:mm");

    TimePickerDialog picker2;
    DatePickerDialog picker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fab);


        //Carga layout y proyecto en el cual se va a crear la nueva actividad
        Bundle bundleRootProyecto = getIntent().getExtras();
        if(bundleRootProyecto!=null){
            rootProyecto = (Actividad) bundleRootProyecto.get("proyecto");
        }

        //BOTONES CREAR Y CANCELAR
        Button btnCrear = (Button) findViewById(R.id.btnCrear);
        Button btnCancelar = (Button) findViewById(R.id.btnVolver);

        //CARGAMOS LOS EDITTEXT DE FECHAINICIO Y FECHAFIN PARA UTILZARLOS
        eTextLimite = (EditText) findViewById(R.id.editTextFechaLimite);
        eTextIni = (EditText) findViewById(R.id.editTextFechaInicioAdd);
        eTextFin = (EditText) findViewById(R.id.editTextFechaFinAdd);
        sLimite = (Switch) findViewById(R.id.switch1);
        sProgramada = (Switch) findViewById(R.id.switch2);
        Spinner tipoActividad  = (Spinner) findViewById(R.id.spinnerTipoAct) ;

        tareaTitulo = (TextView) findViewById(R.id.textUbic3);
        tareaTitulo2 = (TextView) findViewById(R.id.textUbic5);

        tareasSubtitulo = (TextView) findViewById(R.id.textViewTiempoLimite2);
        tareasSubtitulo1 = (TextView) findViewById(R.id.textViewDesde);
        tareasSubtitulo2 = (TextView) findViewById(R.id.textViewHasta);

        decoratorTareaInvisible();

        tipoActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        decoratorTareaInvisible();
                        break;
                    case 1: //tarea
                        decoratorTareaVisible();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sLimite.setOnCheckedChangeListener(this);
        sProgramada.setOnCheckedChangeListener(this);
        eTextLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(limite) {
                    getTimeLimite();
                }
            }
        });

        eTextIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(programada){
                    getTimeProgramadaIni();
                }
            }});

        eTextFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(programada){
                    getTimeProgramadaFin();
                }

            }});




        //CREA LA ACTIVIDAD Y GUARDA
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner mySpinner = (Spinner) findViewById(R.id.spinnerTipoAct);
                String tipoActividad = mySpinner.getSelectedItem().toString();
                EditText et=(EditText) findViewById(R.id.editText4);
                String nombreActividad=et.getText().toString();
                et=(EditText) findViewById(R.id.editTextDescrip);
                String descripcionActividad=et.getText().toString();

                if(tipoActividad.equals("Proyecto"))
                {

                    Proyecto p = (Proyecto)rootProyecto;
                    p.crearSubProyecto(nombreActividad,descripcionActividad);
                }
                else if (tipoActividad.equals("Tarea")){
                    rootProyecto.crearTarea(nombreActividad,descripcionActividad);
                }


                Intent resultIntent= new Intent();
                resultIntent.putExtra("proyectoActualizado",rootProyecto);
                setResult(RESULT_OK,resultIntent);
                finish();

            }
        });

        //CIERRA LA ACTIVIDAD SIN GUARDAR
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void decoratorTareaVisible(){
        eTextLimite.setVisibility(View.VISIBLE);
        eTextIni.setVisibility(View.VISIBLE);
        eTextFin.setVisibility(View.VISIBLE);
        sLimite.setVisibility(View.VISIBLE);
        sProgramada.setVisibility(View.VISIBLE);
        tareaTitulo.setVisibility(View.VISIBLE);
        tareaTitulo2.setVisibility(View.VISIBLE);
        tareasSubtitulo.setVisibility(View.VISIBLE);
        tareasSubtitulo1.setVisibility(View.VISIBLE);
        tareasSubtitulo2.setVisibility(View.VISIBLE);
        eTextLimite.setVisibility(View.INVISIBLE);
        eTextIni.setVisibility(View.INVISIBLE);
        eTextFin.setVisibility(View.INVISIBLE);
    }


    /**
     * Metodos necesarios para dotar de inteligencia al layout.
     * Si la actividad a añadir es de tipo tarea se muestran unos nuevos campos
     * Si se quiere programar la tarea o poner limite se despliegan nuevos
     * campos al activar el switch
     */

    public void decoratorTareaInvisible(){
        eTextLimite.setVisibility(View.INVISIBLE);
        eTextIni.setVisibility(View.INVISIBLE);
        eTextFin.setVisibility(View.INVISIBLE);
        sLimite.setVisibility(View.INVISIBLE);
        sProgramada.setVisibility(View.INVISIBLE);
        tareaTitulo.setVisibility(View.INVISIBLE);
        tareaTitulo2.setVisibility(View.INVISIBLE);
        tareasSubtitulo.setVisibility(View.INVISIBLE);
        tareasSubtitulo1.setVisibility(View.INVISIBLE);
        tareasSubtitulo2.setVisibility(View.INVISIBLE);
        eTextIni.setVisibility(View.INVISIBLE);
        eTextFin.setVisibility(View.INVISIBLE);

    }


    public void getTimeLimite( ){

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
        int minute = cldr.get(Calendar.MINUTE);

        //TIME PICKER DIALOG
        picker2 = new TimePickerDialog(AddFABActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eTextLimite.setText(eTextLimite.getText()+","+hourOfDay+":"+minute);
            }
        },hourOfDay,minute, true);
        picker2.show();

        // DATE PICKER DIALOG
        picker = new DatePickerDialog(AddFABActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eTextLimite.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();

    }

    public void getTimeProgramadaIni( ){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
        int minute = cldr.get(Calendar.MINUTE);

        //TIME PICKER DIALOG
        picker2 = new TimePickerDialog(AddFABActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eTextIni.setText(eTextIni.getText()+","+hourOfDay+":"+minute);
            }
        },hourOfDay,minute, true);
        picker2.show();

        // DATE PICKER DIALOG
        picker = new DatePickerDialog(AddFABActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eTextIni.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();

    }


    public void getTimeProgramadaFin( ){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
        int minute = cldr.get(Calendar.MINUTE);

        //TIME PICKER DIALOG
        picker2 = new TimePickerDialog(AddFABActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eTextFin.setText(eTextFin.getText()+","+hourOfDay+":"+minute);
            }
        },hourOfDay,minute, true);
        picker2.show();

        // DATE PICKER DIALOG
        picker = new DatePickerDialog(AddFABActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eTextFin.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();

    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //Si se activa el switch muestra campos adicionales
        if(sLimite.isChecked()){
            eTextLimite.setVisibility(View.VISIBLE);
            limite=true;
        }else{
            eTextLimite.setVisibility(View.INVISIBLE);
            limite=false;
        }

        if(sProgramada.isChecked()){
            eTextIni.setVisibility(View.VISIBLE);
            eTextFin.setVisibility(View.VISIBLE);
            programada=true;
        }else{
            eTextIni.setVisibility(View.INVISIBLE);
            eTextFin.setVisibility(View.INVISIBLE);
            programada=false;
        }
    }
}
