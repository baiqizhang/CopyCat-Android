package com.copycat.util.db;

import android.provider.BaseColumns;

/**
 * Created by frankluo on 11/13/15.
 */

public abstract class CategoryDb implements BaseColumns{
    public static final String TABLE_NAME = "categoryTbl";
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
}
