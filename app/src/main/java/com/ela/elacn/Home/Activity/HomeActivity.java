package com.ela.elacn.Home.Activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ela.elacn.$;
import com.ela.elacn.Home.View.VOANormal;
import com.ela.elacn.Home.View.VOAslow;
import com.ela.elacn.Model.Result;
import com.ela.elacn.R;
import com.ela.elacn.Util.ReqClient;
import com.ela.elacn.Util.clearCache;
import com.ela.elacn.databinding.ActivityHomeBinding;
import com.loopj.android.http.RequestParams;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.youdao.sdk.app.YouDaoApplication;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        private int maxId = 0;
        private Toolbar toolbar;

        private ActivityHomeBinding b;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            b = DataBindingUtil.setContentView(this, R.layout.activity_home);

            //注册应用ID ，建议在应用启动时，初始化，所有功能的使用都需要该初始化，调用一次即可，demo中在MainActivity类中
            YouDaoApplication.init(this, $.YOUDAO_APPKEY);

            toolbar = findViewById(R.id.toptoolbar);
            setSupportActionBar(toolbar);
            Toolbar toolbar = findViewById(R.id.toptoolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);
            initUI();

//            loadData();

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
            } else if (id == R.id.nav_getcache) {
                double size = clearCache.getSize($.ROOT_DIR);
                String s = String.format("%.2f",size);
                Toasty.info(this,s + "megabytes" , Toast.LENGTH_SHORT, true).show();

            } else if (id == R.id.nav_clearcache) {
                clearCache.clearCache($.ROOT_DIR, this);

            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        private void initUI(){


            FragmentPagerItemAdapter adapter1 = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add("Slow English", VOAslow.class)
                    .add("Normal English", VOANormal.class)
                    .create());

            b.viewpager.setAdapter(adapter1);

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
