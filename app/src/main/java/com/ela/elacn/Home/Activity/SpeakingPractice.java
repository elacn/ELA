package com.ela.elacn.Home.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.Home.View.SpeakingPracticeAdapter;
import com.ela.elacn.R;
import com.ela.elacn.databinding.ActivitySpeakingPracticeBinding;
import com.ela.elacn.databinding.SpeakingPracticeAdapterBinding;

import java.util.ArrayList;

import javax.sql.DataSource;

public class SpeakingPractice extends AppCompatActivity {

    private ActivitySpeakingPracticeBinding b;

    private SpeakingPracticeAdapter speakingPracticeAdapter;

    private ArrayList<VOAslowTextInfoModel> datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this,R.layout.activity_speaking_practice);

        Intent recieved = getIntent();
        datasource = (ArrayList<VOAslowTextInfoModel>) recieved.getSerializableExtra("data");

        INITUI();
    }



    public void INITUI(){

        speakingPracticeAdapter = new SpeakingPracticeAdapter(datasource, this);

        b.speakinglist.setAdapter(speakingPracticeAdapter);

        b.speakinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                speakingPracticeAdapter.changeSelected(position);
            }
        });

        speakingPracticeAdapter.notifyDataSetChanged();

    }
}
