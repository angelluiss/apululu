package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;

import es.dmoral.toasty.Toasty;

public class StudentAtributteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_atributte);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llBtnStudentLayout);

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
                    if (s.equals("other")) {
                        buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                        Intent intent1 = new Intent(StudentAtributteActivity.this, OtherAtributteActivity.class);
                        startActivity(intent1);
                    }else {
                        Intent intent = new Intent(StudentAtributteActivity.this, HomeActivity.class);
                        Toasty.success(StudentAtributteActivity.this, R.string.register_success, Toasty.LENGTH_SHORT, true).show();
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
