package com.copycat.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frankluo on 11/13/15.
 */

public class CategoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "CopyCat.db";

    public static final String TABLE_NAME = "categoryTbl";

    public static final String CATEGORY_URI = "categoryUri";
    public static final String CATEGORY_NAME = "categoryName";

    private static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    CATEGORY_URI + " TEXT NOT NULL UNIQUE, " +
                    CATEGORY_NAME + " TEXT NOT NULL); ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public CategoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
