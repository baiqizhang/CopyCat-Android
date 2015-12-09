package com.copycat.util.remote;

import android.util.Log;

import com.copycat.model.Post;
import com.copycat.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
/**
 * Created by baiqizhang on 11/14/15.
 */
public class PostUtil {

    //Timeline
    public static List<Post> getUserFeed(User user,int skipCount,int count){
        final Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.v("user json:",json);

        String parsedString = "";

        try {

            URL url = new URL("http://10.0.0.8:8080/CopyCatServer/PostUtil/GetPost");
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            parsedString = convertinputStreamToString(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("get json:",parsedString);
        return gson.fromJson(parsedString,new TypeToken<List<Post>>(){}.getType());
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




    //Helper
    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

}
