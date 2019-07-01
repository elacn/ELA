package com.ela.elacn.Home.Activity;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ela.elacn.Home.View.VOASlowPlayInfoAdapter;
import com.ela.elacn.R;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;

import java.util.ArrayList;

public class VOASlowInfoActivity extends AppCompatActivity {

    private ActivityVoaslowInfoBinding b;

    private VOASlowPlayInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_voaslow_info);

        initUI();
    }

    private void initUI(){

        adapter = new VOASlowPlayInfoAdapter(new ArrayList(),this);

        b.voaSlowListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}
