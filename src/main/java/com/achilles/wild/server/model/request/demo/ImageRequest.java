package com.achilles.wild.server.model.request.demo;

import java.io.Serializable;

public class ImageRequest implements Serializable {

    private static final long serialVersionUID = 1142397197907212385L;

    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
