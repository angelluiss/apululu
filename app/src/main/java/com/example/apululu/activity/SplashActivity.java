package com.example.apululu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.apululu.utils.InternetStateService;
import com.example.apululu.utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(SplashActivity.this, LoginOrSingUPActivity.class);
        Intent intentMain = new Intent(SplashActivity.this, HomeActivity.class);

        Context thisContex = SplashActivity.this;
        startService(new Intent(thisContex, InternetStateService.class));

        if (!TextUtils.isEmpty(Util.getUserMailPrefs(prefs)) &&
                !TextUtils.isEmpty(Util.getUserPasswordPrefs(prefs))){
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }
        finish();
    }

}
