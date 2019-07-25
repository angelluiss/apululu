package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apululu.R;


public class LoginOrSingUPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sing_up);

        // Click para cambiar de activity
        LinearLayout registerText = (LinearLayout) findViewById(R.id.llSingUp);
        LinearLayout loginEmailButton = (LinearLayout) findViewById(R.id.llEmailLogin);


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginOrSingUPActivity.this,InsertEmailActivity.class);
                startActivity(intent1);
            }
        });

        loginEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginOrSingUPActivity.this,LoginActivity.class);
                startActivity(intent2);
            }
        });
    }
}
