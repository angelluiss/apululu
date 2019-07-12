package com.example.apululu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.apululu.R;

public class InsertPhoneActivity extends AppCompatActivity {
    String[] registro;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_phone);

        final EditText phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonPhone);

        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone;
                phone = phoneNumber.getText().toString();
                if (phone.length() >= 9){
                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    registro[7] = phone;
                    Intent intent = new Intent(InsertPhoneActivity.this, InsertOtherActivity.class);
                    intent.putExtra("registro",registro);
                    startActivity(intent);
                }else{
                    Toast.makeText(InsertPhoneActivity.this, "Insert a valid phone number", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
