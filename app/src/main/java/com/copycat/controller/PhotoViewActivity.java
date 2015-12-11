package com.copycat.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.copycat.util.CapturePhotoUtil;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoViewActivity extends AppCompatActivity {
    Bitmap bitmap;
    ImageView imageView;

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
        final String uri = intent.getStringExtra("uri");

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
                Toast.makeText(PhotoViewActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
