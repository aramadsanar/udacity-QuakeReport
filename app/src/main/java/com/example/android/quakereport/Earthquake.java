package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {
    private float magnitude;
    private String location;
    private long timeInMilis;
    private String date;
    private String time;
    private String url;

    public String getTime() {
        return time;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public Earthquake(float magnitude, String location, long timeInMilis, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMilis = timeInMilis;
        this.date = date;
        this.time = time;
        this.url = url;

        Date dateParse = new Date(timeInMilis);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");

        date = dateFormatter.format(dateParse);
        dateFormatter.applyPattern("HH:mm a");
        time = dateFormatter.format(dateParse);
    }
}
