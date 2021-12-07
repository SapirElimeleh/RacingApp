package com.example.racingapp.Models;

import java.util.Comparator;

public class Record {
    private long date;
    private int score;
    private MapSpot mySpot;

    public Record(long date, int score, MapSpot mySpot) {
        this.date = date;
        this.score = score;
        this.mySpot = mySpot;
    }

    public Record() {
    }

    public long getDate() {
        return date;
    }

    public Record setDate(long date) {
        this.date = date;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public MapSpot getMySpot() {
        return mySpot;
    }

    public Record setMySpot(MapSpot mySpot) {
        this.mySpot = mySpot;
        return this;
    }


}