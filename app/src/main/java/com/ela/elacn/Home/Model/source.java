package com.ela.elacn.Home.Model;

import java.io.Serializable;

public class source implements Serializable {

    private static final long serialVersionUID = 1012374L;

    private String displayname; //
    private String username;


    public String getDisplayname() {
        return displayname;
    }

    public String getUsername() {
        return username;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
