package com.example.apululu.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class InternetStateService extends Service {

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){
        // Delay de 5 minutos para verificar conexion a internet
        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Log.d("connection", networkInfo.toString());
                } else {
                    Log.d("no connection", "There is no connection" );
                }
            }
        }, 1000,300000);

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
