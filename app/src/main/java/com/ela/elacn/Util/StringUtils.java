package com.ela.elacn.Util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ela.elacn.$;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class StringUtils {

    public static final String EMPTY = "";
    public static final String LOG_TAG = "StringUtils";

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank (String str) {
        return isEmpty(str) || isEmpty(str.trim());
    }

    public static boolean isEmpty(String str) {
        return str == null || EMPTY.equals(str);
    }

    public static String fromBytes(byte[] bytes) {
        if(bytes == null || bytes.length < 1)
            return null;
        try {
            return new String(bytes, $.ENCODE);
        } catch (UnsupportedEncodingException e) {
            Log.w(LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

    public static void appendRichStr(SpannableStringBuilder builder, String text, Integer color, ClickableSpan clickEvent) {
        if(isEmpty(text)|| builder == null)
            return ;
        SpannableString spanStr = new SpannableString(text);
        if(clickEvent != null)
            spanStr.setSpan(clickEvent, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(color != null) {
            ForegroundColorSpan fcs1 = new ForegroundColorSpan(color);
            spanStr.setSpan(fcs1,0, spanStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体的颜色
        }
        builder.append(spanStr);
    }

    public static String viewText(View v) {
        if(v instanceof TextView) {
            return ((TextView) v).getText().toString();
        } else {
            return "";
        }
    }

    public static String pathMerge(String base, String suf) {
        return pathMerge(base, suf, '/');
    }

    public static String pathMerge(String base, String suf, char connector) {
        if(base == null || base.length() < 1)
            return suf;
        if(suf == null || suf.length() < 1 )
            return base;


        if(suf.startsWith("http://") || suf.startsWith("https://"))
            return suf;
        StringBuilder urlBuilder = new StringBuilder(base);
        int prefixMaxInx = base.length() - 1;
        if(base.charAt(prefixMaxInx) == connector)
            urlBuilder.setLength(prefixMaxInx);
        if(suf.charAt(0) != connector)
            urlBuilder.append(connector);
        return urlBuilder.append(suf).toString();
    }

    public static String join(List<String> phones, String j) {
        if(phones == null || phones.isEmpty())
            return "";
        String r = "";
        for(String p : phones) {
            if(isEmpty(r))
                r = p;
            else
                r = $.nonull(j) + p;
        }
        return r;
    }

    public static String md5encode(String toencode){
        if(toencode.isEmpty()) return "";

        byte[] bytesOfMessage = toencode.getBytes();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] thedigest = md.digest(bytesOfMessage);

        String data = new String(thedigest);

        return data;
    }

}
