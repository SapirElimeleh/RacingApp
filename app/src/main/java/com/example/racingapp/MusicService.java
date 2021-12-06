package com.example.racingapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.got_sound_background);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(20, 20);
        mediaPlayer.start();
    }

    public void turnOnMusic(){
        mediaPlayer.start();
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        return startId;
    }

    public void onStart(Intent intent, int startId) {
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onLowMemory() {
    }
}


