package com.ela.elacn.Home.Model;

import android.content.Intent;
import android.view.ViewDebug;
import android.widget.ImageView;

import java.io.Serializable;

public class VOAslowTextInfoModel implements Serializable {

    private static final long serialVersionUID = 1042273L;
    public int getStart() {
        String[] time = start.split("[.:]");
        String timeString = "";
        int num = 0;


        num+=Integer.parseInt(time[0])*3600000;

        num+=Integer.parseInt(time[1])*60000;

        Integer d  = Integer.parseInt(time[3]);

        if(d > 50){

            num+=(Integer.parseInt(time[2]) +1) *1000;

        }else {

            num+=Integer.parseInt(time[2]) *1000;
        }

        num+=Integer.parseInt(time[3]);

        return num;

    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getEnd() {
        String[] time = end.split("[.:]");
        String timeString = "";
        int num = 0;


        num+=Integer.parseInt(time[0])*3600000;

        num+=Integer.parseInt(time[1])*60000;

        Integer d  = Integer.parseInt(time[3]);

//        if(d > 50){
//
//            num+=(Integer.parseInt(time[2]) +1) *1000;
//
//        }else {

            num+=Integer.parseInt(time[2]) *1000;
//        }

        num+=Integer.parseInt(time[3]);

        return num;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    private String start; //auido start time

    public String end; // audio end time

    private String chinese;

    private String english;
}
