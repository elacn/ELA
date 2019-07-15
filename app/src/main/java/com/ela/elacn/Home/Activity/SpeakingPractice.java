package com.ela.elacn.Home.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ela.elacn.$;
import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.Home.View.SpeakingPracticeAdapter;
import com.ela.elacn.Home.View.VOAslow;
import com.ela.elacn.R;
import com.ela.elacn.Util.StringUtils;
import com.ela.elacn.Util.mediamanager;
import com.ela.elacn.Util.permissioncheck;
import com.ela.elacn.Util.recordermanager;
import com.ela.elacn.databinding.ActivitySpeakingPracticeBinding;
import com.ela.elacn.databinding.SpeakingPracticeAdapterBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

public class SpeakingPractice extends AppCompatActivity {

    private ActivitySpeakingPracticeBinding b;

    private SpeakingPracticeAdapter speakingPracticeAdapter;

    private ArrayList<VOAslowTextInfoModel> datasource;

    private String mp3Path;

    private Toolbar toolbar;

    private VOAslowModel model;

    private boolean status = false;

    final String recordpath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() +"/myrecording.mp3";




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
        status = true;


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
                status = true;
            }
        });



       speakingPracticeAdapter.setListener(new SpeakingPracticeAdapter.onclickbuttonlistener() {
           @Override
           public void clickplay(ImageView button, VOAslowTextInfoModel model, int position) {
               if(status == false){
                   status = true;
                   playmp3(model);
                   button.setImageResource(R.drawable.pauseicon);
               }
               else{
                   status = false;
                   pausemp3();
                   button.setImageResource(R.drawable.playlarge);
               }
           }

           @Override
           public void clickrecord(ImageView button, VOAslowTextInfoModel model, int position) {

               mediamanager.getManager().stopMp3();

               Integer tag = Integer.valueOf(button.getTag().toString());
               String md5String = StringUtils.md5encode(model.getEnglish());
               md5String = "123";

               if(permissioncheck.checkpermissions(SpeakingPractice.this, Manifest.permission.RECORD_AUDIO)){

                if(tag == 1){
                    button.setImageResource(R.drawable.stop);
                    try {

                        recordermanager.getManager().record(recordpath, MediaRecorder.OutputFormat.MPEG_2_TS,MediaRecorder.AudioEncoder.AAC);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    button.setTag(0);
                } else{
                    button.setImageResource(R.drawable.recordicon);
                   recordermanager.getManager().stopRecord();
                    button.setTag(0);
                }



            }else {

                permissioncheck.requestpermission(SpeakingPractice.this,SpeakingPractice.this,1,new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE});
             }

           }

           @Override
           public void clickplayBack(ImageView button, VOAslowTextInfoModel model, int position) {

               String md5String = StringUtils.md5encode(model.getEnglish());
               md5String = "123";

               try {
                   mediamanager.getManager().simplePlay(recordpath);
               } catch (IOException error) {
                   error.printStackTrace();
               }


           }
       });

        speakingPracticeAdapter.notifyDataSetChanged();

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


}
