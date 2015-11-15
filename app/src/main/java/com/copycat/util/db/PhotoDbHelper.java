package com.copycat.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by frankluo on 11/13/15.
 */
public class PhotoDbHelper extends SQLiteOpenHelper {

        private static final String TEXT_TYPE = "TEXT";

        private static final String CREATE_PHOTO_TABLE =
                "CREATE TABLE" + PhotoDb.TABLE_NAME + " (" +
                        PhotoDb.PHOTO_ID + TEXT_TYPE + "," +
                        PhotoDb.OWNER_ID + TEXT_TYPE + "," +
                        PhotoDb.PHOTO_URL + TEXT_TYPE +
                        " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + CategoryDb.TABLE_NAME;

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "CopyCat.db";

        public PhotoDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PHOTO_TABLE);
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
