package com.example.racingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.racingapp.Adapters.RecordAdapter;
import com.example.racingapp.Models.Record;
import com.example.racingapp.Utils.MyDB;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private AppCompatActivity activity;
    private RecyclerView list_RV_records;
    private List_CallBack listCallBack;
    private ArrayList<Record> records;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_list, container, false);


        list_RV_records = view.findViewById(R.id.list_RV_records);


        String js = MSP.getInstance().getString("MY_DB", "");
        MyDB myDB = new Gson().fromJson(js, MyDB.class);

        records = myDB.getRecords();

        RecordAdapter recordAdapter = new RecordAdapter(this, MyDB.getRecords());


        list_RV_records.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        list_RV_records.setHasFixedSize(true);
        list_RV_records.setItemAnimator(new DefaultItemAnimator());
        list_RV_records.setAdapter(recordAdapter);

        recordAdapter.setRecordItemClickListener(new RecordAdapter.RecordItemClickListener() {
            @Override
            public void recordItemClick(Record record, int position) {
                if(listCallBack != null){
                    double lat = record.getMySpot().getLatitude();
                    double lon = record.getMySpot().getLongitude();
                    listCallBack.RecordClicked(lat,lon);
                }
            }
        });

        return view;

    }

    public void setListCallBack(List_CallBack listCallBack){
        this.listCallBack = listCallBack;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;

    }
}