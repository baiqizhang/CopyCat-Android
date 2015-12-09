package com.copycat.util.remote;

import android.util.Log;

import com.copycat.model.Post;
import com.copycat.model.User;
import com.google.gson.Gson;

import java.util.List;
/**
 * Created by baiqizhang on 11/14/15.
 */
public class PostUtil {
    //Timeline
    public static List<Post> getUserFeed(User user,int skipCount,int count){
        final Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.v("user json:",json);
        return null;
    }
    public static List<Post> getUserTimeline(User user,int skipCount,int count){
        return null;
    }

    //Post
    public static boolean uploadUserPost(User user, Post post){
        return false;
    }
    public static boolean deleteUserPost(User user, Post post){
        return false;
    }
    public static boolean userLike(User user, Post post){
        return false;
    }
    public static boolean userUnLike(User user, Post post){return false;}

}
