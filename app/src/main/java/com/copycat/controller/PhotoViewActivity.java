package com.copycat.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.copycat.model.Photo;
import com.copycat.model.Post;
import com.copycat.model.User;
import com.copycat.util.CapturePhotoUtil;
import com.copycat.util.CoreUtil;
import com.copycat.util.remote.PostUtil;
import com.copycat.util.remote.UserUtil;
import com.example.baiqizhang.copycat.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoViewActivity extends AppCompatActivity {
    Bitmap bitmap;
    String uri;
    ImageView imageView;

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photoview);

        //Hide actionbar and status bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //get intent extra
        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");

        imageView = (ImageView)findViewById(R.id.imageView);
        if (uri.substring(0,4).equals("draw")){
            int id = Integer.valueOf(uri.substring(7));
            bitmap = CoreUtil.decodeSampledBitmapFromResource(getResources(), id, 600, 600);
        } else if (uri.substring(0, 4).equals("file")) {
            bitmap = BitmapFactory.decodeFile(uri.substring(4));
        }
        imageView.setImageBitmap(bitmap);

        ImageButton mShareButton = (ImageButton)findViewById(R.id.share);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] byteImage_photo = baos.toByteArray();
                String encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
                Post post = new Post(encodedImage, UserUtil.getCurrentUser(), 0, "Mountain View");

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    NewPostTask task = new NewPostTask();
                    task.post = post;
                    task.execute();
                } else {
                    Log.e("error", "No network connection available.");
                }
                Toast.makeText(PhotoViewActivity.this, "shared", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton mStoreButton = (ImageButton)findViewById(R.id.store);
        mStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapturePhotoUtil.insertImage(PhotoViewActivity.this.getContentResolver(), bitmap, bitmap.toString(), "by copycat");
                Toast.makeText(PhotoViewActivity.this,"Saved to system library",Toast.LENGTH_SHORT).show();;
            }
        });

//        ImageButton mSendToButton = (ImageButton)findViewById(R.id.sendto);
//        mSendToButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri bmpUri = getLocalBitmapUri(imageView);
//                if (bmpUri != null) {
//                    // Construct a ShareIntent with link to image
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    shareIntent.setType("image/*");
//                    // Launch sharing dialog for image
//                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
//                } else {
//                    // ...sharing failed, handle error
//                }
//            }
//        });


        ImageButton mRotateButton = (ImageButton)findViewById(R.id.flip);
        mRotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imageView.setImageBitmap(bitmap);
            }
        });

        ImageButton mDeleteButton = (ImageButton)findViewById(R.id.delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Photo> temp = new ArrayList<Photo>();
                temp.add(new Photo("",uri));
                CoreUtil.removePhotoList(temp,context);
                Toast.makeText(PhotoViewActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private class NewPostTask extends AsyncTask<String, Void, String> {
        public Post post;
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            PostUtil.uploadUserPost(post);
            return "200";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("result:", post.toString());
        }
    }


//    // Returns the URI path to the Bitmap displayed in specified ImageView
//    public Uri getLocalBitmapUri(ImageView imageView) {
//        // Extract Bitmap from ImageView drawable
//        Drawable drawable = imageView.getDrawable();
//        Bitmap bmp = null;
//        if (drawable instanceof BitmapDrawable){
//            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            return null;
//        }
//        // Store image to default external storage directory
//        Uri bmpUri = null;
//        try {
//            File file =  new File(Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
//            file.getParentFile().mkdirs();
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//            bmpUri = Uri.fromFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }
}
