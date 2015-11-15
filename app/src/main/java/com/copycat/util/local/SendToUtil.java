package com.copycat.util.local;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.copycat.model.Photo;

/**
 * Created by baiqizhang on 11/14/15.
 */
public class SendToUtil {
    public static void sendPhotoToApp(Photo photo,Context context){
        //sharing implementation here

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");

        //TODO:change the uri
        Uri uri = null;//Uri.fromFile(Photo.uri);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
