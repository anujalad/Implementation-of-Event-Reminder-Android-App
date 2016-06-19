package com.example.anuja.androidproject.androidproject.Others;

import java.io.Serializable;

/**
 * Created by anuja on 4/28/2016.
 */
public class Event implements Serializable {
    private String date;
    private String time;
    private String description;
    private byte[] image;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
