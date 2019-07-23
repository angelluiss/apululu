package com.example.apululu.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Notification {

    private Integer iconID;
    private String tittleNotification;
    private String contentNotifications;
    private int statusNotification;
    private JSONArray noticationFrom;
    private int numberNotifications;
    private String typeNotification;

    public Notification(Integer iconID, String name, String content, String typeNotification,JSONArray notification, int numberNotifications) {
        this.iconID = iconID;
        this.tittleNotification = name;
        this.contentNotifications = content;
        this.noticationFrom = notification;
        this.numberNotifications = numberNotifications;
        this.typeNotification = typeNotification;
    }

    public Integer getIconID() {
        return iconID;
    }

    public String gettypeNotification() {
        return typeNotification;
    }

    public void setIconID(Integer iconID) {
        this.iconID = iconID;
    }

    public String getTittleNotification() {
        return tittleNotification;
    }

    public void setTittleNotification(String tittleNotification) {
        this.tittleNotification = tittleNotification;
    }

    public String getContentNotifications() {
        return contentNotifications;
    }

    public void setContentNotifications(String contentNotifications) {
        this.contentNotifications = contentNotifications;
    }

    public JSONArray getJSONarrayNotifications() {
        return noticationFrom;
    }

    public int getNumberNotifications() {
        return numberNotifications;
    }
}
