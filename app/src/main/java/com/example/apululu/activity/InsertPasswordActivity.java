package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apululu.R;

public class InsertPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_password);

        // Instanciaciòn del los Widgets
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonPasswordNext);
        final TextView password = (TextView) findViewById(R.id.etPassword);
        final TextView repeatPassword = (TextView) findViewById(R.id.etRepeatPassword);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordString = password.getText().toString();
                if (passwordString.length() < 8){
                    password.setError("Your Password must have at least 8 chars");
                }
            }
        });
        repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String repeatpasswordString = repeatPassword.getText().toString();
                if (repeatpasswordString.length() < 8){
                    repeatPassword.setError("Your Password must have at least 8 chars");
                }
            }
        });



        // Comprobación de los formularios
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvpassword = password.getText().toString();
                String tvrepeatPassword = repeatPassword.getText().toString();
                if (tvpassword.equals(tvrepeatPassword)){

                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    Toast.makeText(InsertPasswordActivity.this,"Password valid",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(InsertPasswordActivity.this,InsertNameActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(InsertPasswordActivity.this,"Password does not matched",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
