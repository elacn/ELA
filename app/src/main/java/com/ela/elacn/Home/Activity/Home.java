package com.ela.elacn.Home.Activity;

import android.os.Bundle;

import com.ela.elacn.$;
import com.ela.elacn.Model.Result;
import com.ela.elacn.R;
import com.ela.elacn.Util.ReqClient;
import com.ela.elacn.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.RequestParams;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ela.elacn.databinding.ActivityHomeBinding;

import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int maxId = 0;
    private Toolbar toolbar;

    private ActivityHomeBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ActivityHomeBinding b = DataBindingUtil.setContentView(this,R.layout.activity_home);

        b = DataBindingUtil.setContentView(this, R.layout.activity_home);



        loadData();
        toolbar = findViewById(R.id.toptoolbar);
        setSupportActionBar(toolbar);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toptoolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
