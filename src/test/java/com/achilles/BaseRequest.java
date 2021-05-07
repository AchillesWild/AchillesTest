package com.achilles;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 9165052969272861134L;

    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
