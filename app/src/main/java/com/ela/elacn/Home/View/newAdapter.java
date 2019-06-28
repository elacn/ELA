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

public class newAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<VOAslowModel> dataSource;

    public newAdapter(ArrayList dataSource,Context context){
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

    public static class ViewHolder{

        private ImageView image;
        private View itemView;
        private TextView title;
        private TextView date;
        private TextView viewCount;

        public ViewHolder(View itemView){
            this.itemView = itemView;
            title = itemView.findViewById(R.id.Title_Text);
            date = itemView.findViewById(R.id.Date_Text);
            viewCount = itemView.findViewById(R.id.View_Count);
            image = itemView.findViewById(R.id.listview_image);
        }

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null) {

            View layoutView = LayoutInflater.from(context).inflate(R.layout.list_adapter, viewGroup, false);

            viewHolder = new ViewHolder(layoutView);
            layoutView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        VOAslowModel model = dataSource.get(position);

        viewHolder.title.setText(model.getData().getTitle());
        viewHolder.viewCount.setText(String.valueOf(model.getData().getPost().getPageviews()) + "Views");
        viewHolder.date.setText(model.getCreatedAt());

        Glide.with(context)
                .load(model.getData().getImage().getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(viewHolder.image);

        return viewHolder.itemView;
    }



}