package com.example.baiqizhang.copycat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private Button mTimelineButton;
    private Button mGalleryButton;
    private Button mSettingsButton;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mTimelineButton = (Button)findViewById(R.id.welcome_InspireButton);
        mGalleryButton = (Button)findViewById(R.id.welcome_galleryButton);
        mSettingsButton = (Button)findViewById(R.id.welcome_SettingsButton);
        mLoginButton = (Button)findViewById(R.id.welcome_LoginButton);

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
    }
}
