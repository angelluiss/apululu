package com.example.apululu.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apululu.R;
import com.example.apululu.model.Notification;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListAdapter extends ArrayAdapter {

    // to reference the Activity
    private final Activity context;

    private ArrayList<Notification> notificationArrayList;

    public CustomListAdapter(@NonNull Activity context, ArrayList<Notification> notificationArrayList) {
        super(context, R.layout.listview_row, notificationArrayList);
        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }


    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.listview_row, null, true);

        //This code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.tvNotificationTitle);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.tvNotificationContent);
        CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.ivNotification1);


        //This code sets the values of the objects to values from the arrays

        nameTextField.setText(notificationArrayList.get(position).getTittleNotification());

        infoTextField.setText(R.string.notification_like_message);


        imageView.setBackgroundResource(notificationArrayList.get(position).getIconID());

        return rowView;
    }






}
