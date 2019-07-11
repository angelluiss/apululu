package com.example.apululu.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.apululu.R;
import com.example.apululu.adapter.PagerTabAdapter;

public class ChataAndNotificationActivity extends AppCompatActivity {

    String[] nameArray = {"Karla Peña", "Sofia Vergara", "Mia Khalifa", "Amanda Rimkjock"};
    String[] infoArray = {"It's Matched with you", "Likes you", "Send you a message", "Send you a message"};
    Integer[] imageArray = {R.drawable.heart_1_like, R.drawable.heart_1_like,R.drawable.feed_icon_big,R.drawable.feed_icon_big};

    ListView listView;

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
