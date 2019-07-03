package com.ela.elacn.Home.Activity;

import android.annotation.SuppressLint;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
import java.util.ArrayList;

public class VOASlowInfoActivity extends AppCompatActivity{

    private ArrayList<VOAslowTextInfoModel> dataSource = new ArrayList<>();

    private ActivityVoaslowInfoBinding b;

    private VOASlowPlayInfoAdapter adapter;

    private Toolbar toolbar;

    private VOAslowModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_voaslow_info);

        FileDownloader.setup(this);

        model = (VOAslowModel)getIntent().getSerializableExtra("data");

        initUI();

        downloadCheck();
    }

    @SuppressLint("ResourceAsColor")
    private void initUI(){


        Toolbar toolbar = findViewById(R.id.voaslow_info_toolbar);
        toolbar.setTitle("ELA Slow English");
        toolbar.setTitleTextColor(R.color.black);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String text = model.getData().getText();

        String[] textArray = text.split("\r\n");

        for (int i= 0; i < textArray.length; i++){

            VOAslowTextInfoModel model = JSON.parseObject(textArray[i],VOAslowTextInfoModel.class);

            dataSource.add(model);
        }

        adapter = new VOASlowPlayInfoAdapter(dataSource,this);

        b.voaSlowListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


    public void downloadCheck(){


        String mp3Path = $.MP3_DIRECTORY+ File.separator +model.getData().getUrl();

        if (!FileUtil.checkFile($.MP3_DIRECTORY)){
            FileUtil.makeRootDirectory($.MP3_DIRECTORY);
        }

        if(!FileUtil.checkFile(mp3Path)){
            downloadMP3(model.getData().getUrl(), mp3Path);

        }
        else{

            try {
                mediamanager.getManager().playMp3(mp3Path, dataSource.get(0).getStart(), dataSource.get(0).getEnd(), new mediamanager.completedPlay() {
                    @Override
                    public void playercallback() {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
}
