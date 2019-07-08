package com.example.apululu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.apululu.fragment.Profile1Fragment;
import com.example.apululu.fragment.Profile2Fragment;

public class ViewProfilesDatesAdapter extends FragmentPagerAdapter {

    private int numberViews;

    public ViewProfilesDatesAdapter(FragmentManager fm, int numberViews) {
        super(fm);
        this.numberViews = numberViews;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Profile1Fragment();
            case 1:
                return new Profile2Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberViews;
    }
}
