package com.copycat.util.remote;

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.model.User;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class UserUtil {
    private static User currentUser = new User("Anonymous");

    public static User getCurrentUser(){
        return currentUser;
    }




}
