package com.ela.elacn.Home.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.bluetooth.le.AdvertisingSetCallback;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryfork.spanny.Spanny;
import com.ela.elacn.$;
import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.Home.Model.speechmodel;
import com.ela.elacn.Home.Model.wordobject;
import com.ela.elacn.Home.View.SpeakingPracticeAdapter;
import com.ela.elacn.Home.View.VOAslow;
import com.ela.elacn.R;
import com.ela.elacn.Util.FileUtil;
import com.ela.elacn.Util.JSON;
import com.ela.elacn.Util.StringUtils;
import com.ela.elacn.Util.mediamanager;
import com.ela.elacn.Util.permissioncheck;
import com.ela.elacn.Util.recordermanager;
import com.ela.elacn.databinding.ActivitySpeakingPracticeBinding;
import com.ela.elacn.databinding.SpeakingPracticeAdapterBinding;
import com.youdao.speechEvaluator.SpEvaLanguage;
import com.youdao.speechEvaluator.SpEvaListener;
import com.youdao.speechEvaluator.SpEvaParameters;
import com.youdao.speechEvaluator.SpEvaResult;
import com.youdao.speechEvaluator.SpeechEvaluate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;

public class SpeakingPractice extends AppCompatActivity {

    private ActivitySpeakingPracticeBinding b;

    private SpeakingPracticeAdapter speakingPracticeAdapter;

    private ArrayList<VOAslowTextInfoModel> datasource;

    private String mp3Path;

    private Toolbar toolbar;

    private VOAslowModel model;


    final String recordpath = Environment.getExternalStorageDirectory()
            .getAbsolutePath();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_speaking_practice);

        Intent recieved = getIntent();
        datasource = (ArrayList<VOAslowTextInfoModel>) recieved.getSerializableExtra("data");

        model = (VOAslowModel) recieved.getSerializableExtra("model");

        Uri url = Uri.parse(model.getData().getUrl());

        mp3Path = $.MP3_DIRECTORY + File.separator + url.getLastPathSegment();


        playmp3(datasource.get(0));


        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception error) {

            }
        });

        INITUI();
    }

    protected void onDestroy() {
        super.onDestroy();

        mediamanager.getManager().stopMp3();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @SuppressLint("ResourceAsColor")
    public void INITUI(){

        Toolbar toolbar = findViewById(R.id.toolbarSPEECH);
        toolbar.setTitle("ELA Speech Practice");
        toolbar.setTitleTextColor(R.color.black);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        speakingPracticeAdapter = new SpeakingPracticeAdapter(datasource, this);

        b.speakinglist.setAdapter(speakingPracticeAdapter);


        b.speakinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                speakingPracticeAdapter.changeSelected(position);
                playmp3(datasource.get(position));
                int h1 = b.speakinglist.getHeight();
                int h2 = b.speakinglist.getHeight();
                b.speakinglist.smoothScrollToPositionFromTop(position, h1/2 - h2/2);
            }
        });



       speakingPracticeAdapter.setListener(new SpeakingPracticeAdapter.onclickbuttonlistener() {
           @Override
           public void clickplay(SpeakingPracticeAdapter.ViewHolder holder, VOAslowTextInfoModel model, int position) {
               Integer tag = Integer.valueOf(holder.playAudio.getTag().toString());
               if(tag == 0){
                   playmp3(model);
                   holder.playAudio.setImageResource(R.drawable.pauseicon);
                   holder.playAudio.setTag(1);
               }
               else{
                   pausemp3();
                   holder.playAudio.setImageResource(R.drawable.playlarge);
                   holder.playAudio.setTag(0);
               }
           }

           @Override
           public void clickrecord(final SpeakingPracticeAdapter.ViewHolder holder, final VOAslowTextInfoModel model, final int position) {

               mediamanager.getManager().stopMp3();
               holder.playAudio.setImageResource(R.drawable.playlarge);
               Integer tag = Integer.valueOf(holder.record.getTag().toString());
               String md5String = StringUtils.md5encode(String.valueOf(model.getEnd()));

               if(permissioncheck.checkpermissions(SpeakingPractice.this, Manifest.permission.RECORD_AUDIO)){

                if(tag == 1){
                    holder.record.setImageResource(R.drawable.stopicon);

                    if (!FileUtil.checkFile($.RECORD_PATH)) {
                        Environment.getExternalStorageState();
                        File directory = new File($.RECORD_PATH);
                        directory.mkdirs();
                    }

                    try {

                        recordermanager.getManager().record($.RECORD_PATH + File.separator + md5String + ".mp3", MediaRecorder.OutputFormat.MPEG_2_TS,MediaRecorder.AudioEncoder.AAC);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    holder.record.setTag(0);
                } else{
                    holder.record.setImageResource(R.drawable.recordicon);
                    holder.record.setTag(1);
                    recordermanager.getManager().stopRecord();
                    grade( md5String + ".mp3", model.getEnglish(), new SpEvaListener() {
                        @Override
                        public void onError(int i) {
                            Log.e("NOGRADE",String.valueOf(i));
                        }

                        @Override
                        public void onResult(final SpEvaResult spEvaResult, String s) {

                            final double data = spEvaResult.getPronunciation() + 0.5;

                           final int score = (int)data;

                            speechmodel spmodel = JSON.parseObject(spEvaResult.getJson(),speechmodel.class);
                            settextcolor(model.getEnglish(), spmodel.getWords(),holder.english_text,position );
                            datasource.get(position).setScore(score);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.banner.setVisibility(View.VISIBLE);
                                    holder.score.setVisibility(View.VISIBLE);

                                    holder.score.setText(String.valueOf(score)+"%");
                                }
                            });

                        }
                    });
                }

            }else {

                permissioncheck.requestpermission(SpeakingPractice.this,SpeakingPractice.this,1,new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE});
             }

           }

           @Override
           public void clickplayBack(SpeakingPracticeAdapter.ViewHolder holder, VOAslowTextInfoModel model, int position) {

               holder.playAudio.setTag(0);

               holder.playAudio.setImageResource(R.drawable.playlarge);

               String md5String = StringUtils.md5encode(String.valueOf(model.getEnd()));

               try {
                   mediamanager.getManager().simplePlay($.RECORD_PATH + File.separator + md5String + ".mp3");
               } catch (IOException error) {
                   error.printStackTrace();
               }


           }
       });

        speakingPracticeAdapter.notifyDataSetChanged();

    }

    @SuppressLint("ResourceAsColor")
    private void settextcolor(String key, ArrayList<wordobject> userspeech, final TextView textView, int position){

        final String[] englisharray = key.split(" ");

        final Spanny spannable = new Spanny();

        for (int i =0; i < userspeech.size(); i++) {

            int start = key.indexOf(englisharray[i]);
            int end = start + englisharray[i].length();


            if(userspeech.get(i).getPronunciation() >= 50){
                spannable.append(" " + englisharray[i], new ForegroundColorSpan(Color.GREEN));
            }else{
                spannable.append(" " + englisharray[i], new ForegroundColorSpan(Color.RED));
            }
        }

        datasource.get(position).setTxtcolor(spannable);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(spannable);
            }
        });
    }



    private void grade(String filename, final String key, final SpEvaListener listener){
        File mp3path = new File($.RECORD_PATH,filename);
        IConvertCallback callback = new IConvertCallback() {
            @Override
            public void onSuccess(File convertedFile) {
                byte[] bytesArray = new byte[(int) convertedFile.length()];

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(convertedFile);
                    fis.read(bytesArray); //read file into bytes[]
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String value = Base64.encodeToString(bytesArray, Base64.DEFAULT);

                evaluate(value, key, listener);
            }

            @Override
            public void onFailure(Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SpeakingPractice.this, "Could not grade", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };


        AndroidAudioConverter.with(this)
                .setFile(mp3path)
                .setFormat(AudioFormat.WAV)
                .setCallback(callback)
                .convert();
    }

    public void playmp3(VOAslowTextInfoModel model){

        int start = model.getStart();
        int end = model.getEnd();
        try {
            mediamanager.getManager().playMp3(mp3Path, start, end - start, new mediamanager.completedPlay() {
                @Override
                public void playercallback() {

                }

                @Override
                public void prepared() {
                }

                @Override
                public void progresstrack(int postion) {

                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pausemp3(){

        mediamanager.getManager().stopMp3();
    }


    private SpEvaParameters mSpEvaParameters = new SpEvaParameters.Builder()
            .channel(1)
            .language(SpEvaLanguage.ENGLISH)
            .rate(16000)
            .timeout(100000)
            .format("wav")
            .build();

    private void evaluate(String base64AudioData,String spEvaText, SpEvaListener listener){

        SpeechEvaluate.getInstance(mSpEvaParameters).evaluate(base64AudioData, spEvaText, listener);
    }

}
