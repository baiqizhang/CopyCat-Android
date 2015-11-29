package com.copycat.controller;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.copycat.model.User;
import com.example.baiqizhang.copycat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Hide actionbar and status bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Profile image
        CircleImageView imageView = (CircleImageView)findViewById(R.id.userimageview);

        Drawable color = new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark));
        Drawable image = getResources().getDrawable(R.drawable.sample_6);

        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        imageView.setImageDrawable(ld);

        //Gridview
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(UserProfileActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(UserProfileActivity.this, PhotoViewActivity.class);
                startActivity(loginIntent);

            }
        });

        ImageButton mBackButton = (ImageButton)findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }

}
