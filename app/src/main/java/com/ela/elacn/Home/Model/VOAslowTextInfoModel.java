package com.ela.elacn.Home.Model;

import android.view.ViewDebug;

public class VOAslowTextInfoModel {


    public int getStart() {
        String[] time = start.split("[.:]");
        String timeString = "";
        int num = 0;


        num+=Integer.parseInt(time[0])*3600000;

        num+=Integer.parseInt(time[1])*60000;

        num+=Integer.parseInt(time[2])*1000;

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

        num+=Integer.parseInt(time[2])*1000;

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

    private String end; // audio end time

    private String chinese;

    private String english;
}
