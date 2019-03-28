package com.example.a97cve.timetrackerv7.mActivitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Class ViewPagerAdapter
 * Adaptador encargado de
 * generar un listado de
 * fragmentos y su gestion.
 *
 */
class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }


    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
}
