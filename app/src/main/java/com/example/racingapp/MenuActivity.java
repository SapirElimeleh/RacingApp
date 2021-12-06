package com.example.racingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.racingapp.Utils.MyDB;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MenuActivity extends AppCompatActivity {

    SwitchMaterial soundSwitch;
    SwitchMaterial sensorSwitch;
    Button menu_BTN_play;
    Button menu_BTN_records;
    boolean sensorValue = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_activity);


        findMenuView();
        playBackgroundSound();
        goToGame();
        goToRecords();

    }

    public void playBackgroundSound() {

        Intent intent = new Intent(MenuActivity.this, MusicService.class);
        startService(intent);

        soundSwitch.setOnClickListener(v -> {
            if (!soundSwitch.isChecked()) {
                stopService(intent);
            } else {
                startService(intent);
            }
        });

    }


    //Turn on and off sensor mode
//    public void turnOnAndOffSensorsMode() {
//
//        sensorSwitch.setOnClickListener(v -> {
//
//
//        }else{
//            //TO DO
//        }
//    });
//
//}


    //get the switchers and the button view
    private void findMenuView() {
        soundSwitch = findViewById(R.id.menu_SWC_sound);
        sensorSwitch = findViewById(R.id.menu_SWC_sensor);
        menu_BTN_play = findViewById(R.id.menu_BTN_play);
        menu_BTN_records = findViewById(R.id.menu_BTN_records);
    }


    //fo to game activity by clicking the Play button
    private void goToGame() {
        menu_BTN_play.setOnClickListener(v -> {

            Intent intent = new Intent(MenuActivity.this, GameActivity.class);

            if (sensorSwitch.isChecked()) {
                intent.putExtra(GameActivity.SENSOR_TYPE, "SENS");
            }else{
                intent.putExtra(GameActivity.SENSOR_TYPE, "BTN");
            }

            startActivity(intent);
        });
    }


    //   go ro Records activity
    private void goToRecords() {
        menu_BTN_records.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RecordsActivity.class);
            startActivity(intent);

        });
    }


}
