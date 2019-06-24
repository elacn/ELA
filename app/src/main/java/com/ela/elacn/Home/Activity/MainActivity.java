package com.ela.elacn.Home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.FrameLayoutBindingAdapter;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;


import com.ela.elacn.$;
import com.ela.elacn.Model.Result;
import com.ela.elacn.R;
import com.ela.elacn.Util.ReqClient;
import com.ela.elacn.databinding.ActivityMainBinding;
import com.loopj.android.http.RequestParams;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int maxId = 0;
    private Toolbar toolbar;

    private ActivityMainBinding b;

    private Fragment frag;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);


        loadData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    private void initUI(){

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("VOA", VOAslow.class)
                .add("Movie", VOAslow.class)
                .create());

        b.viewpager.setAdapter(adapter);

        b.viewpagertab.setViewPager(b.viewpager);
    }

    private void loadData(){

        RequestParams params = new RequestParams();
        params.add("limit",String.valueOf(10));
        params.add("s","voaspecial");
        params.add("syncText","1");
        params.add("maxId",String.valueOf(maxId));

        ReqClient.get($.DEV_SERVER + "/feed", params, new ReqClient.Handler<String>(String.class){
            @Override
            public void res(Result<String> res) {

                if (res.isSuccess()){

                    String json = res.data;
                }
            }
        });
    }

}
