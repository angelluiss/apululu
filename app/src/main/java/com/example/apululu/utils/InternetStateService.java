package com.example.apululu.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.apululu.R;
import com.example.apululu.activity.ChataAndNotificationActivity;
import com.example.apululu.activity.ProfileYouActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class InternetStateService extends Service {

    private String hello;
    private SharedPreferences prefs;
    private NotificationHandler notificationHandler;

    @Override
    public void onCreate(){
        notificationHandler = new NotificationHandler(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess){

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Socket socket = this.getSocket();

        socket.connect();
        socket.on("new notification", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                JSONObject hello;
                try {
                    JSONObject data =  new JSONObject((String) args[0]);
                    hello = data.getJSONObject("from");
                    String dato = hello.toString();
                    Log.d("SocketMessage",dato);
                    sendNotification("like");
                } catch (JSONException e) {
                    String error = e.toString();
                    Log.d("SocketMessage",error);
                    return;
                }

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

    private void sendNotification(String message){
        String title = "Like";

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){
            boolean highNotification = true;

            Notification.Builder nb = notificationHandler.createNotification(title,message, highNotification);

            notificationHandler.getManager().notify(1,nb.build());
        }
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
