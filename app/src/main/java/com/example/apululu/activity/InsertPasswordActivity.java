package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apululu.R;

public class InsertPasswordActivity extends AppCompatActivity {
    String[] registro;
    String passwordString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_password);

        // Instanciaciòn del los Widgets
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonPasswordNext);
        final EditText password = (EditText) findViewById(R.id.etPassword);
        final EditText repeatPassword = (EditText) findViewById(R.id.etRepeatPassword);

        // Obtención de los parametros de registro
        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordString = password.getText().toString();
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
                    registro[1] = passwordString;
                    Intent intent = new Intent(InsertPasswordActivity.this,InsertNameActivity.class);
                    intent.putExtra("registro",registro);
                    startActivity(intent);
                }else{
                    Toast.makeText(InsertPasswordActivity.this,"Password does not matched",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
