package com.ela.elacn.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

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

    public  void playMp3(String filepath, int start, int stop, completedPlay b) throws IOException {

        stopTimer();
        createTimer();
        mp.stop();
        block = b;
        mp.setDataSource(filepath);
        mp.seekTo(start);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mp.prepareAsync();

        Timer timer = new Timer();
        timer.schedule(task, stop-start);
    }


    public  void stopMp3(){
        mp.stop();
    }


    public  void pauseMp3(){
        mp.pause();
    }

    private static TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (block != null) block.playercallback();
            mp.stop();
        }
    };

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
