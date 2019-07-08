package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        //*** Instancia de los Widgets del Menu
        LinearLayout buttonProfile = (LinearLayout) findViewById(R.id.llProfileButton);
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat scPlay = (SwitchCompat) findViewById(R.id.scPlayGame);



        // *** Click listener para pasar de actividades 
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, ProfileYouActivity.class);
                startActivity(intent);
            }
        });

        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, ChataAndNotificationActivity.class);
                startActivity(intent);
            }
        });

        scPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
