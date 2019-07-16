package com.ela.elacn.Home.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class speechmodel {

    public int getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(int pronunciation) {
        this.pronunciation = pronunciation;
    }

    public ArrayList<wordobject> getWords() {
        return words;
    }

    public void setWords(ArrayList<wordobject> words) {
        this.words = words;
    }

    private int pronunciation;

private ArrayList<wordobject> words;




}
