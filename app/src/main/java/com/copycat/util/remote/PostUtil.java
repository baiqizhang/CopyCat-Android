package com.copycat.util.remote;

import android.util.Log;

import com.copycat.model.Post;
import com.copycat.model.User;
import com.copycat.util.ServerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
/**
 * Created by baiqizhang on 11/14/15.
 */
public class PostUtil {
    private static final Gson gson = new Gson();
    //Timeline
    public static List<Post> getUserFeed(User user){
        String parsedString = ServerUtil.HTTPPostWithObject(null,
                "/CopyCatServer/PostUtil/GetPost");
        Log.v("get json:",parsedString);
        if (!parsedString.equals(""))
            return gson.fromJson(parsedString,new TypeToken<List<Post>>(){}.getType());
        else
            return new ArrayList<>();
    }

    public static List<Post> getUserTimeline(User user,int skipCount,int count){
        return null;
    }

    //Post
    public static boolean uploadUserPost(Post post)
    {
        ServerUtil.HTTPPostWithObject(post,
                "/CopyCatServer/PostUtil/NewPost");
        return true;
    }
    public static boolean deleteUserPost(User user, Post post){
        return false;
    }

}
