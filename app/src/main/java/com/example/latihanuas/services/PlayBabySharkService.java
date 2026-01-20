package com.example.latihanuas.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.latihanuas.R;

public class PlayBabySharkService extends Service {
    private MediaPlayer mediaPlayer;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO 3A: play baby shark dari R.raw di sini
        if(mediaPlayer == null){

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //TODO 3B: stop media player

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
