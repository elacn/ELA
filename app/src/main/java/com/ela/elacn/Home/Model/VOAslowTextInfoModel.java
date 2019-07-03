package com.ela.elacn.Home.Model;

import android.view.ViewDebug;

public class VOAslowTextInfoModel {

    public int getStart() {
        String[] time = start.split("[.:]");
        String timeString = "";

        for (int i = 0; i < time.length ; i++){
            timeString = timeString + time[i];
        }

        timeString = timeString + "0";

        int export = Integer.valueOf(timeString);

        return export;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getEnd() {
        String[] time = end.split("[.:]");
        String timeString = "";

        for (int i = 0; i < time.length ; i++){
            timeString = timeString + time[i];
        }

        timeString = timeString + "0";

        int export = Integer.valueOf(timeString);

        return export;
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
