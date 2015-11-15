package com.copycat.model;

import android.graphics.Picture;

/**
 * Created by ningtuffy on 11/13/15.
 */
public class User {
    private int userId;
    private String name;
    private String info;
    private Picture profilePicture;

    public int getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }

    public Picture getPicture() {
        return this.profilePicture;
    }
    public void setUserId(int id) {
        this.userId = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public void setPicture(Picture picture) {
        this.profilePicture = picture;
    }

}
