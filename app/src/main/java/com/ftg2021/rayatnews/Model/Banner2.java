package com.ftg2021.rayatnews.Model;

import java.io.Serializable;

public class Banner2 implements Serializable {
    private String bannerId;
    private int path;

    public Banner2(String bannerId,int path){

        this.bannerId = bannerId;
        this.path = path;

    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}