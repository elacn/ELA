package com.ela.elacn.Fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ela.elacn.R;
import com.ela.elacn.databinding.ActivityVoaslowInfoBinding;
import com.ela.elacn.databinding.FragmentTranslationBinding;
import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import org.w3c.dom.Text;

import java.util.List;

import static com.youdao.sdk.app.YouDaoApplication.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link translation} interface
 * to handle interaction events.
 * Use the {@link translation} factory method to
 * create an instance of this fragment.
 */
public class translation extends RelativeLayout implements View.OnClickListener {

    private Button closeButton;

    private Context where;

    private TextView definition;

    private TextView title;

    private Boolean isShowing = false;

    public translation(Context context) {
        super(context);
        where = context;
        View v = inflate(context, R.layout.fragment_translation,this);

        closeButton = v.findViewById(R.id.closebutton);
        closeButton.setOnClickListener(this);

        definition = v.findViewById(R.id.definitionText);

        title = v.findViewById(R.id.wordTitle);

    }

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public void translateCard(String text){

        definition.setText("");
        title.setText("searching...");

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

                Toast.makeText(where,s,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResult(final Translate translate, String s, String s1) {

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        String translateString = new String();

                        if(!(translate.getExplains() == null)){

                            for (int i=0;i<translate.getExplains().size();i++){

                                translateString = translateString + translate.getExplains().get(i) + "\n";
                            }


                            title.setText(translate.getQuery());
                            definition.setText(translateString);

                        }
                    }
                });
            }

            @Override
            public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {

                Toast.makeText(where,s,Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void show(boolean showanimation){

        if (isShowing){

            closeButton.setVisibility(View.VISIBLE);

        }else {

            Animation slideup = new TranslateAnimation(0,0,getHeight(),0){};
            slideup.setFillAfter(true);
            closeButton.setVisibility(View.VISIBLE);
            if(showanimation){
                slideup.setDuration(200);
            }
            else{
                slideup.setDuration(0);
            }
            startAnimation(slideup);
        }

        isShowing = true;

    }

    public void hidden(){

        if (!isShowing){
            

        }else {

            hide(true);
        }

        isShowing = false;
    }

    public void hide(boolean showanimation){

        isShowing  = false;
        Animation slidedown = new TranslateAnimation(0,0,0,getHeight()){};
        slidedown.setFillAfter(true);
        if(showanimation){
            slidedown.setDuration(200);
        }
        else{
            slidedown.setDuration(0);
        }
        slidedown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                closeButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(slidedown);

    }

    @Override
    public void onClick(View v) {
        if(v == closeButton){

            hide(true);
        }
    }
}
