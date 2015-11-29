package com.copycat.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.copycat.model.Category;
import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //setup toolbar and hide status bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set title text style
        TextView mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mTitleTextView.setLetterSpacing(0.13f);

        //setup listview
        ListView mListView = (ListView) findViewById(R.id.listView);
        mListView.setDivider(null);
        mListView.setDividerHeight(0);

        // create the grid item mapping
        String[] from = new String[] {"title"};
        int[] to = new int[] {R.id.listitem_category_title};

        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", "Selfie"); fillMaps.add(map); map = new HashMap<String, String>();
        map.put("title", "Nature"); fillMaps.add(map); map = new HashMap<String, String>();
        map.put("title", "People"); fillMaps.add(map); map = new HashMap<String, String>();
        map.put("title", "Food"); fillMaps.add(map); map = new HashMap<String, String>();
        map.put("title", "Lifestyle"); fillMaps.add(map); map = new HashMap<String, String>();


        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.listitem_category, from, to);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showGallery =
                        new Intent(CategoryActivity.this, GalleryActivity.class);
                showGallery.putExtra("index", position);
                startActivity(showGallery); // start the Activity
            }
        });

        ImageButton mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
