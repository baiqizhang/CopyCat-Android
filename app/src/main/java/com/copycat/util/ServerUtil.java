package com.copycat.util;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by baiqizhang on 12/11/15.
 */
public class ServerUtil {
//    private static final String DNS = "ec2-52-90-164-203.compute-1.amazonaws.com";
    private static final String DNS = "172.29.92.23:8080";
    private static final Gson gson = new Gson();

    public static String HTTPPostWithObject(Object obj, String urlstr) {
        InputStream is = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        String contentAsString = null;
        //can catch a variety of wonderful things
        try {
            //constants
            URL url = new URL("http://"+DNS+urlstr);
            String message = gson.toJson(obj);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /* milliseconds */);
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
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //clean up
            os.flush();

            //do somehting with response
            is = conn.getInputStream();
            contentAsString = convertInputStreamToString(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //clean up
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (conn != null)
                    conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contentAsString;
    }

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
