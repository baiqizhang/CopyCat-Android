package com.copycat.util.db;

import android.provider.BaseColumns;

/**
 * Created by frankluo on 11/13/15.
 */
public abstract class PhotoDb implements BaseColumns {
    public static final String TABLE_NAME = "photoTbl";
    public static final String PHOTO_ID = "photoId";
    public static final String PHOTO_URL = "photoUrl";
    public static final String OWNER_ID = "ownerId";
    //public static final String PHOTO_FILE = "photoFile";
}
