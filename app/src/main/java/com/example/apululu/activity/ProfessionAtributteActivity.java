package com.example.apululu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;

public class ProfessionAtributteActivity extends AppCompatActivity {
    String professionParameter;
    String studentParameter;
    String otherParameter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_atributte);

        LinearLayout buttonNext = (LinearLayout)findViewById(R.id.llBtnProfessionNext);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            professionParameter = parametros.getString("profession");
            studentParameter = parametros.getString("student");
            otherParameter = parametros.getString("other");
        }

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (professionParameter != null){
                    Toast.makeText(ProfessionAtributteActivity.this,professionParameter,Toast.LENGTH_LONG).show();
                }else if(studentParameter != null){
                    Toast.makeText(ProfessionAtributteActivity.this,studentParameter,Toast.LENGTH_LONG).show();
                }else if (otherParameter != null){
                    Toast.makeText(ProfessionAtributteActivity.this,otherParameter,Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(ProfessionAtributteActivity.this,professionParameter,)
                }
            }
        });




    }
}
