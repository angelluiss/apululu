package com.example.apululu.activity;

import android.content.Context;
import android.content.Intent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.adapter.CustomListAdapter;
import com.example.apululu.adapter.PagerTabAdapter;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.model.Notification;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChataAndNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chata_and_notification);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        final ViewPager views = (ViewPager)findViewById(R.id.viewPager);
        LinearLayout buttonProfile = (LinearLayout) findViewById(R.id.llProfileButton);
        SwitchCompat scPlay = (SwitchCompat) findViewById(R.id.scPlayGame);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChataAndNotificationActivity.this, ProfileYouActivity.class);
                startActivity(intent);
            }
        });

        scPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChataAndNotificationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });





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
