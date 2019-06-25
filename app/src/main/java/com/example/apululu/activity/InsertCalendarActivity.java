package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class InsertCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_calendar);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertAge);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertCalendarActivity.this,InsertPhoneActivity.class);
                startActivity(intent);
            }
        });


    }
}
