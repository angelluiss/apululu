package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;

import java.util.ArrayList;

public class ProfessionAtributteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_atributte);

        LinearLayout buttonNext = (LinearLayout)findViewById(R.id.llBtnProfessionNext);

        Bundle parametros = this.getIntent().getExtras();
        String[] professionParameter = new String[0];
        if (parametros != null) {
            professionParameter = parametros.getStringArray("Selected");
        }

        final String[] finalProfessionParameter = professionParameter;
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert finalProfessionParameter != null;
                for (String s : finalProfessionParameter) {
                    if (s.equals("student")) {
                        Intent intent = new Intent(ProfessionAtributteActivity.this,StudentAtributteActivity.class);
                        Toast.makeText(ProfessionAtributteActivity.this, "student", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else if (s.equals("other")) {
                        Intent intent1 = new Intent(ProfessionAtributteActivity.this, OtherAtributteActivity.class);
                        Toast.makeText(ProfessionAtributteActivity.this, "other", Toast.LENGTH_LONG).show();
                        startActivity(intent1);
                    }
                }
            }
        });

    }
}
