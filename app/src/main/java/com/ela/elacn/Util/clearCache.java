package com.ela.elacn.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ela.elacn.R;

import java.io.File;
import java.math.BigDecimal;

import es.dmoral.toasty.Toasty;


public class clearCache {
    //recursive get size
    public static double getSize(String filepath){
        File dir = new File(filepath);
        if (dir.exists()) {
            double result = 0;
            File[] fileList = dir.listFiles();
            for(int i = 0; i < fileList.length; i++) {
                // Recursive call if it's a directory
                if(fileList[i].isDirectory()) {
                    result += getSize(fileList [i].getPath());
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length();
                }
            }
            return result/1024.0/1024.0; // return the file size
        }
        return 0;
    }

    public static void clearCache(final String filepath, final Context where){

        new AlertDialog.Builder(where )
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtil.deleteDir(filepath);



                        Toasty.success(where, "Success!", Toast.LENGTH_SHORT, true).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }

    public static void main(String[] args) {
        double test = 1.291929192;

        BigDecimal bg = new BigDecimal(test);

        double f1 = bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println(f1);

        System.out.println(String.format("%.2f",test));

    }
}
