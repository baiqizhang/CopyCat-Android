package com.copycat.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.copycat.model.Category;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baiqizhang on 11/18/15.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>  {

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //public TextView tvtinfo_text;
        public ImageView image;
        //Profile image
        public CircleImageView profileImageView;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            image = (ImageView)itemLayoutView.findViewById(R.id.imageView);
            profileImageView= (CircleImageView)itemLayoutView.findViewById(R.id.userImageView);
            
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //do sth
        }
    }

    public Category[] categories;
    public RecyclerView.OnItemTouchListener mItemClickListener;

    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public TimelineAdapter(Category[] categories) {
        this.categories = categories;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TimelineAdapter(Category[] categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View rootView = LayoutInflater.from(context).inflate(R.layout.listitem_timeline, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        ViewHolder holder = new ViewHolder(rootView);

//        Drawable color = new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark));
//        Drawable image = getResources().getDrawable(R.drawable.sample_6);
//
//        LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
//        imageView.setImageDrawable(ld);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get data from your itemsData at this position
        holder.image.setImageBitmap(
                CoreUtil.decodeSampledBitmapFromResource(context.getResources(), R.drawable.img1_1, 30, 100));

    }

    @Override
    public int getItemCount() {
        return categories.length;
    }



    public void setmItemClickListener(final RecyclerView.OnItemTouchListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

}
