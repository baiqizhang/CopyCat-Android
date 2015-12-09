package com.copycat.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.db.BitmapUtility;
import com.copycat.util.db.CategoryDbHelper;
import com.copycat.util.db.PhotoDbHelper;
import com.example.baiqizhang.copycat.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CoreUtil {
    //Category
    public static List<Category> getCategoryListFromDB(Context context){
        CategoryDbHelper dbHelper = new CategoryDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] tableName = new String[2];
        tableName[0] = CategoryDbHelper.CATEGORY_NAME;
        tableName[1] = CategoryDbHelper.BANNER_IMAGE;
        Cursor cursor = db.query(CategoryDbHelper.TABLE_NAME,tableName,null,null,null,null,null,null);

        List<Category> cList = new ArrayList<>();

        for( cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            byte[] bBanner = (byte[])cursor.getBlob(1);
            Bitmap banner = BitmapUtility.getImage(bBanner);
            cList.add(new Category(cursor.getString(0),banner));
        }

        return cList;
    }

    public static boolean addCategory(Category category, Context context){
        try {
            String cName = category.getCategoryName();

            Bitmap banner = category.getBanner();
            byte[] bBanner = BitmapUtility.getBytes(banner);

            ContentValues values = new ContentValues();
            values.put(CategoryDbHelper.BANNER_IMAGE, bBanner);
            values.put(CategoryDbHelper.CATEGORY_NAME, cName);

            //insert into db
            CategoryDbHelper dbHelper = new CategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(CategoryDbHelper.TABLE_NAME, null, values);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeCategory(Category category, Context context){
        try {
            CategoryDbHelper dbHelper = new CategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(CategoryDbHelper.TABLE_NAME,CategoryDbHelper.CATEGORY_NAME + "=" + category.getCategoryName(),null);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Photo
    public static boolean addPhotoListToCategory(List<Photo> photoList,Category category){return false;}
    public static boolean removePhotoListFromCategory(List<Photo> photoList,Category category){return false;}

    private Photo storePhoto(Bitmap bitmapImage,String imageName, Context context){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath=new File(directory,imageName +".png");

        //store locally
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //store URL to database
        try {

            byte[] bBanner = BitmapUtility.getBytes(bitmapImage);

            ContentValues values = new ContentValues();
            values.put(PhotoDbHelper.PHOTO_URL, directory.getAbsolutePath());
            values.put(PhotoDbHelper.PHOTO_NAME, imageName);

            //insert into db
            CategoryDbHelper dbHelper = new CategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(PhotoDbHelper.TABLE_NAME, null, values);

        } catch (SQLiteException e ) {
            e.printStackTrace();
            return null;
        }
        return new Photo(imageName, directory.getAbsolutePath());
    }

    private Bitmap loadImageFromStorage(String path, String filename)
    {
        try {
            File f=new File(path, filename);
            return BitmapFactory.decodeStream(new FileInputStream(f));
            //imgView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
