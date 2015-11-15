package com.copycat.util.remote;

import com.copycat.model.Post;
import com.copycat.model.User;

import java.util.List;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class PostUtil {
    //Timeline
    public static List<Post> getUserFeed(User user,int skipCount,int count){return null;}
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
