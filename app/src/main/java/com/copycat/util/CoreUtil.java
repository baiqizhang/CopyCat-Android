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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.copycat.controller.TimelineActivity;
import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.db.DatabaseHelper;
import com.copycat.util.remote.PostUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CoreUtil implements LocationListener {

    //Location
    public Location getLocation(Context context) {
        LocationManager lManager;
        lManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        Location loc = null;

        try {
            lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 500.0f, this);
            loc = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Log.d("lllll",loc.getLatitude()+" "+loc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Toast.makeText(context,loc.getLatitude()+","+loc.getLongitude(),Toast.LENGTH_SHORT);
        return loc;
    }

    public static boolean addSetting(String key, String value, Context context) {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SETTING_KEY,key);
            values.put(DatabaseHelper.SETTING_VALUE, value);

            db.insert(DatabaseHelper.SETTING_TABLE_NAME, null, values);
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getSetting(String key, Context context) {
        String value = "Setting Key Not Found";
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query(
                    DatabaseHelper.SETTING_TABLE_NAME,
                    new String[]{DatabaseHelper.SETTING_VALUE},
                    DatabaseHelper.SETTING_KEY + "= ?",
                    new String[] {key},
                    null, null, null, null);

            if(cursor!=null) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    value = cursor.getString(0);
                }
            }
            db.close();
            return value;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean setSetting(String key, String value, Context context) {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SETTING_VALUE, value);
            db.update(DatabaseHelper.SETTING_TABLE_NAME, values, DatabaseHelper.SETTING_KEY + " = ?",new String[] {key} );
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Category
    public static List<Category> getCategoryListFromDB(Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[2];
        columns[0] = DatabaseHelper.CATEGORY_URI;
        columns[1] = DatabaseHelper.CATEGORY_NAME;
        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE_NAME,columns,null,null,null,null,null,null);

        List<Category> cList = new ArrayList<>();

        for( cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String cUri = cursor.getString(0);
            String cName = cursor.getString(1);
            Bitmap banner = BitmapFactory.decodeFile(cUri);

            cList.add(new Category(cName,banner,cUri));
        }
        db.close();
        return cList;
    }

    public static int getPhotoAmountInCategory(String categoryUri, Context context) {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = new String[2];
            columns[0] = DatabaseHelper.CATEGORY_URI;

            Cursor cursor = db.query(DatabaseHelper.CP_RELATION_TABLE_NAME,
                    columns,
                    DatabaseHelper.CATEGORY_URI + " = ?",
                    new String[]{categoryUri},
                    null,null,null,null);

            int amount = 0;
            if(cursor!=null) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    amount ++;
                }
            }
            db.close();
            return amount;

        } catch (SQLiteException e) {
            e.printStackTrace();
            return -1;
        }
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
            values.put(DatabaseHelper.CATEGORY_URI, myPath.getAbsolutePath());
            values.put(DatabaseHelper.CATEGORY_NAME, cName);

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(DatabaseHelper.CATEGORY_TABLE_NAME, null, values);
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
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(DatabaseHelper.CATEGORY_TABLE_NAME, DatabaseHelper.CATEGORY_URI + "= ?" ,new String[] {category.getCategoryUri()});
            db.delete(DatabaseHelper.CP_RELATION_TABLE_NAME,DatabaseHelper.CATEGORY_URI + "= ?" ,new String[] {category.getCategoryUri()});
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Photo
    //Category should have been stored in db so that it has uri value
    public static boolean addPhotoListToCategory(List<Photo> photoList,String categoryUri, Context context){

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            for(Photo photo : photoList) {

                String photoUri = photo.getPhotoUrl();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.CATEGORY_URI, categoryUri);
                values.put(DatabaseHelper.PHOTO_URI, photoUri);
                db.insert(DatabaseHelper.CP_RELATION_TABLE_NAME, null, values);
            }
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    //category must be stored in DB in advance, so that it have absolutePath/URI
    public static List<Photo> getPhotoListWithCategory(String categoryUri, Context context) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = new String[2];
        columns[0] = DatabaseHelper.CATEGORY_URI;
        columns[1] = DatabaseHelper.PHOTO_URI;

        Cursor cursor = db.query(DatabaseHelper.CP_RELATION_TABLE_NAME,
                columns,
                DatabaseHelper.CATEGORY_URI + " = ?",
                new String[]{categoryUri},
                null,null,null,null);

        if(cursor!=null) {
            List<Photo> pList = new ArrayList<>();

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String photoUri = cursor.getString(1);
                Bitmap photo = BitmapFactory.decodeFile(photoUri.substring(4));

                File f = new File(photoUri.substring(4));
                String photoName = f.getName();

                pList.add(0,new Photo(photoName, photoUri));
            }
            db.close();
            return pList;
        }
        db.close();
        return  null;
    }

    public static boolean removePhotoList(List<Photo> photoList, Context context){
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for(Photo photo: photoList) {
                db.delete(DatabaseHelper.CP_RELATION_TABLE_NAME,
                        DatabaseHelper.PHOTO_URI + " = ?",
                        new String[] {photo.getPhotoUrl()});
                db.delete(DatabaseHelper.PHOTO_TABLE_NAME, DatabaseHelper.PHOTO_URI + " = ?", new String[]{photo.getPhotoUrl()});
                //Toast.makeText(context, "Delete:" + photo.getPhotoUrl(), Toast.LENGTH_LONG);
            }
            db.close();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Photo storePhotoLocally(Bitmap bitmapImage,String imageName, String imagePath, Context context) {

        if(imagePath==null) {
            //store locally
            ContextWrapper cw = new ContextWrapper(context);
            // path to /data/data/yourapp/app_data/photoImageDir
            File directory = cw.getDir("photoImageDir", Context.MODE_PRIVATE);
            File myPath = new File(directory, imageName + ".png");
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
            values.put(DatabaseHelper.PHOTO_URI, myPath.getAbsolutePath());
            values.put(DatabaseHelper.PHOTO_NAME, imageName);

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(DatabaseHelper.PHOTO_TABLE_NAME, null, values);
            db.close();

            return new Photo(imageName, "file" + myPath.getAbsolutePath());
        } else {
            //register in db
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.PHOTO_URI, imagePath);
            values.put(DatabaseHelper.PHOTO_NAME, imageName);

            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(DatabaseHelper.PHOTO_TABLE_NAME, null, values);
            db.close();

            return new Photo(imageName, "file" + imagePath);
        }
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

    public static Bitmap scaleBitmap(Bitmap bitmap, double scale) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        height*=scale;
        width*=scale;
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
