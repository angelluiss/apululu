package com.example.apululu.model;

import org.json.JSONArray;

public class ChatList {
    private Integer imageChat;
    private String NameChat;
    private String LastName;

    public ChatList(Integer imageChat, String NameChat, String LastName) {
        this.imageChat = imageChat;
        this.NameChat = NameChat;
        this.LastName = LastName;
    }

    public Integer getImageChat() {
        return imageChat;
    }

    public String getNameChat() {
        return NameChat;
    }

    public String getLastName() {
        return LastName;
    }


}
