package com.copycat.model;

public class Photo {
    private String photoName;
    private String photoUrl;

    public Photo(String photoName, String photoUrl) {
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    public String getPhotoName() {return photoName;}
    public void setPhotoName(String photoName) {this.photoName = photoName;}

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String url) {
        this.photoUrl = url;
    }

}
