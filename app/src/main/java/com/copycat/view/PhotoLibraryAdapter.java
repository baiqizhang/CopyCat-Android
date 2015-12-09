package com.copycat.view;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

/**
 * Created by frankluo on 11/6/15.
 */
public class PhotoLibraryAdapter extends BaseAdapter {
    private Context mContext;

    public PhotoLibraryAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.img1_1, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        SquareImageView mImageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            mImageView = new SquareImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(0, 0, 0, 0);
        } else {
            mImageView = (SquareImageView) convertView;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(),mThumbIds[position], options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        mImageView.setImageBitmap(
                CoreUtil.decodeSampledBitmapFromResource(mContext.getResources(),R.drawable.img1_1,
                        imageWidth/10, imageHeight/10));
//        mImageView.setImageResource(mThumbIds[position]);
        return mImageView;
    }





}