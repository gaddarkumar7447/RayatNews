package com.ftg2021.rayatnews.Model;

import java.io.Serializable;

public class Banner implements Serializable {
    private String bannerId;
    private String path;

    public Banner(String bannerId,String path){

        this.bannerId = bannerId;
        this.path = path;

    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}