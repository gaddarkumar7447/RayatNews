package com.ftg2021.rayatnews.Model;

public class NewsDataModel {

    private boolean isAds;
    private int id;
    private String heading;
    private String description;
    private String image;
    private String postedAt;
    private String source;
    private String publisher;
    private String sourceUrl;
    private int bFlag=0;


    public boolean isAds() {
        return isAds;
    }

    public void setAds(boolean ads) {
        isAds = ads;
    }

    public NewsDataModel(int id, String heading, String description, String image, String postedAt, String source,int bFlag) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.image = image;
        this.postedAt = postedAt;
        this.source = source;
        this.bFlag = bFlag;
    }


    public NewsDataModel(int id, String heading, String description, String image, String postedAt, String source,String publisher,String sourceUrl,boolean isAds,int bFlag) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.image = image;
        this.postedAt = postedAt;
        this.source = source;
        this.publisher = publisher;
        this.sourceUrl = sourceUrl;
        this.isAds=isAds;
        this.bFlag = bFlag;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getbFlag() {
        return bFlag;
    }

    public void setbFlag(int bFlag) {
        this.bFlag = bFlag;
    }
}
