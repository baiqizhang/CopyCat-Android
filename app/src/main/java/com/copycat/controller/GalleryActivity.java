package com.copycat.controller;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.copycat.model.Photo;
import com.copycat.view.GalleryAdapter;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 666;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //setup toolbar and hide status bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set title text style
        TextView mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mTitleTextView.setLetterSpacing(0.13f);

        //get intent extra
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);
        Toast.makeText(GalleryActivity.this, "" + index, Toast.LENGTH_SHORT).show();

        //Content adapter
        List<Photo> placeholders = new ArrayList<Photo>();
        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
        placeholders.add(new Photo("","draw://" + R.drawable.sample_2));
        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
        placeholders.add(new Photo("","draw://" + R.drawable.img1_1));
        placeholders.add(new Photo("","draw://" + R.drawable.sample_6));

        GalleryAdapter galleryAdapter = new GalleryAdapter(placeholders,this);

        //RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gridview);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(galleryAdapter);

        ImageButton mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case RESULT_LOAD_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();



//                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

                }
        }
    }
}
