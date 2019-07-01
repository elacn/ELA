package com.ela.elacn.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

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

    public  void playMp3(Context where, Uri filepath, int start, int stop, completedPlay b){

        stopTimer();
        createTimer();
        mp.stop();
        block = b;
        mp = MediaPlayer.create(where, filepath);
        mp.seekTo(start);
        mp.start();

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
