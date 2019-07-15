package com.ela.elacn.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class permissioncheck {


    public static void requestpermission(Context where, Activity activity,int requestCode, String[] permission){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(activity, permission,
                        requestCode);
        }
    }

    public static Boolean checkpermissions(Context where,String permission){
        return ContextCompat.checkSelfPermission(where,
                permission) == PackageManager.PERMISSION_GRANTED;
    }





}
