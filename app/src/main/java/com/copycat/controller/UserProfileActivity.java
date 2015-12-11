package com.copycat.controller;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.copycat.util.remote.UserUtil;
import com.copycat.view.GalleryAdapter;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.List;

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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtil.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(UserProfileActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });

        Drawable color = new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark));
        Drawable image = getResources().getDrawable(R.drawable.sample_6);

        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        imageView.setImageDrawable(ld);

//        //Content adapter
//        List<Photo> placeholders = new ArrayList<Photo>();
//        placeholders.add(new Photo("","draw://" + R.drawable.sample_0));
//        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
//        placeholders.add(new Photo("","draw://" + R.drawable.sample_2));
//        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
//        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));

        List<Photo> placeholders = CoreUtil.getPhotoListWithCategory(CoreUtil.getCategoryListFromDB(this).get(0).getCategoryUri(),this);
        GalleryAdapter galleryAdapter = new GalleryAdapter(placeholders,this,"User");

        //RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gridview);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(galleryAdapter);

        //back
        ImageButton mBackButton = (ImageButton)findViewById(R.id.backButton);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }

}
