package com.example.anuja.androidproject.androidproject.Others;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anuja.androidproject.androidproject.Fragments.First_Fragment;
import com.example.anuja.androidproject.androidproject.Fragments.Second_Fragment;

/**
 * Created by anuja on 4/20/2016.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter{

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Tab 1";
        if(position==1)
            return "Tab 2";
       return "";
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new First_Fragment();
        if(position==1)
            return new Second_Fragment();

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
