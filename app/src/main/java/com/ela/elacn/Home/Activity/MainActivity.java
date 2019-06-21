package com.ela.elacn.Home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ela.elacn.$;
import com.ela.elacn.Model.Result;
import com.ela.elacn.R;
import com.ela.elacn.Util.ReqClient;
import com.loopj.android.http.RequestParams;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int maxId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

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
