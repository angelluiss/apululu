package com.example.apululu.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apululu.R;
import com.example.apululu.model.ChatList;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends ArrayAdapter {

    private final Activity context;

    private ArrayList<ChatList> chatArrayList;

    public ChatListAdapter(@NonNull Activity context, ArrayList<ChatList> chatArrayList) {
        super(context, R.layout.list_view_chat, chatArrayList);
        this.context = context;
        this.chatArrayList = chatArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.list_view_chat, null, true);

        //This code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.tvChatTitle);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.tvChatContent);
        CircleImageView imageView = (CircleImageView) rowView.findViewById(R.id.ivChat1);

        //This code sets the values of the objects to values from the arrays

        nameTextField.setText(chatArrayList.get(position).getNameChat());

        infoTextField.setText(chatArrayList.get(position).getLastName());

       // imageView.setBackgroundResource();
        imageView.setBackground(chatArrayList.get(position).getImageChat());
        return rowView;
    }
}
