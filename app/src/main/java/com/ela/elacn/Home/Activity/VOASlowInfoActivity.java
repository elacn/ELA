package com.ela.elacn.Home.Activity;

import android.annotation.SuppressLint;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.Home.View.VOASlowPlayInfoAdapter;
import com.ela.elacn.R;
import com.ela.elacn.Util.JSON;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;

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

        model = (VOAslowModel)getIntent().getSerializableExtra("data");

        initUI();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
}
