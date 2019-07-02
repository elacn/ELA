package com.ela.elacn.Home.Model;

import java.io.Serializable;

public class post implements Serializable {

    private static final long serialVersionUID = 1012375L;

    private int pageviews;


    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }


    public int getPageviews(){
        return pageviews;
    }
}
