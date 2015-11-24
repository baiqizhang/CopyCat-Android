package com.copycat.model;

import android.graphics.Picture;

/**
 * Created by ningtuffy on 11/13/15.
 */
public class Photo {
    private int photoId;
//    private Picture picture;
//  (not sure if we should use it here because it is memory consuming
    private Picture thumbNail;
    private User owner;
    private String photoUrl;

    public int getPhotoId() {
        return photoId;
    }
//    public Picture getPicture() {
//        return picture;
//    }
    public User getOwner() {
        return owner;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoId(int id) {
        this.photoId = id;
    }
    public void setOwner(User user) {
        this.owner = user;
    }
//    public void setPicture(Picture picture) {
//        this.picture = picture;
//    }
    public void setPhotoUrl(String url) {
        this.photoUrl = url;
    }
}
