package com.example.a97cve.timetrackerv7.mActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a97cve.timetrackerv7.Core.GeneradorTicks;
import com.example.a97cve.timetrackerv7.Core.Proyecto;
import com.example.a97cve.timetrackerv7.Core.Reloj;
import com.example.a97cve.timetrackerv7.R;
import com.example.a97cve.timetrackerv7.mFragments.ConfigFragment;
import com.example.a97cve.timetrackerv7.mFragments.CronometroFragment;
import com.example.a97cve.timetrackerv7.mFragments.ExplorerFragment;
import com.example.a97cve.timetrackerv7.mFragments.InformeFragment;


public class MainActivity extends AppCompatActivity {


    /**
     * Class MainActivity
     * Actividad principal encargada de
     * gestionar y crear los fragmentos
     * en relacion a las tabs selecionadas
     *
     * @default ExplorerFragment
     */



    public Proyecto rootProyecto = new Proyecto();
    //BOTTONES TAB
    private TabLayout mTabLayout;
    //CARGA TODAS LAS VISTAS DE LOS FRAGMENTOS
    private ViewPager mViewPager;
    private boolean first= true;

    public void loadTabImagenes(){

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_explorer);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_timer);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_informe);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_config);
    }

    //Funcion para test
    public void inicializarProyecto(){
        rootProyecto.setNombre("GLOBAL");
        rootProyecto.crearSubProyecto("Proyecto 1 ", "Proyecto 1 des");
        rootProyecto.crearSubProyecto("Proyecto 2 ", "Proyecto 2 des");

        Proyecto p1= (Proyecto) rootProyecto.getSubProyectos().toArray()[0];
        p1.crearTarea("tarea", "tarea");
        p1.crearSubProyecto("SubProyecto1","Descripcion");
        Proyecto subProyecto = (Proyecto) p1.getSubProyectos().toArray()[0];
        subProyecto.crearSubProyecto("SubSub1","sub");
        subProyecto.crearTarea("Tarea1","Tarea");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int intervaloContador = 1000;
        GeneradorTicks gt = new GeneradorTicks(Reloj.getInstancia(), intervaloContador);

        //CARGA LA VISTA DEL LAYOUT
        setContentView(R.layout.activity_main);

        //CARGAMOS COMPONENTES TABS Y CONTENEDOR VIEWPAGER
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        ////////////////INIT ADAPTER//////////////
        //INICIALIZAMOS ADAPTADOR
        ViewPagerAdapter fragmentAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //ADD FRAGMENTS

        //inicializarProyecto(); //Funcion para test
        Bundle bundleRootProyecto = new Bundle();
        bundleRootProyecto.putSerializable("root",rootProyecto);

        //Crea y vincula los fragments
        ExplorerFragment explorerFragment = new ExplorerFragment();
        CronometroFragment cronometroFragment = new CronometroFragment();
        ConfigFragment configFragment = new ConfigFragment();
        InformeFragment informeFragment = new InformeFragment();
        explorerFragment.setArguments(bundleRootProyecto);
        cronometroFragment.setArguments(bundleRootProyecto);
        configFragment.setArguments(bundleRootProyecto);
        informeFragment.setArguments(bundleRootProyecto);

        fragmentAdapter.addFragment( explorerFragment);
        fragmentAdapter.addFragment(cronometroFragment);
        fragmentAdapter.addFragment(informeFragment);
        fragmentAdapter.addFragment(configFragment);


        ////////////////SETUP ADAPTER//////////////
        //ADAPTAMOS LAS VISTAS AL CONTENEDOR
        mViewPager.setAdapter(fragmentAdapter);

        //RELACIONAMOS LOS FRAGMENTOS VISTA POR TABS
        mTabLayout.setupWithViewPager(mViewPager);

        //LOAD ICONS TABS
         this.loadTabImagenes();


    }
}


