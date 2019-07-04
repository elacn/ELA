package com.ela.elacn.Home.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;

import com.ela.elacn.$;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class VOASlowInfoActivity extends AppCompatActivity{

    private ArrayList<VOAslowTextInfoModel> dataSource = new ArrayList<>();

    private ActivityVoaslowInfoBinding b;

    private VOASlowPlayInfoAdapter adapter;

    private Toolbar toolbar;

    private VOAslowModel model;

    private int playIndex = 0;

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

        FileDownloader.setup(this);

        model = (VOAslowModel)getIntent().getSerializableExtra("data");

        initUI();

        downloadCheck();
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

        for (int i = 0; i < textArray.length; i++) {

            VOAslowTextInfoModel model = JSON.parseObject(textArray[i], VOAslowTextInfoModel.class);

            dataSource.add(model);
        }

        adapter = new VOASlowPlayInfoAdapter(dataSource, this);

        b.voaSlowListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        b.voaSlowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri url = Uri.parse(model.getData().getUrl());
                String mp3Path = $.MP3_DIRECTORY + File.separator + url.getLastPathSegment();
                int h1 = b.voaSlowListView.getHeight();
                int h2 = b.voaSlowListView.getHeight();

                b.voaSlowListView.smoothScrollToPositionFromTop(playIndex, h1 / 2 - h2 / 2);
                playIndex = position;
                playmp3(mp3Path);
            }
        });

    }




    public void downloadCheck() {

        Uri url = Uri.parse(model.getData().getUrl());

        String mp3Path = $.MP3_DIRECTORY + File.separator + url.getLastPathSegment();


        if (FileUtil.checkFile($.MP3_DIRECTORY)) {
            FileUtil.makeRootDirectory($.MP3_DIRECTORY);

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
    }


    public void playmp3(final String mp3Path){
        try {
            mediamanager.getManager().playMp3(mp3Path, dataSource.get(playIndex).getStart(), dataSource.get(playIndex).getEnd(), new mediamanager.completedPlay() {
                @Override
                public void playercallback() {
                    if(playIndex <= dataSource.size()){
                        playIndex++;
                        //b.voaSlowListView.smoothScrollToPosition(playIndex);
                        int h1 = b.voaSlowListView.getHeight();
                        int h2 = b.voaSlowListView.getHeight();

                        b.voaSlowListView.smoothScrollToPositionFromTop(playIndex, h1/2 - h2/2);
                        playmp3(mp3Path);
                    }

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
