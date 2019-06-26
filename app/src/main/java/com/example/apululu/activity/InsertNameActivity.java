package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;

public class InsertNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonName);
        final EditText firtsName = (EditText) findViewById(R.id.etName);
        final EditText lastName = (EditText) findViewById(R.id.etLastName);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firtsNameV = firtsName.getText().toString();
                String lastNameV = lastName.getText().toString();
                if(!firtsNameV.isEmpty() && !lastNameV.isEmpty()){
                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    Intent intent4 = new Intent(InsertNameActivity.this,InsertMaleFemaleActivity.class);
                    startActivity(intent4);
                }else if(firtsNameV.isEmpty()){
                    Toast.makeText(InsertNameActivity.this,"Insert your First Name", Toast.LENGTH_LONG).show();
                }else if(lastNameV.isEmpty()){
                    Toast.makeText(InsertNameActivity.this,"Insert your Last Name", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
