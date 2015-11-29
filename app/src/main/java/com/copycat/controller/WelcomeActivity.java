package com.copycat.controller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.copycat.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private Button mTimelineButton;
    private Button mGalleryButton;
    private Button mSettingsButton;
    private Button mLoginButton;
    private Button mCameraButton;

    CameraActivity ca = new CameraActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        //Hide actionbar and status bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mTimelineButton = (Button)findViewById(R.id.welcome_InspireButton);
        mGalleryButton = (Button)findViewById(R.id.welcome_galleryButton);
        mSettingsButton = (Button)findViewById(R.id.welcome_SettingsButton);
        mLoginButton = (Button)findViewById(R.id.welcome_LoginButton);
        mCameraButton = (Button)findViewById(R.id.welcome_cameraButton);

        mTimelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showList =
                        new Intent(WelcomeActivity.this, TimelineActivity.class);
                startActivity(showList); // start the Activity
            }
        });
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showList =
                        new Intent(WelcomeActivity.this, CategoryActivity.class);
                startActivity(showList); // start the Activity
            }
        });
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSettings =
                        new Intent(WelcomeActivity.this, SettingsActivity.class);
                startActivity(showSettings); // start the Activity
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showLogin =
                        new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(showLogin); // start the Activity
            }
        });
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                Intent showCamera =
                        new Intent(WelcomeActivity.this, CameraActivity.class);
                startActivity(showCamera);
            }
            });
        }

    }
