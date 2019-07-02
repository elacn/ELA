package com.ela.elacn.Home.Model;

import java.io.Serializable;

public class data implements Serializable {

    private static final long serialVersionUID = 1012376L;

    private String inboxType;
    private String type;

    private source source;

    public String getInboxType() {
        return inboxType;
    }

    public void setInboxType(String inboxType) {
        this.inboxType = inboxType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public com.ela.elacn.Home.Model.source getSource() {
        return source;
    }

    public void setSource(com.ela.elacn.Home.Model.source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public com.ela.elacn.Home.Model.image getImage() {
        return image;
    }

    public void setImage(com.ela.elacn.Home.Model.image image) {
        this.image = image;
    }

    public com.ela.elacn.Home.Model.post getPost() {
        return post;
    }

    public void setPost(com.ela.elacn.Home.Model.post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String title;

    private String url;//mp3 address

    private image image;

    private post post;

    private String text; //text body

}
