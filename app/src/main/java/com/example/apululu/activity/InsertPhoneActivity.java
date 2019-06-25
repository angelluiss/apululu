package com.example.apululu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.apululu.R;

public class InsertPhoneActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_phone);

        final EditText phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonPhone);

        phoneNumber.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    String phone;
                    phone = phoneNumber.getText().toString();
                    if (phone.length() >= 9 ){
                        buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    }else{
                        buttonNext.setBackgroundResource(R.drawable.button_rounded_white);
                    }
                    return false;
                }
            });
             /*buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertPhoneActivity.this, );
            }
        }); */
    }
}
