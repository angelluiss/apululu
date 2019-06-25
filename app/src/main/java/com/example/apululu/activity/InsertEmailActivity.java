package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.apululu.R;

public class InsertEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_email);

        // Instancias de los Widgets
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertEmailBtn);
        final EditText email = (EditText) findViewById(R.id.etEmail);
        final CheckBox termAndagreedments = (CheckBox) findViewById(R.id.cbPrivacy);


        // Evento click de boton Next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                boolean termsChecked = termAndagreedments.isChecked();
                if (emailText.matches(emailPattern) && termsChecked)
                {
                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(InsertEmailActivity.this,VerificationEmailActivity.class);
                    startActivity(intent2);
                }
                else if (!emailText.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                }else if(!termsChecked){
                    Toast.makeText(getApplicationContext(),"You must accept Terms and Conditions",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
