package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.apululu.R;

public class InsertOtherActivity extends AppCompatActivity {

    int casesOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_other);

        final LinearLayout professionLayout = (LinearLayout) findViewById(R.id.llBtnProfession);
        final LinearLayout studentLayout = (LinearLayout) findViewById(R.id.llBtnStudent);
        final LinearLayout otherLayout = (LinearLayout) findViewById(R.id.llBtnOther);
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonOtherNext);

        final CheckBox professionCB = (CheckBox) findViewById(R.id.cbProfession);
        final CheckBox studentCB = (CheckBox) findViewById(R.id.cbStudent);
        final CheckBox otherCB = (CheckBox) findViewById(R.id.cbOther);


        // Profession Click Listeners.... Eventos de cambios en la UI
        professionCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!professionCB.isChecked()){
                    professionCB.setChecked(true);
                    casesOther++;
                    professionLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    professionCB.setChecked(false);
                    casesOther--;
                    professionLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });
        professionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!professionCB.isChecked()){
                    professionCB.setChecked(true);
                    casesOther++;
                    professionLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    professionCB.setChecked(false);
                    casesOther--;
                    professionLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });

        // Student Click Listeners.... Eventos de cambios en la UI
        studentCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!studentCB.isChecked()){
                    studentCB.setChecked(true);
                    casesOther++;
                    studentLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    studentCB.setChecked(false);
                    casesOther--;
                    studentLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });
        studentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!studentCB.isChecked()){
                    studentCB.setChecked(true);
                    casesOther++;
                    studentLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    studentCB.setChecked(false);
                    casesOther--;
                    studentLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });

        // Student Click Listeners.... Eventos de cambios en la UI
        otherCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otherCB.isChecked()){
                    otherCB.setChecked(true);
                    casesOther++;
                    otherLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    otherCB.setChecked(false);
                    casesOther--;
                    otherLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });
        otherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otherCB.isChecked()){
                    otherCB.setChecked(true);
                    casesOther++;
                    otherLayout.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    otherCB.setChecked(false);
                    casesOther--;
                    otherLayout.setBackgroundResource(R.drawable.button_rounded_gray);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);

                if(professionCB.isChecked()){
                    Bundle parametros = new Bundle();
                    Intent intent = new Intent(InsertOtherActivity.this,ProfessionAtributteActivity.class);
                    intent.putExtra("Profession","profession");
                    startActivity(intent);
                }else if(studentCB.isChecked()){
                    Intent intent = new Intent(InsertOtherActivity.this,StudentAtributteActivity.class);
                    intent.putExtra("Student", "student");
                    startActivity(intent);
                }else if(otherCB.isChecked()){
                    Intent intent = new Intent(InsertOtherActivity.this,OtherAtributteActivity.class);
                    intent.putExtra("Other", "other");
                    startActivity(intent);
                }else{
                    //Intent Activity Success Register
                }
            }
        });
    }
}
