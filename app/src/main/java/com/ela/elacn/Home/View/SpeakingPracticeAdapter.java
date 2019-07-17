package com.ela.elacn.Home.View;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ela.elacn.Home.Activity.SpeakingPractice;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.R;
import com.jaychang.st.OnTextClickListener;
import com.jaychang.st.Range;
import com.jaychang.st.SimpleText;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.ArrayList;
import java.util.List;

public class SpeakingPracticeAdapter extends BaseAdapter {


    public SpeakingPracticeAdapter(ArrayList<VOAslowTextInfoModel> dataSource, Context context){
        this.context = context;
        this.dataSource = dataSource;
    }


    public interface onclickbuttonlistener{
        void clickplay(ViewHolder holder, VOAslowTextInfoModel model, int position);
        void clickrecord(ViewHolder holder, VOAslowTextInfoModel model, int position);
        void clickplayBack(ViewHolder holder, VOAslowTextInfoModel model, int position);
    }


    public void setListener(onclickbuttonlistener listener) {
        this.listener = listener;
    }

    private onclickbuttonlistener listener;


    private ArrayList<VOAslowTextInfoModel> dataSource;

    private Context context;

    @Override
    public int getCount() {return dataSource.size(); }

    @Override
    public Object getItem(int position) {return null; }

    @Override
    public long getItemId(int position) {return 0;}





    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;

        Animation iconanimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        iconanimation.setDuration(600);



        if(view == null) {

            View layoutView = LayoutInflater.from(context).inflate(R.layout.speaking_practice_adapter, parent, false);

            viewHolder = new ViewHolder(layoutView);
            layoutView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        final VOAslowTextInfoModel model = dataSource.get(position);

        if(mSelect==position){
            viewHolder.iconpanel.setVisibility(View.VISIBLE);
            viewHolder.playAudio.startAnimation(iconanimation);
            viewHolder.playBack.startAnimation(iconanimation);
            viewHolder.record.startAnimation(iconanimation);
            viewHolder.mask.setAlpha(0);
        }else{
            viewHolder.iconpanel.setVisibility(View.GONE);
            //animation
            viewHolder.mask.setAlpha(0.5f);
        }

        viewHolder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)listener.clickplay(viewHolder, dataSource.get(position), position);
            }
        });

        viewHolder.record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!= null)listener.clickrecord((viewHolder), dataSource.get(position), position);
            }
        });

        viewHolder.playBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!= null)listener.clickplayBack((viewHolder), dataSource.get(position), position);
            }
        });


        if(model.getTxtcolor() != null){
            viewHolder.english_text.setText(model.getTxtcolor());
            viewHolder.chinese_text.setText(model.getChinese());

        }
        else{

            viewHolder.english_text.setText(model.getEnglish());
            viewHolder.chinese_text.setText(model.getChinese());
        }

        if(model.getScore() != -1){
            viewHolder.score.setText(String.valueOf(model.getScore()));
            viewHolder.banner.setVisibility(View.VISIBLE);
            viewHolder.score.setVisibility(View.VISIBLE);
        }
        else{

            viewHolder.banner.setVisibility(View.INVISIBLE);
            viewHolder.score.setVisibility(View.INVISIBLE);
        }


        viewHolder.record.setTag(1);
        viewHolder.record.setImageResource(R.drawable.recordicon);

        return viewHolder.itemView;
    }

    public static class ViewHolder{
        public ImageView banner;
        public TextView score;
        public View itemView;
        public TextView english_text;
        public TextView chinese_text;
        public View mask;
        public ImageView record;
        public ImageView playAudio;
        public ImageView playBack;
        public LinearLayout iconpanel;

        public ViewHolder(View itemView){
            this.itemView = itemView;
            iconpanel = itemView.findViewById(R.id.buttonPanel);
            record = itemView.findViewById(R.id.record);
            playBack = itemView.findViewById(R.id.playback);
            playAudio = itemView.findViewById(R.id.playaudio);
            mask = itemView.findViewById(R.id.mask);
            english_text = itemView.findViewById(R.id.speakingpractice_en);
            chinese_text = itemView.findViewById(R.id.speakingpractice_cn);
            banner = itemView.findViewById(R.id.scorebanner);
            score = itemView.findViewById(R.id.scoretext);
        }

    }


    private int mSelect = 0;   //选中项

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }


}
