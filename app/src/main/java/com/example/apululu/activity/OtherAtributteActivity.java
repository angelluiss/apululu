package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.apululu.R;

import es.dmoral.toasty.Toasty;

public class OtherAtributteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_atributte);

        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llBtnOther);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherAtributteActivity.this, HomeActivity.class);
                Toasty.success(OtherAtributteActivity.this, R.string.register_success, Toasty.LENGTH_SHORT, true).show();
                startActivity(intent);
            }
        });


    }
}
