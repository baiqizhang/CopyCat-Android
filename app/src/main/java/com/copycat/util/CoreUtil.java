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

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.db.CategoryDbHelper;
import com.copycat.util.db.PhotoInCategoryDbHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class CoreUtil {
    //Category
    public static List<Category> getCategoryListFromDB(Context context){
        CategoryDbHelper dbHelper = new CategoryDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[2];
        columns[0] = CategoryDbHelper.CATEGORY_URI;
        columns[1] = CategoryDbHelper.CATEGORY_NAME;
        Cursor cursor = db.query(CategoryDbHelper.TABLE_NAME,columns,null,null,null,null,null,null);

        List<Category> cList = new ArrayList<>();

        for( cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String cUri = cursor.getString(0);
            String cName = cursor.getString(1);
            Bitmap banner = BitmapFactory.decodeFile(cUri);

            cList.add(new Category(cName,banner,cUri));
        }

        return cList;
    }

    public static Category addCategory(Category category, Context context){
        try {
            String cName = category.getCategoryName();
            Bitmap banner = category.getBanner();

            //store locally
            ContextWrapper cw = new ContextWrapper(context);
            // path to /data/data/yourapp/app_data/categoryImageDir
            File directory = cw.getDir("categoryImageDir", Context.MODE_PRIVATE);
            File myPath=new File(directory,cName +".png");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myPath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                banner.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            //insert into db
            ContentValues values = new ContentValues();
            values.put(CategoryDbHelper.CATEGORY_URI, myPath.getAbsolutePath());
            values.put(CategoryDbHelper.CATEGORY_NAME, cName);

            CategoryDbHelper dbHelper = new CategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(CategoryDbHelper.TABLE_NAME, null, values);
            db.close();
            category.setCategoryUri(myPath.getAbsolutePath());
            return category;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean removeCategory(Category category, Context context){
        try {
            CategoryDbHelper dbHelper = new CategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(CategoryDbHelper.TABLE_NAME,CategoryDbHelper.CATEGORY_URI + "=" + category.getCategoryUri(),null);
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Photo
    //Category should have been stored in db so that it has uri value
    public static boolean addPhotoListToCategory(List<Photo> photoList,Category category, Context context){

        try {
            PhotoInCategoryDbHelper dbHelper = new PhotoInCategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            for(Photo photo : photoList) {

                String photoUri = photo.getPhotoUrl();
                String categoryUri = category.getCategoryUri();
                ContentValues values = new ContentValues();
                values.put(PhotoInCategoryDbHelper.CATEGORY_URI, categoryUri);
                values.put(PhotoInCategoryDbHelper.PHOTO_URI, photoUri);
                db.insert(PhotoInCategoryDbHelper.TABLE_NAME, null, values);
            }
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //category must be stored in DB in advance, so that it have absolutePath/URI
    public static List<Photo> getPhotoListWithCategory(Category category, Context context) {

        PhotoInCategoryDbHelper dbHelper = new PhotoInCategoryDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = new String[2];
        columns[0] = PhotoInCategoryDbHelper.CATEGORY_URI;
        columns[1] = PhotoInCategoryDbHelper.PHOTO_URI;

        Cursor cursor = db.query(PhotoInCategoryDbHelper.TABLE_NAME,
                columns,
                PhotoInCategoryDbHelper.CATEGORY_URI + " MATCH " + category.getCategoryUri(),
                null,null,null,null,null);

        if(cursor!=null) {
            List<Photo> pList = new ArrayList<>();

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String photoUri = cursor.getString(1);
                Bitmap photo = BitmapFactory.decodeFile(photoUri);

                File f = new File(photoUri);
                String photoName = f.getName();

                pList.add(new Photo(photoName, photoUri, photo));
            }
            db.close();
            return pList;
        }
        db.close();
        return  null;
    }

    public static boolean removePhotoListFromCategory(List<Photo> photoList,Category category, Context context){
        try {
            PhotoInCategoryDbHelper dbHelper = new PhotoInCategoryDbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for(Photo photo: photoList) {
                db.delete(PhotoInCategoryDbHelper.TABLE_NAME,
                        PhotoInCategoryDbHelper.CATEGORY_URI + " MATCH " + category.getCategoryName() + "AND" +
                                PhotoInCategoryDbHelper.PHOTO_URI + " MATCH " + photo.getPhotoUrl(),
                        null);
            }
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Photo storePhotoLocally(Bitmap bitmapImage,String imageName, Context context) {

        //store locally
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/categoryImageDir
        File directory = cw.getDir("photoImageDir", Context.MODE_PRIVATE);
        File myPath=new File(directory,imageName +".png");
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

        //insert into db
        ContentValues values = new ContentValues();
        values.put(PhotoInCategoryDbHelper.PHOTO_URI, myPath.getAbsolutePath());
        values.put(PhotoInCategoryDbHelper.CATEGORY_URI, imageName);

        PhotoInCategoryDbHelper dbHelper = new PhotoInCategoryDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(PhotoInCategoryDbHelper.TABLE_NAME, null, values);
        db.close();

        return new Photo(imageName, myPath.getAbsolutePath(),bitmapImage);
    }

    private Bitmap loadImageFromStorage(String absolutePath)
    {
        try {
            return BitmapFactory.decodeFile(absolutePath);
            //imgView.setImageBitmap(b);
        }
        catch (Exception e)
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
