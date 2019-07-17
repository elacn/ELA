package com.ela.elacn.Util;

import java.io.File;

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
            return result/1024/1024; // return the file size
        }
        return 0;
    }

    public static void clearCache(String filepath){
        FileUtil.deleteDir(filepath);
    }




}
