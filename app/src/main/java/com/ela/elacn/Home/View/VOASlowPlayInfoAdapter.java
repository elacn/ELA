package com.ela.elacn.Home.View;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

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

        /*
        simpleText.onClick(viewHolder.textview_en, new OnTextClickListener() {
            @Override
            public void onClicked(CharSequence text, Range range, Object tag) {

                QueryTranslation(String.valueOf(text));
            }
        });
*/
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

    private void QueryTranslation(String text){

        //查词对象初始化，请设置source参数为app对应的名称（英文字符串）
        Language langFrom = LanguageUtils.getLangByName("英文");
        //若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        //Language langFrom = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName("中文");

        TranslateParameters tps = new TranslateParameters.Builder()
                .source("ydtranslate-demo")
                .from(langFrom).to(langTo).build();

        Translator translator = Translator.getInstance(tps);


        //查询，返回两种情况，一种是成功，相关结果存储在result参数中，另外一种是失败，失败信息放在TranslateErrorCode中，TranslateErrorCode是一个枚举类，整个查询是异步的，为了简化操作，回调都是在主线程发生。

        translator.lookup(text, "requestId", new TranslateListener() {
            @Override
            public void onError(TranslateErrorCode translateErrorCode, String s) {

                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResult(final Translate translate, String s, String s1) {
                translate.getExplains();

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        String translateString = new String();

                        for (int i=0;i<translate.getExplains().size();i++){

                            translateString = translateString + translate.getExplains().get(i);
                        }

                        Toast.makeText(context,translateString,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private Handler mainHandler = new Handler(Looper.getMainLooper());
}
