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

public class mediamanager implements Runnable {

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (block != null) block.progresstrack(mp.getCurrentPosition());
        }

    }

    public interface completedPlay{
        void playercallback();
        void prepared();
        void progresstrack(int postion);
    }

    private static mediamanager self;

    private static MediaPlayer mp;

    private static completedPlay block;

    private static int pausedTime;

    public static mediamanager getManager(){
        if(self == null){
            synchronized (mediamanager.class) {
                pausedTime = 0;
                mp = new MediaPlayer();
                self = new mediamanager();
                new Thread(self).start();
            }
        }
        return self;
    }

    public void simplePlay(String mp3) throws IOException {
        mp.reset();
        mp.setDataSource(mp3);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        mp.prepare();
        mp.start();
    }

    public int getDuration(){
        return mp.getDuration();
    }

    public int getPosition(){
        return mp.getCurrentPosition();
    }

    public void playAt(String mp3,int endtime){

        try {
            playMp3(mp3, pausedTime,  endtime - pausedTime,block);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseAt(){
        pausedTime = mediamanager.getManager().getPosition();
        stopTimer();
        mp.stop();

    }
    private static Timer timer;


    public  void playMp3(String filepath, final int start,final int delay, completedPlay b) throws IOException {

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
                if(block != null) block.prepared();
                mp.seekTo(start);
                mp.start();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (block != null){
                            block.playercallback();
                        }
                        mp.stop();
                    }
                }, delay);
            }
        });

        mp.prepareAsync();
    }


    public void skipto(int position){
        mp.seekTo(position);
    }

    public boolean playstatus(){
        return mp.isPlaying();
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
