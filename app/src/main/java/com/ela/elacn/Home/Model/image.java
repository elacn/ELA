package com.ela.elacn.Home.Model;

import java.io.Serializable;

public class image implements Serializable {

    private static final long serialVersionUID = 1012376L;

    private String url; //image url


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUrl(){
        return url;
    }
}
