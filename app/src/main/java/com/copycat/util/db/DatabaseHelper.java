package com.copycat.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frankluo on 11/13/15.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "CopyCat.db";
    private static final String TEXT_TYPE = " TEXT ";

    /************************** CATEGORY *************************************************/
    public static final String CATEGORY_TABLE_NAME = "categoryTbl";
    public static final String CATEGORY_URI = "categoryUri";
    public static final String CATEGORY_NAME = "categoryName";

    private static final String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                    CATEGORY_URI + " TEXT NOT NULL UNIQUE, " +
                    CATEGORY_NAME + " TEXT NOT NULL); ";

    private static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;

    /*************************** PHOTO ************************************************/

    public static final String PHOTO_TABLE_NAME = "photoTbl";
    public static final String PHOTO_URI = "photoUri";
    public static final String PHOTO_NAME = "photoName";

    private static final String CREATE_PHOTO_TABLE =
            "CREATE TABLE " + PHOTO_TABLE_NAME + " (" +
                    PHOTO_URI + " TEXT NOT NULL UNIQUE," +
                    PHOTO_NAME + " TEXT NOT NULL);";

    private static final String SQL_DELETE_PHOTO =
            "DROP TABLE IF EXISTS " + PHOTO_TABLE_NAME;

    /*************************** CP_RELATION ************************************************/

    public static final String CP_RELATION_TABLE_NAME = "photoInCategoryTbl";

    private static final String CREATE_PHOTO_CATEGORY_TABLE =
            "CREATE TABLE " + CP_RELATION_TABLE_NAME + " (" +
                    CATEGORY_URI + " TEXT NOT NULL, " +
                    PHOTO_URI + " TEXT NOT NULL, " +
                    "PRIMARY KEY("+ CATEGORY_URI + "," + PHOTO_URI + "));";

    private static final String SQL_DELETE_CP_RELATION =
            "DROP TABLE IF EXISTS " + CP_RELATION_TABLE_NAME;

    /*************************** POST ************************************************/

    private static final String CREATE_POST_TABLE =
            "CREATE TABLE " + PostDb.TABLE_NAME + " (" +
                    PostDb.POST_ID + TEXT_TYPE + "," +
                    PostDb.PHOTO_ID + TEXT_TYPE + "," +
                    PostDb.OWNER_ID + TEXT_TYPE + "," +
                    PostDb.LIKE_NUMBER + "INTEGER" + "," +
                    PostDb.POST_TIME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_POST =
            "DROP TABLE IF EXISTS " + PostDb.TABLE_NAME;

    /***************************  USER INFO  ********************************************/

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + UserInfoDb.TABLE_NAME + " (" +
                    UserInfoDb.USER_ID + TEXT_TYPE + "," +
                    UserInfoDb.USER_NAME + TEXT_TYPE + "," +
                    UserInfoDb.USER_INFO + TEXT_TYPE + "," +
                    UserInfoDb.PROFILE_PICTURE_ID + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_USER_INFO =
            "DROP TABLE IF EXISTS " + UserInfoDb.TABLE_NAME;

    /**********************************************************************/

    public DatabaseHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
        db.execSQL(CREATE_PHOTO_CATEGORY_TABLE);
        db.execSQL(CREATE_POST_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CATEGORY);
        db.execSQL(SQL_DELETE_PHOTO);
        db.execSQL(SQL_DELETE_CP_RELATION);
        db.execSQL(SQL_DELETE_POST);
        db.execSQL(SQL_DELETE_USER_INFO);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
