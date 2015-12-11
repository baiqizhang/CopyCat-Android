package com.copycat.util.remote;

import android.os.AsyncTask;
import android.util.Log;

import com.copycat.model.Category;
import com.copycat.model.Photo;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class UserUtil {
    private static User currentUser = null;
//    private static final String DNS = "ec2-52-90-164-203.compute-1.amazonaws.com";
        private static final String DNS = "172.29.92.23:8080";
    private static final Gson gson = new Gson();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logOut() {
        currentUser = null;
    }

    public static User logInOrRegister(String username, String password) {
        User user = new User(username, password);

        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        String contentAsString = ServerUtil.HTTPPostWithObject(user,
                "/CopyCatServer/UserUtil/Login");

        if (contentAsString == null || contentAsString.charAt(0) == 'f')
            return null;
        if (contentAsString.charAt(0) == 'r') {
            currentUser = user;
            return user;
        } else if (contentAsString.charAt(0) == 'l') {
            User newuser = gson.fromJson(contentAsString.substring(1), new TypeToken<User>() {
            }.getType());
            currentUser = newuser;
            return newuser;
        }
        return null;
    }

    public static boolean userSync() {
        String reply = ServerUtil.HTTPPostWithObject(getCurrentUser(),
                "/CopyCatServer/UserUtil/Sync");
        return true;
    }

    public static boolean userLike(final Post post) {
//        class User_Post{
//            User user;
//            Post post;
//        }
//        User_Post user_post = new User_Post();
//        user_post.user = UserUtil.getCurrentUser();
//        user_post.post = post;
//        String reply = HTTPPostWithObject(user_post,
//                "http://"+DNS+"/CopyCatServer/UserUtil/Like");

        UserUtil.getCurrentUser().setLikes(UserUtil.getCurrentUser().getLikes() + 1);
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                userSync();
                ServerUtil.HTTPPostWithObject(post,
                        "/CopyCatServer/PostUtil/LikePost");
                return "OK";
            }
        }.execute();

        return true;
    }

    public static boolean userUnLike(final Post post) {
        UserUtil.getCurrentUser().setLikes(UserUtil.getCurrentUser().getLikes() - 1);
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... params) {
                userSync();
                ServerUtil.HTTPPostWithObject(post,
                        "/CopyCatServer/PostUtil/UnlikePost");
                return "OK";
            }
        }.execute();

        return true;
    }


}
