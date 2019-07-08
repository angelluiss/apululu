package com.example.apululu.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import android.support.annotation.Nullable;

public class InternetStateService extends Service {

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
        } else {
            // No hay conexión a Internet en este momento
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy(){

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
