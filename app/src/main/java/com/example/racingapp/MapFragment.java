package com.example.racingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.racingapp.Models.Record;
import com.example.racingapp.Utils.MyDB;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private GoogleMap recordMap;
    private MarkerOptions markerOptions;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                recordMap = googleMap;

                LatLng israel = new LatLng(31.4117, 35.0818);
                googleMap.addMarker(new MarkerOptions().position(israel).title("Israel"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(israel));
            }
        });


        return view;
    }

    private void addPin(LatLng latlng) {
        recordMap.clear();
        markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        recordMap.addMarker(markerOptions);
    }

    public void RecordClicked(double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        recordMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        addPin(latLng);
    }


}