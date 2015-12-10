package com.copycat.controller;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photopreview);

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

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        if (uri.substring(0,4).equals("draw")){
            int id = Integer.valueOf(uri.substring(7));
            imageView.setImageBitmap(
                    CoreUtil.decodeSampledBitmapFromResource(getResources(), id, 100, 100));
        } else if (uri.substring(0,4).equals("file")){

        }


        //Buttons
        ImageButton mYesButton = (ImageButton)findViewById(R.id.yesPreviewButton);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoPreviewActivity.this, CameraActivity.class);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });

        ImageButton mNoButton = (ImageButton)findViewById(R.id.noPreviewButton);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
