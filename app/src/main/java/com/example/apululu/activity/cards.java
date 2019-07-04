package com.example.apululu.activity;

public class cards {
    private String userId;
    private String name;
    public cards (String userdId, String name){
        this.userId = userdId;
        this.name = name;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
