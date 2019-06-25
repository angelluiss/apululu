package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class InsertEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_email);

        //setContentView(R.layout.activity_insert_email);

        // Click para cambiar de activity
        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertEmailBtn);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(InsertEmailActivity.this,VerificationEmailActivity.class);
                startActivity(intent2);
            }
        });

    }
}
