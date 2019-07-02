package com.ela.elacn.Home.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.Home.Model.VOAslowTextInfoModel;
import com.ela.elacn.R;
import com.jaychang.st.OnTextClickListener;
import com.jaychang.st.Range;
import com.jaychang.st.SimpleText;

import java.util.ArrayList;
import java.util.List;

public class VOASlowPlayInfoAdapter extends BaseAdapter {

    private ArrayList<VOAslowTextInfoModel> dataSource;

    private Context context;

    public VOASlowPlayInfoAdapter(ArrayList<VOAslowTextInfoModel> dataSource, Context context){
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if(view == null) {

            View layoutView = LayoutInflater.from(context).inflate(R.layout.voaslow_play_info_adapter, parent, false);

            viewHolder = new ViewHolder(layoutView);
            layoutView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        VOAslowTextInfoModel model = dataSource.get(position);

        String[] textArray = model.getEnglish().split(" ");

        List<String> l = new ArrayList<>();

        for (int i = 0; i < textArray.length; i++){

            l.add(textArray[i]);
        }

        SimpleText simpleText = SimpleText.from(model.getEnglish());

        simpleText.allStartWith("");

        simpleText.tags(l);

        simpleText.onClick(viewHolder.textview_en, new OnTextClickListener() {
            @Override
            public void onClicked(CharSequence text, Range range, Object tag) {

                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.textview_en.setText(simpleText);
        viewHolder.textview_cn.setText(model.getChinese());

        return viewHolder.itemView;
    }

    public static class ViewHolder{

        private View itemView;
        private TextView textview_en;
        private TextView textview_cn;

        public ViewHolder(View itemView){
            this.itemView = itemView;
            textview_en = itemView.findViewById(R.id.voaSlow_en_TextView);
            textview_cn = itemView.findViewById(R.id.voaSlow_cn_TextView);
        }

    }
}
