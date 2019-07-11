package com.example.apululu.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class InternetStateService extends Service {

    private SharedPreferences prefs;
    @Override
    public void onCreate(){
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Socket socket = this.getSocket();

        socket.connect();
        socket.on("hello", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                JSONObject data = (JSONObject) args[0];
                String hello;
                try {
                    hello = data.getString("hello");

                } catch (JSONException e) {
                    return;
                }
                Log.d("SocketMessage",hello);
            }
        });

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
    private Socket getSocket()
    {
        Socket socket = null;
        try {
            IO.Options opts = new IO.Options();
            opts.query = "token=" + Util.getTokenPrefs(prefs);
            socket = IO.socket("http://192.168.2.117:3000", opts);
        } catch (URISyntaxException e) {}
        return socket;
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
