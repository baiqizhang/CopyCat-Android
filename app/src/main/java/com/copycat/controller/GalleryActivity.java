package com.copycat.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.copycat.view.CategoryAdapter;
import com.copycat.view.GalleryAdapter;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 666;
    private static String CATEGORY_URI;
    private RecyclerView mRecyclerView;
    private GalleryAdapter galleryAdapter;
    private List<Photo> photos;

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

        //Get PhotoList by Category uri(absolutePath of it's banner named by category's name)
        Intent intent = getIntent();
        CATEGORY_URI = intent.getStringExtra("cUri");
        String title = intent.getStringExtra("title");
        mTitleTextView.setText(title);
        photos = CoreUtil.getPhotoListWithCategory(CATEGORY_URI,this);
        galleryAdapter = new GalleryAdapter(photos,this);


        //RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.gridview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(galleryAdapter);

        //Buttons
        ImageButton mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add button
        ImageButton mDelButton = (ImageButton) findViewById(R.id.delCategory);
        mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoreUtil.removeCategory(new Category("",null,CATEGORY_URI),GalleryActivity.this);
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
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Photo tempPhoto = CoreUtil.storePhotoLocally(yourSelectedImage, Long.toString(new Date().getTime()),filePath, this);
                    List<Photo> tempPList = new ArrayList<Photo>();
                    tempPList.add(tempPhoto);
                    boolean result = CoreUtil.addPhotoListToCategory(tempPList, CATEGORY_URI,this);
                    photos.add(tempPhoto);
                    galleryAdapter.notifyDataSetChanged();
                }
        }
    }
}
