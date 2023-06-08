package com.example.projectstart;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.projectstart.R;

public class MusicService extends Service {
    private static  final String TAG=null;
    MediaPlayer mediaPlayer;
    int audio= R.raw.mp1;



    public MusicService() {
    }

   /* public MusicService(int audio) {
        this.audio=audio;
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");
    return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this,this.audio);
        mediaPlayer.setVolume(0.3f,0.4f);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopSelf();
            }
        });
        mediaPlayer.start();
       // return 1;
        return super.onStartCommand(intent, flags, startId);
    }
    public void onStop()
    {
        this.mediaPlayer.stop();
    }
    @Override
    public void onDestroy() {
        onStop();
        super.onDestroy();
    }
}