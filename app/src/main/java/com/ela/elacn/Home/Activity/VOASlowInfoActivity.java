package com.ela.elacn.Home.Activity;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ela.elacn.R;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;

public class VOASlowInfoActivity extends AppCompatActivity {


    private ActivityVoaslowInfoBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_voaslow_info);


    }
}
