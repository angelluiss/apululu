package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class InsertMaleFemaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_male_female);
        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonMaleFemale);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setBackgroundResource(R.drawable.button_rounded_blue);
                Intent intent = new Intent(InsertMaleFemaleActivity.this, InsertCalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
