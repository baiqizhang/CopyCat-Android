package com.copycat.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.copycat.util.remote.UserUtil;
import com.copycat.view.GalleryAdapter;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    List<Photo> photos;

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.usernameTextView && !hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
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

        //Username
        final EditText editText = (EditText)findViewById(R.id.usernameTextView);
        editText.setText(UserUtil.getCurrentUser().getName());
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                UserUtil.getCurrentUser().setName(v.getText().toString());
                return true;
            }
        });
        MyFocusChangeListener ofcListener = new MyFocusChangeListener();
        editText.setOnFocusChangeListener(ofcListener);

        if (!editText.getText().toString().equals("Anonymous"))
            editText.clearFocus();

//        Drawable color = new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark));
//        Drawable image = getResources().getDrawable(R.drawable.sample_6);
//
//        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
//        imageView.setImageDrawable(ld);

//        //Content adapter
        photos = CoreUtil.getPhotoListWithCategory(CoreUtil.getCategoryListFromDB(this).get(0).getCategoryUri(),this);
        GalleryAdapter galleryAdapter = new GalleryAdapter(photos,this,"User");

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
                editText.onEditorAction(0);
                finish();
            }
        });
    }

}
