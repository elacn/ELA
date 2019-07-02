package com.ela.elacn.Home.Model;

import java.io.Serializable;

public class VOAslowModel implements Serializable {


    private static final long serialVersionUID = 1012373L;

    private data data;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    private int messageId;

    public com.ela.elacn.Home.Model.data getData() {
        return data;
    }

    public void setData(com.ela.elacn.Home.Model.data data) {
        this.data = data;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String id;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;

}


