package com.copycat.model;

import android.graphics.Bitmap;

public class Photo {
    private String photoName;
    private String photoUrl;
    private Bitmap photoImage;

    public Photo(String photoName, String photoUrl, Bitmap photoImage) {
        this.photoName = photoName;
        this.photoUrl = photoUrl;
        this.photoImage = photoImage;
    }

    public String getPhotoName() {return photoName;}
    public void setPhotoName(String photoName) {this.photoName = photoName;}

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String url) {
        this.photoUrl = url;
    }

    public Bitmap getPhotoImage() {
        return photoImage;
    }

    public void setPhotoImage(Bitmap photoImage) {
        this.photoImage = photoImage;
    }
}
