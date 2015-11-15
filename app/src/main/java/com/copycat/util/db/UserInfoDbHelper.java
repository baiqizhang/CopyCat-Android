package com.copycat.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frankluo on 11/13/15.
 */
public class UserInfoDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = "TEXT";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE" + UserInfoDb.TABLE_NAME + " (" +
                    UserInfoDb.USER_ID + TEXT_TYPE + "," +
                   UserInfoDb.USER_NAME + TEXT_TYPE + "," +
                    UserInfoDb.USER_INFO + TEXT_TYPE + "," +
                    UserInfoDb.PROFILE_PICTURE_ID + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserInfoDb.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CopyCat.db";

    public UserInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
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