package com.copycat.util.remote;

import android.util.Log;

import com.copycat.model.Post;
import com.copycat.model.User;
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

    private static final String DNS = "ec2-52-90-164-203.compute-1.amazonaws.com";
    private static final Gson gson = new Gson();
    //Timeline
    public static List<Post> getUserFeed(User user,int skipCount,int count){
        String json = gson.toJson(user);
        Log.v("user json:",json);

        String parsedString = "";

        try {

            URL url = new URL("http://"+DNS+"/CopyCatServer/PostUtil/GetPost");
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            parsedString = convertInputStreamToString(is);

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
    public static boolean uploadUserPost(User user, Post post)
    {
        InputStream is;
        OutputStream os;
        HttpURLConnection conn;
        //can catch a variety of wonderful things
        try {
            //constants
            URL url = new URL("http://myhost.com/ajax");
            String message = gson.toJson(post);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout( 10000 /*milliseconds*/ );
            conn.setConnectTimeout( 15000 /* milliseconds */ );
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);

            //make some HTTP header nicety
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //open
            conn.connect();

            //setup send
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //clean up
            os.flush();

            //do somehting with response
            is = conn.getInputStream();
            String contentAsString = convertInputStreamToString(is);
        } catch (Exception e ){
            e.printStackTrace();
        }
        finally {
            //clean up
            os.close();
            is.close();
            conn.disconnect();
        }


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
    public static String convertInputStreamToString(InputStream ists)
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
