package com.copycat.controller;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.copycat.util.db.DatabaseHelper;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageButton mTimelineButton;
    private ImageButton mGalleryButton;
    private ImageButton mSettingsButton;
    private Button mCameraButton;
//    private Button mLoginButton;

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

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE_NAME, new String[]{DatabaseHelper.CATEGORY_URI}, null, null, null, null, null, null);

        if(!cursor.moveToFirst()) {
            CoreUtil.addSetting("save","no",this);

            Bitmap banner_people = CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.banner0, 200, 70);
            Bitmap banner_city = CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.banner1, 200, 70);
            Bitmap banner_nature = CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.banner2, 200, 70);
            Bitmap banner_lifestyle = CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.banner3, 200, 70);

            Category People = new Category("People", banner_people, null);
            Category City = new Category("City", banner_city, null);
            Category Nature = new Category("Nature", banner_nature, null);
            Category Lifestyle = new Category("Lifestyle", banner_lifestyle, null);
            Category UserCategory = new Category("UserCategory", banner_lifestyle, null);

            UserCategory = CoreUtil.addCategory(UserCategory, this);
            People = CoreUtil.addCategory(People, this);
            City = CoreUtil.addCategory(City, this);
            Nature = CoreUtil.addCategory(Nature, this);
            Lifestyle = CoreUtil.addCategory(Lifestyle, this);

            List<Photo> photoList = new ArrayList<>();
            Photo p0_1 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p0_1,300,300), "p0-1", null, this);
            photoList.add(p0_1);
//            Photo p0_2 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p0_2,300,300), "p0-2", null, this);
//            photoList.add(p0_2);
//            Photo p0_3 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p0_3,300,300), "p0-3", null, this);
//            photoList.add(p0_3);
            CoreUtil.addPhotoListToCategory(photoList, People.getCategoryUri(), this);

            photoList = new ArrayList<>();
            Photo p1_1 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p1_1,300,300), "p1-1", null, this);
            photoList.add(p1_1);
//            Photo p1_2 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p1_2,300,300), "p1-2", null, this);
//            photoList.add(p1_2);
//            Photo p1_3 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p1_3,300,300), "p1-3", null, this);
//            photoList.add(p1_3);
            CoreUtil.addPhotoListToCategory(photoList, City.getCategoryUri(), this);

            photoList = new ArrayList<>();
            Photo p2_1 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p2_1,300,300), "p2-1", null, this);
            photoList.add(p2_1);
//            Photo p2_2 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p2_2,300,300), "p2-2", null, this);
//            photoList.add(p2_2);
//            Photo p2_3 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p2_0,300,300), "p2-3", null, this);
//            photoList.add(p2_3);
            CoreUtil.addPhotoListToCategory(photoList, Nature.getCategoryUri(), this);

            photoList = new ArrayList<>();
            Photo p3_0 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p3_0,300,300), "p3-1", null, this);
            photoList.add(p3_0);
//            Photo p3_2 = CoreUtil.storePhotoLocally(CoreUtil.decodeSampledBitmapFromResource(this.getResources(), R.drawable.p3_2,300,300), "p3-2", null, this);
//            photoList.add(p3_2);
            CoreUtil.addPhotoListToCategory(photoList, Lifestyle.getCategoryUri(), this);


            photoList = new ArrayList<>();
            photoList.add(p1_1);
//            photoList.add(p0_3);
//            photoList.add(p1_3);
            CoreUtil.addPhotoListToCategory(photoList, UserCategory.getCategoryUri(), this);
        }

        db.close();

        mTimelineButton = (ImageButton)findViewById(R.id.welcome_InspireButton);
        mGalleryButton = (ImageButton)findViewById(R.id.welcome_galleryButton);
        mSettingsButton = (ImageButton)findViewById(R.id.welcome_SettingsButton);

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
                        new Intent(WelcomeActivity.this, UserProfileActivity.class);
                startActivity(showSettings); // start the Activity
            }
        });

//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent showLogin =
//                        new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(showLogin); // start the Activity
//            }
//        });
    }
}
