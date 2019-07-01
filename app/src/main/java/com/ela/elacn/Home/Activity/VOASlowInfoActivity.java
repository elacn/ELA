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

import com.ela.elacn.Home.View.VOASlowPlayInfoAdapter;
import com.ela.elacn.R;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;

import java.util.ArrayList;

public class VOASlowInfoActivity extends AppCompatActivity{

    private ActivityVoaslowInfoBinding b;

    private VOASlowPlayInfoAdapter adapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_voaslow_info);

        initUI();
    }

    @SuppressLint("ResourceAsColor")
    private void initUI(){


        Toolbar toolbar = findViewById(R.id.voaslow_info_toolbar);
        toolbar.setTitle("ELA Slow English");
        toolbar.setTitleTextColor(R.color.black);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new VOASlowPlayInfoAdapter(new ArrayList(),this);

        b.voaSlowListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
}
