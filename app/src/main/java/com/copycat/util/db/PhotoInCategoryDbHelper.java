package com.copycat.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frankluo on 12/9/15.
 */
public class PhotoInCategoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "CopyCat.db";

    public static final String TABLE_NAME = "photoInCategoryTbl";
    public static final String CATEGORY_ID = "categoryId";
    public static final String PHOTO_ID = "photoId";

    private static final String CREATE_PHOTO_CATEGORY_TABLE =
            "CREATE TABLE" + TABLE_NAME + " (" +
                    CATEGORY_ID + "INTEGER, " +
                    PHOTO_ID + "INTEGER, " +
                    "PRIMARY KEY("+ CATEGORY_ID + "," +PHOTO_ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public PhotoInCategoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PHOTO_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}