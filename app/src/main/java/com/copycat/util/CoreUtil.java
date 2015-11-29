package com.copycat.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.example.baiqizhang.copycat.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class CoreUtil {
    //Category
    public static List<Category> getCategoryListFromStorage(){return null;}
    public static boolean addCategory(Category category){return false;}
    public static boolean removeCategory(Category category){return false;}

    //Photo
    public static boolean addPhotoListToCategory(List<Photo> photoList,Category category){return false;}
    public static boolean removePhotoListFromCategory(List<Photo> photoList,Category category){return false;}


    private String saveToInternalSorage(Bitmap bitmapImage,Context context){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            ImageView img=(ImageView)findViewById(R.id.imgPicker);
//            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
