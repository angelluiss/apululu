package com.example.apululu.utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPrefs(SharedPreferences preferences) {
        return preferences.getString("email","");
    }

    public static String getUserPasswordPrefs(SharedPreferences preferences) {
        return preferences.getString("pass","");
    }

    public static String getTokenPrefs(SharedPreferences preferences) {
        return preferences.getString("token","");
    }
}

