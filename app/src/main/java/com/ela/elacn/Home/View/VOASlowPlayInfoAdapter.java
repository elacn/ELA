package com.ela.elacn.Home.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ela.elacn.Home.Model.VOAslowModel;
import com.ela.elacn.R;

import java.util.ArrayList;

public class VOASlowPlayInfoAdapter extends BaseAdapter {

    private ArrayList dataSource;

    private Context context;

    public VOASlowPlayInfoAdapter(ArrayList dataSource,Context context){
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public int getCount() {
//        return dataSource.size();
        return 10;
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
