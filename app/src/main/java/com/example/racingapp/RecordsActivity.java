package com.example.racingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.racingapp.Models.Record;
import com.example.racingapp.Utils.MyDB;
import com.google.gson.Gson;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    private List_CallBack listCallBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.rec_LST_records, listFragment).commit();

        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.rec_MAP_recMap, mapFragment).commit();



        listCallBack = new List_CallBack() {
            @Override
            public void RecordClicked(double lat, double lon) {
                mapFragment.RecordClicked(lat, lon);
            }
        };

        listFragment.setActivity(this);

        listFragment.setListCallBack(listCallBack);

    }



}