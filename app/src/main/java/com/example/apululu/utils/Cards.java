package com.example.apululu.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Cards {

    private int imageBitmap;
    private String name;
    private String location;

    public Cards(int imageBitmap, String name, String location) {
        this.imageBitmap = imageBitmap;
        this.name = name;
        this.location = location;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
}
