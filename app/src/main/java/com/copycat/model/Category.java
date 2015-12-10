package com.copycat.model;

import android.graphics.Bitmap;
import android.graphics.Picture;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by ningtuffy on 11/13/15.
 */
public class Category {
    private String categoryName;
    private Bitmap banner;
    private String categoryUri;
    private int count = 0;

    public String getCategoryUri() {
        return categoryUri;
    }

    public void setCategoryUri(String categoryUri) {
        this.categoryUri = categoryUri;
    }

    public Category(String categoryName, Bitmap banner, String categoryUri) {
        this.categoryName = categoryName;
        this.banner = banner;
        this.categoryUri = categoryUri;
    }

    public Category(String categoryName, Bitmap banner, int count) {
        this.categoryName = categoryName;
        this.banner = banner;
        this.count = count;
    }

    public Bitmap getBanner() {
        return banner;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public  int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBanner(Bitmap banner) {
        this.banner = banner;
    }
}
