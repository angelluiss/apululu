package com.example.apululu.model;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;

public class ChatList {
    private Drawable imageChat;
    private String NameChat;
    private String LastName;

    public ChatList(Drawable imageChat, String NameChat, String LastName) {
        this.imageChat = imageChat;
        this.NameChat = NameChat;
        this.LastName = LastName;
    }

    public Drawable getImageChat() {
        return imageChat;
    }

    public String getNameChat() {
        return NameChat;
    }

    public String getLastName() {
        return LastName;
    }


}
