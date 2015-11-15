package com.copycat.util.db;

import android.provider.BaseColumns;

/**
 * Created by frankluo on 11/13/15.
 */
public abstract class UserInfoDb implements BaseColumns {
    public static final String TABLE_NAME = "userInfoTbl";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_INFO = "userInfo";
    public static final String PROFILE_PICTURE_ID = "profilePictureId";

}
