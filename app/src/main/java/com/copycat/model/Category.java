package com.copycat.model;

import android.graphics.Bitmap;
import android.graphics.Picture;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by ningtuffy on 11/13/15.
 */
public class Category {
    private int categoryId;
    private String categoryName;
    List <Bitmap> photoList = new ArrayList<Bitmap> ();

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int Id) {
        this.categoryId = Id;
    }
}
