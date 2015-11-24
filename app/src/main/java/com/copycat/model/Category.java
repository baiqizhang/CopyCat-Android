package com.copycat.model;

import android.graphics.Picture;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by ningtuffy on 11/13/15.
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private Picture banner;
    List <Photo> photoList = new ArrayList<Photo> ();

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
