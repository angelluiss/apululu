package com.example.apululu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apululu.R;

public class InsertMaleFemaleActivity extends AppCompatActivity {
    Button button = (Button) findViewById(R.id.buttonMale);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_male_female);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundResource(R.drawable.button_rounded_blue);
            }
        });
    }
}
