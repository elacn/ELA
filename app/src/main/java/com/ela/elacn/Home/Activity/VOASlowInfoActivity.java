package com.ela.elacn.Home.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.ParagraphStyle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.ela.elacn.$;
import com.ela.elacn.Fragments.translation;
import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.Home.View.VOASlowPlayInfoAdapter;
import com.ela.elacn.R;
import com.ela.elacn.Util.FileUtil;
import com.ela.elacn.Util.JSON;
import com.ela.elacn.Util.mediamanager;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.youdao.speechEvaluator.SpEvaLanguage;
import com.youdao.speechEvaluator.SpEvaListener;
import com.youdao.speechEvaluator.SpEvaParameters;
import com.youdao.speechEvaluator.SpeechEvaluate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class VOASlowInfoActivity extends AppCompatActivity{

    private ArrayList<VOAslowTextInfoModel> dataSource = new ArrayList<>();

    private ActivityVoaslowInfoBinding b;

    private VOASlowPlayInfoAdapter adapter;

    private Toolbar toolbar;

    private Runnable runnable;

    private Handler handler;

    private VOAslowModel model;

    private int playIndex = 0;

    private String mp3Path;

    private translation t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_voaslow_info);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int PERMISSION_REQUEST_CODE = 1;
            if (ContextCompat.checkSelfPermission(VOASlowInfoActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(VOASlowInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(VOASlowInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE);
                }
            }
        }
        b.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(mp3Path);
            }
        });

        b.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous(mp3Path);
            }
        });

        b.playpauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseplay(mp3Path);
            }
        });




        FileDownloader.setup(this);

        model = (VOAslowModel)getIntent().getSerializableExtra("data");

        Uri url = Uri.parse(model.getData().getUrl());
        mp3Path = $.MP3_DIRECTORY + File.separator + url.getLastPathSegment();

        initUI();

        downloadCheck();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent intent = new Intent(this,SpeakingPractice.class);
                intent.putExtra("data",dataSource);
                intent.putExtra("model",model);
                mediamanager.getManager().stopMp3();
                startActivity(intent);

                return true;
            case android.R.id.home:

                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void pauseplay(String mp3Path){
       Integer tag = Integer.valueOf(b.playpauseButton.getTag().toString());
        if(tag == 1){ // selected
            b.playpauseButton.setImageResource(R.drawable.pauseicon);
            mediamanager.getManager().playAt(mp3Path,dataSource.get(playIndex).getEnd());
            b.playpauseButton.setTag(0);
        }
        else{

            b.playpauseButton.setImageResource(R.drawable.playlarge);
            mediamanager.getManager().pauseAt();
            b.playpauseButton.setTag(1);
        }
    }



    private void previous(String mp3Path){
        playIndex--;
        if(playIndex >= 0){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    int h1 = b.voaSlowListView.getHeight();
                    int h2 = b.voaSlowListView.getHeight();

                    b.voaSlowListView.smoothScrollToPositionFromTop(playIndex, h1/2 - h2/2);
                    adapter.changeSelected(playIndex);//刷新
                    b.playpauseButton.setImageResource(R.drawable.pauseicon);
                }
            });
            b.playpauseButton.setTag(0);
            playmp3(mp3Path);
        }else {

            playIndex = dataSource.size()-1;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    b.voaSlowListView.smoothScrollToPosition(playIndex);
                    adapter.changeSelected(playIndex);//刷新
                    b.playpauseButton.setImageResource(R.drawable.pauseicon);
                }
            });
            b.playpauseButton.setTag(0);
            playmp3(mp3Path);
        }
    }
    private void next(String mp3Path){

        playIndex++;
        if(playIndex < dataSource.size()){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int h1 = b.voaSlowListView.getHeight();
                    int h2 = b.voaSlowListView.getHeight();
                    b.voaSlowListView.smoothScrollToPositionFromTop(playIndex, h1/2 - h2/2);
                    adapter.changeSelected(playIndex);//刷新


                    b.playpauseButton.setImageResource(R.drawable.pauseicon);

                }
            });
            b.playpauseButton.setTag(0);

            playmp3(mp3Path);
        }else {

            playIndex = 0;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    b.voaSlowListView.smoothScrollToPosition(playIndex);
                    adapter.changeSelected(playIndex);//刷新
                }
            });

            playmp3(mp3Path);

        }
    }


    @SuppressLint("ResourceAsColor")
    private void initUI() {


        Toolbar toolbar = findViewById(R.id.voaslow_info_toolbar);
        toolbar.setTitle("ELA Slow English");
        toolbar.setTitleTextColor(R.color.black);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String text = model.getData().getText();

        String[] textArray = text.split("\r\n");

        t = new translation(this);

        for (int i = 0; i < textArray.length; i++) {

            VOAslowTextInfoModel model = JSON.parseObject(textArray[i], VOAslowTextInfoModel.class);

            dataSource.add(model);
        }

        adapter = new VOASlowPlayInfoAdapter(dataSource, this);

        b.voaSlowListView.setAdapter(adapter);



        adapter.setListener(new VOASlowPlayInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                t.hidden();
                adapter.changeSelected(position);//刷新
                b.voaSlowListView.smoothScrollToPosition(position);
                playIndex = position;
                playmp3(mp3Path);
            }

            @Override
            public void onTextClick(String what) {
                t.translateCard(what);
                t.show(true);
            }


        });

        adapter.notifyDataSetChanged();

        b.voaSlowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
                int h1 = b.voaSlowListView.getHeight();
                int h2 = b.voaSlowListView.getHeight();

                b.voaSlowListView.smoothScrollToPositionFromTop(playIndex, h1 / 2 - h2 / 2);
                playIndex = position;
                playmp3(mp3Path);
            }
        });

        b.playpauseButton.setTag(0);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        t.setVisibility(View.INVISIBLE);
        b.voaslow.addView(t,params);

    }

    public void playCycle(final int progress){

        int m = progress;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        //取整
        m=  Math.abs(m);

        String hms = formatter.format(m);

        final String date = hms.substring(3,5) + ":" + hms.substring(6,hms.length());

        float p = progress;
        float d = mediamanager.getManager().getDuration();

        final float pro = p / d * 100;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.currentTime.setText(date);
            }
        });

                b.seekbar.post(new Runnable() {
                    @Override
                    public void run() {
                        b.seekbar.setProgress(pro);
                    }
                });
    }




    public void downloadCheck() {


            if (!FileUtil.checkFile($.MP3_DIRECTORY)) {
                Environment.getExternalStorageState();
                File directory = new File($.MP3_DIRECTORY);
                directory.mkdirs();
            }


        if (!FileUtil.checkFile(mp3Path)) {
            downloadMP3(model.getData().getUrl(), mp3Path);
        } else {
            playmp3(mp3Path);
        }
    }


    public void playmp3(final String mp3Path){

        int start = dataSource.get(playIndex).getStart();
        int end = dataSource.get(playIndex).getEnd();
        try {
            mediamanager.getManager().playMp3(mp3Path, start, playIndex == dataSource.size()-1 ? (end - start) * 2 : end - start, new mediamanager.completedPlay() {
                @Override
                public void playercallback() {
                    next(mp3Path);
                }

                @Override
                public void prepared() {
                    float m = mediamanager.getManager().getDuration();

                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
                    //取整
                    m =  Math.abs(m);

                    String hms = formatter.format(m);

                    final String date = hms.substring(3,5) + ":" + hms.substring(6,hms.length());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            b.totalTime.setText(date);
                        }
                    });
                }

                @Override
                public void progresstrack(int postion) {
                    playCycle(postion);
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadMP3(String url, String path){
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        downloadCheck();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        mediamanager.getManager().stopMp3();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
}
