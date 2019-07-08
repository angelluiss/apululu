package com.example.apululu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.apululu.fragment.ChatFragment;
import com.example.apululu.fragment.NotificationFragment;

public class PagerTabAdapter extends FragmentStatePagerAdapter {

    private int numberTabs;

    public PagerTabAdapter(FragmentManager fm, int numberTabs) {
        super(fm);
        this.numberTabs = numberTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new NotificationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTabs;
    }
}
