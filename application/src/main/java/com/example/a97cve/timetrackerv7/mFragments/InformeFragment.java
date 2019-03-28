package com.example.a97cve.timetrackerv7.mFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.a97cve.timetrackerv7.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class InformeFragment extends Fragment {


    /**
     * Class InformeFragment
     * Fragmento que nos permite
     * la generacion de un informe
     * selecionando sus propiedades.
     */
    DateFormat format = new SimpleDateFormat("d/M/yyyy,HH:mm");
    TimePickerDialog picker2;
    DatePickerDialog picker;
    EditText eTextIni;
    EditText eTextFin;
    Spinner periode;

    //DEVUELVE LA VISTA DEL FRAGMENTO INFORME
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab3_informe,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //CARGAMOS LOS EDITTEXT DE FECHAINICIO Y FECHAFIN PARA UTILZARLOS
        eTextIni = (EditText) getView().findViewById(R.id.editTextFechaInicio);
        eTextFin = (EditText) getView().findViewById(R.id.editTextFechaFin);
        periode = (Spinner) getView().findViewById(R.id.spinnerPeriode);



        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);


                Calendar tiempoInicio = Calendar.getInstance();
                Calendar tiempoFinal = Calendar.getInstance();

                switch ( position){
                    case 0: //PERSONALIZADO

                        break;
                    case 1: //HOY
                        tiempoInicio.set(Calendar.HOUR_OF_DAY,0);
                        tiempoInicio.set(Calendar.MINUTE,0);
                        tiempoFinal.set(Calendar.HOUR_OF_DAY,23);
                        tiempoFinal.set(Calendar.MINUTE,59);
                        break;
                    case 2: //AYER

                        tiempoInicio.set(Calendar.DAY_OF_MONTH,tiempoInicio.get(Calendar.DAY_OF_MONTH)-1);
                        tiempoInicio.set(Calendar.HOUR_OF_DAY,0);
                        tiempoInicio.set(Calendar.MINUTE,0);
                        tiempoFinal.set(Calendar.DAY_OF_MONTH,tiempoFinal.get(Calendar.DAY_OF_MONTH)-1);
                        tiempoFinal.set(Calendar.HOUR_OF_DAY,23);
                        tiempoFinal.set(Calendar.MINUTE,59);
                        break;
                    case 3://ESTA SEMANA/

                        while (tiempoInicio.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                            tiempoInicio.add(Calendar.DATE, -1);
                        }
                        tiempoInicio.set(Calendar.HOUR_OF_DAY,0);
                        tiempoInicio.set(Calendar.MINUTE,0);
                        tiempoFinal.set(Calendar.DAY_OF_MONTH,tiempoInicio.get(Calendar.DAY_OF_MONTH)+6);
                        tiempoFinal.set(Calendar.HOUR_OF_DAY,23);
                        tiempoFinal.set(Calendar.MINUTE,59);

                        break;
                    case 4: //ESTE MES
                        tiempoInicio.set(Calendar.DAY_OF_MONTH,tiempoInicio.getMinimum(Calendar.DAY_OF_MONTH));
                        tiempoInicio.set(Calendar.HOUR_OF_DAY,0);
                        tiempoInicio.set(Calendar.MINUTE,0);
                        tiempoFinal.set(Calendar.DAY_OF_MONTH,tiempoFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        tiempoFinal.set(Calendar.HOUR_OF_DAY,23);
                        tiempoFinal.set(Calendar.MINUTE,59);

                        break;

                    case 5: //MES PASADO

                        tiempoInicio.set(Calendar.MONTH,-1);
                        tiempoInicio.set(Calendar.DAY_OF_MONTH,tiempoInicio.getMinimum(Calendar.DAY_OF_MONTH));
                        tiempoInicio.set(Calendar.HOUR_OF_DAY,0);
                        tiempoInicio.set(Calendar.MINUTE,0);
                        tiempoFinal.set(Calendar.MONTH,-1);
                        tiempoFinal.set(Calendar.DAY_OF_MONTH,tiempoFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        tiempoFinal.set(Calendar.HOUR_OF_DAY,23);
                        tiempoFinal.set(Calendar.MINUTE,59);
                        break;
                }

                String inicio = format.format(tiempoInicio.getTime());
                String fin = format.format(tiempoFinal.getTime());
                eTextIni.setText(inicio);
                eTextFin.setText(fin);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //NO FUNCIONA
            }
        });


        //DATE CALENDAR INICIO

        //eTextIni.setInputType(InputType.TYPE_NULL);

        eTextIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);

                //TIME PICKER DIALOG
                picker2 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eTextIni.setText(eTextIni.getText()+","+hourOfDay+":"+minute);
                    }
                },hourOfDay,minute, true);
                picker2.show();

                // DATE PICKER DIALOG
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextIni.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();


            }
        });

        //DATE CALENDAR FINAL

        // eTextFin.setInputType(InputType.TYPE_NULL);
        eTextFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hourOfDay = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);

                //TIME PICKER DIALOG
                picker2 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eTextFin.setText(eTextFin.getText()+","+hourOfDay+":"+minute);
                    }
                },hourOfDay,minute, true);
                picker2.show();

                // DATE PICKER DIALOG
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextFin.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();


            }
        });


        //BUTTON CREAR
        Button btn1 = (Button) getView().findViewById(R.id.btnGenerarInforme);
        btn1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Informe Generado Correctamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
