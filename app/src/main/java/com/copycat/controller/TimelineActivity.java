package com.copycat.controller;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.copycat.model.Category;
import com.copycat.model.Post;
import com.copycat.view.TimelineAdapter;
import com.example.baiqizhang.copycat.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    UltimateRecyclerView mUltimateRecyclerView;
    TextView mTitleTextView;
    RecyclerView mRecyclerView;
    ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //setup toolbar and hide status bar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set title text style
        mTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mTitleTextView.setLetterSpacing(0.13f);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Post> placeholders = new ArrayList<Post>();
        placeholders.add(new Post());
        placeholders.add(new Post());
        placeholders.add(new Post());
        placeholders.add(new Post());

        final TimelineAdapter newAdapter = new TimelineAdapter(placeholders,this);
        mUltimateRecyclerView = (UltimateRecyclerView)findViewById(R.id.ultimate_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mUltimateRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mUltimateRecyclerView.setLayoutManager(mLayoutManager);

        mUltimateRecyclerView.setAdapter(newAdapter);

        newAdapter.setCustomLoadMoreView(LayoutInflater.from(this)
                .inflate(R.layout.loadmore_progressbar, null));

        mBackButton = (ImageButton) findViewById(R.id.toolbar_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUltimateRecyclerView.enableLoadmore();
        mUltimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        newAdapter.insert(new Post(), newAdapter.getAdapterItemCount());
                        mUltimateRecyclerView.disableLoadmore();
                        newAdapter.remove(newAdapter.getAdapterItemCount()-1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mUltimateRecyclerView.enableLoadmore();
                            }
                        }, 2000);
                    }
                }, 1000);
            }
        });

//        mUltimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.listitem_category,
//                mUltimateRecyclerView.mRecyclerView, false));
//
//        mUltimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
//            @Override
//            public void onParallaxScroll(float percentage, float offset, View parallax) {
//                Drawable c = toolbar.getBackground();
//                c.setAlpha(Math.round(127 + percentage * 128));
//                toolbar.setBackgroundDrawable(c);
//            }
//        });


        mUltimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(TimelineActivity.this, "onRefresh",
                        Toast.LENGTH_SHORT).show();
                // Refresh items
                refreshItems();
            }
        });

    }

    void refreshItems() {
        // Load items
        // ...
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mUltimateRecyclerView.mSwipeRefreshLayout.setRefreshing(false);
    }



}
