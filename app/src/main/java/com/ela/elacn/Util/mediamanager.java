package com.ela.elacn.Util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.rtp.AudioStream;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class mediamanager {

    public interface completedPlay{
        void playercallback();
    }

    private static mediamanager self;

    private static MediaPlayer mp;

    private static completedPlay block;

    public static mediamanager getManager(){
        if(self == null){
            synchronized (mediamanager.class) {
                mp = new MediaPlayer();
                self = new mediamanager();
            }
        }
        return self;
    }

    private static Timer timer;

    public  void playMp3(String filepath, final int start, final int stop, completedPlay b) throws IOException {

        stopTimer();
        createTimer();
        mp.stop();
        block = b;
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.reset();
        mp.setDataSource(filepath);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.seekTo(start);
                mp.start();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (block != null) block.playercallback();
                        mp.stop();
                    }
                }, stop - start);
            }
        });
        mp.prepareAsync();
    }


    public void stopMp3(){
        stopTimer();
        mp.stop();
    }

    public  void pauseMp3(){
        mp.pause();
    }

    private static void createTimer(){
        timer = new Timer();
    }

    private static void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }


}
