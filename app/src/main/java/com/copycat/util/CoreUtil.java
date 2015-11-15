package com.copycat.util;

import com.copycat.model.Category;
import com.copycat.model.Photo;

import java.util.List;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class CoreUtil {
    //Category
    public static List<Category> getCategoryList(){return null;}
    public static boolean addCategory(Category category){return false;}
    public static boolean removeCategory(Category category){return false;}

    //Photo
    public static boolean addPhotoListToCategory(List<Photo> photoList,Category category){return false;}
    public static boolean removePhotoListFromCategory(List<Photo> photoList,Category category){return false;}

}
