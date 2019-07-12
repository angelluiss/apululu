package com.example.apululu.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import com.example.apululu.R;

public class NotificationHandler extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH_CHANNEL";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_LOW_NAME = "LOW_CHANNEL";


    public NotificationHandler(Context context){
        super(context);
        createChannel();
    }

    public NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT >= 26){
            // Creating HIHG CHANNEL
            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);

            // .... Extra Config ...

            highChannel.enableLights(true);
            highChannel.enableVibration(true);
         //   highChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            highChannel.setLightColor(Color.BLUE);
            highChannel.setShowBadge(true);

            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);


            NotificationChannel lowChannel = new NotificationChannel(
                    CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);


        }
    }

    public Notification.Builder createNotification (String title, String message,boolean isHighImportance){
        if (Build.VERSION.SDK_INT >= 26){
            if (isHighImportance){
                return this.createNotificationWithChannel(title,message,CHANNEL_HIGH_NAME);
            }
            return this.createNotificationWithChannel(title,message,CHANNEL_LOW_NAME);
        }
        return this.createNotificationWithoutChannel(title, message);
    }

    private Notification.Builder createNotificationWithChannel (String title, String message, String channelID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return new Notification.Builder(getApplicationContext(),channelID).setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.heart_1_like).setAutoCancel(true);
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel (String title, String message) {
        return new Notification.Builder(getApplicationContext()).setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.heart_1_like).setAutoCancel(true);
    }
}
