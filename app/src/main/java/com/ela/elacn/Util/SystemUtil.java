package com.ela.elacn.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ela.elacn.BuildConfig;

public class SystemUtil {
    public static String getName(){
        return BuildConfig.APPLICATION_ID;
        //PackageManager packageManager = context.getPackageManager();

        //return context.getPackageName();
    }




}
