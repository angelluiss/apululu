package com.example.apululu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apululu.R;
import com.example.apululu.adapter.PagerTabAdapter;

public class ChataAndNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chata_and_notification);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        final ViewPager views = (ViewPager)findViewById(R.id.viewPager);

        // **** Creaciòn de los tabs
        tabs.addTab(tabs.newTab().setText("CHAT"));
        tabs.addTab(tabs.newTab().setText("NOTIFICATIONS"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter adapter = new PagerTabAdapter(getSupportFragmentManager(),tabs.getTabCount());

        views.setAdapter(adapter);
        views.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        int position = tab.getPosition();
                        views.setCurrentItem(position);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        // ...
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        // ...
                    }
                }
        );





    }
}
