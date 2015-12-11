
package com.copycat.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.os.Handler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import java.net.URL;
import java.util.List;


import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;


public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    CameraPreview preview = null;
    private SurfaceHolder cameraSurfaceHolder = null;
    private boolean previewing = false;
    RelativeLayout relativeLayout;
    private ImageButton captureButton = null;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    SeekBar barOpacity;
    ImageView overlay;
    Camera camera;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //Hide actionbar and status bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        preview = (CameraPreview) findViewById(R.id.cameraPreview);

        //get intent extra
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        overlay = (ImageView) findViewById(R.id.imageView1);

        if(uri!=null) {
            if (uri.substring(0, 4).equals("draw")) {
                int id = Integer.valueOf(uri.substring(7));
                overlay.setImageBitmap(
                        CoreUtil.decodeSampledBitmapFromResource(getResources(), id, 100, 100));
            } else if (uri.substring(0, 4).equals("file")) {
                Bitmap tempPhoto = BitmapFactory.decodeFile(uri.substring(4));
                overlay.setImageBitmap(tempPhoto);
            }
        }



        barOpacity = (SeekBar) findViewById(R.id.opacity);
        int alpha = barOpacity.getProgress();
        overlay.setAlpha(alpha);
        barOpacity.setOnSeekBarChangeListener(barOpacityOnSeekBarChangeListener);

        captureButton = (ImageButton) findViewById(R.id.captureButton);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        preview.getCamera().takePicture(null, null, mPicture);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                preview.getCamera().stopPreview();
                                preview.getCamera().startPreview();
                            }
                        }, 2000);

                    }
                }
        );

    }

    OnSeekBarChangeListener barOpacityOnSeekBarChangeListener =
            new OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    int alpha = barOpacity.getProgress();

                    overlay.setAlpha(alpha);   //deprecated
                    //image.setImageAlpha(alpha); //for API Level 16+
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

            };

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height) {
        onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            // Call stopPreview() to stop updating the preview surface.
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                System.out.print("Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                List<Photo> tempPList = new ArrayList<Photo>();
                Photo tempPhoto = new Photo(pictureFile.getName(),"file" + pictureFile.getAbsolutePath());
                tempPList.add(tempPhoto);
                CoreUtil.addPhotoListToCategory(tempPList, CoreUtil.getCategoryListFromDB(getContext()).get(0).getCategoryUri(), getContext());
            } catch (FileNotFoundException e) {
                System.out.print("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.print("Error accessing file: " + e.getMessage());
            }
        }
    };


    private Context getContext() {
        return (Context) this;
    }
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}