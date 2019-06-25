package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class VerificationEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_email);
        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llVerificationBtn);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationEmailActivity.this, InsertPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
