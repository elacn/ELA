package com.ela.elacn;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.ela.elacn.Model.Result;
import com.ela.elacn.Util.FileUtil;
import com.ela.elacn.Util.StringUtils;
import com.ela.elacn.Util.SystemUtil;
import com.ela.elacn.Util.TimeUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

public class $ {

    public static final String DEV_SERVER = "http://english.leanapp.cn";
    public static final String RELEASE_SERVER = "http://english.leanapp.cn";
    private static String User_PHONE = "";


    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+ SystemUtil.getName();
    public static final String MP3_DIRECTORY = ROOT_DIR + File.separator + "MP3";
    public static final String RECORD_PATH = ROOT_DIR + File.separator + "Recorder";
    public static final String YOUDAO_APPKEY = "5fb87219b1669402";


    /* 全局配置 */
    public static final String ENCODE = "utf-8";

    public static String tag(Class<?> clazz) {
        return clazz == null ? "null" : clazz.getSimpleName();
    }


    public static class Userinfo {

        public static String phone = "";
    }

    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String nonull(Object o) {
        if(o == null)
            return StringUtils.EMPTY;
        String r = o.toString();
        return r == null ? StringUtils.EMPTY : r;
    }


    public static class server {
        public static final String context = BuildConfig.DEBUG ? DEV_SERVER : RELEASE_SERVER;
        public static String url(String uri) {
            return StringUtils.pathMerge(server.context, uri);
        }

        public static class app {
            public static final String context = StringUtils.pathMerge(server.context, "app");
            public static String url(String uri) {
                return StringUtils.pathMerge(app.context, uri);
            }
        }

    }

    /* local error result */
    public static class error {
        public static class access {

            public static String data_null_err = "data equal null";
            public static Result<String> data_josn_err = Result.fail(-100, "Can't convert the data to a result object.");
        }

    }

    public static class format {
        private static final String UTIL_DATE_FORMAT = "yyyy年MM月dd日";
        private static final String UTIL_TIME_FORMAT = "yyyy年MM月dd日 HH:mm:ss";

        public static String dateTime(Date d) {
            return TimeUtil.format(d, UTIL_TIME_FORMAT);
        }

        public static String date(Date d) {
            return TimeUtil.format(d, UTIL_DATE_FORMAT);
        }
    }

    public String toDoubleStr(Double num){
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(num);
    }

}
