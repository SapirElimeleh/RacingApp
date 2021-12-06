package com.example.racingapp.Models;

public class MapSpot {
    private double latitude;
    private double longitude;


    public MapSpot(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MapSpot() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
