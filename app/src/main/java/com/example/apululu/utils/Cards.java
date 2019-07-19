package com.example.apululu.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Cards {

    private int imageBitmap;
    private String name;
    private String location;
    private int userID;

    public Cards(int imageBitmap, String name, String location, Integer userID) {
        this.imageBitmap = imageBitmap;
        this.name = name;
        this.location = location;
        this.userID = userID;
    }

    public int getDrawable() {
        return imageBitmap;
    }

    public void setDrawable(int imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
}
