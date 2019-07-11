package com.ela.elacn.Home.View;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ViewHolder viewHolder;

        if(view == null) {

            View layoutView = LayoutInflater.from(context).inflate(R.layout.speaking_practice_adapter, parent, false);

            viewHolder = new ViewHolder(layoutView);
            layoutView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        VOAslowTextInfoModel model = dataSource.get(position);

        if(mSelect==position){
            viewHolder.record.setVisibility(View.VISIBLE);
           viewHolder.mask.setAlpha(0);
        }else{
            viewHolder.record.setVisibility(View.INVISIBLE);
           viewHolder.mask.setAlpha(0.3f);
        }

        viewHolder.english_text.setText(model.getEnglish());
        viewHolder.chinese_text.setText(model.getChinese());


        return viewHolder.itemView;
    }

    public static class ViewHolder{

        private View itemView;
        private TextView english_text;
        private TextView chinese_text;
        private View mask;
        private ImageView record;

        public ViewHolder(View itemView){
            this.itemView = itemView;
            record = itemView.findViewById(R.id.record);
            mask = itemView.findViewById(R.id.mask);
            english_text = itemView.findViewById(R.id.speakingpractice_en);
            chinese_text = itemView.findViewById(R.id.speakingpractice_cn);
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
