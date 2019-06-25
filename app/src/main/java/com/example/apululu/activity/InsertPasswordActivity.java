package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class InsertPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_password);

        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonPassword);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertPasswordActivity.this,InsertNameActivity.class);
                startActivity(intent);
            }
        });
    }
}
