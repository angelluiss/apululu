package com.example.apululu.model;

public class GetNotifications {

    private Integer iconID;
    private String name;
    private String location;
    private int userID;

    public GetNotifications(int imageBitmap, String name, String location, Integer userID) {
        this.imageBitmap = imageBitmap;
        this.name = name;
        this.location = location;
        this.userID = userID;
    }

}
