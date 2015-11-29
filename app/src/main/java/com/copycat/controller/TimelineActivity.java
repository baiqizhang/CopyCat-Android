package com.copycat.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.baiqizhang.copycat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //setup toolbar and hide status bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set title text style
        TextView mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mTitleTextView.setLetterSpacing(0.13f);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // create the grid item mapping
        String[] from = new String[] {};
        int[] to = new int[] { };

        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 10; i++){
            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("rowid", "" + i);
//            map.put("col_1", "col_1_item_" + i);
//            map.put("col_2", "col_2_item_" + i);
//            map.put("col_3", "col_3_item_" + i);
            fillMaps.add(map);
        }

        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.listitem_timeline, from, to);

        ListView mListView = (ListView) findViewById(R.id.timelineListView);
        mListView.setAdapter(adapter);

        ImageButton mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
