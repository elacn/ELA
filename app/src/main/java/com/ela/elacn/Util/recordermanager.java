package com.ela.elacn.Util;

import android.media.AudioManager;
import android.media.MediaRecorder;

import java.io.IOException;

public class recordermanager {


    private static MediaRecorder recorder;
    private static recordermanager instance;

    public static recordermanager getManager(){

        if(instance==null){
            synchronized (recordermanager.class){
                instance = new recordermanager();
                recorder = new MediaRecorder();
            }
        }
        return instance;
    }



    public void record(String savepath, int format, int encoder) throws IOException {

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(format);
        recorder.setOutputFile(savepath);
        recorder.setAudioEncodingBitRate(44100);
        recorder.setAudioSamplingRate(16000);
        recorder.setMaxDuration(60000); // Set max duration 60 sec.
        recorder.setMaxFileSize(5000000);
        recorder.setAudioEncoder(encoder);
        recorder.prepare();
        recorder.start();
    }

    public void stopRecord(){
        recorder.stop();
    }



}
