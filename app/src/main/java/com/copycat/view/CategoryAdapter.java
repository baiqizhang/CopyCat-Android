package com.copycat.view;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.copycat.controller.CategoryActivity;
import com.copycat.controller.GalleryActivity;
import com.copycat.model.Category;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baiqizhang on 11/18/15.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {
    private List<Category> categories;
    private Context context;
    private RecyclerView.OnItemTouchListener mItemClickListener;

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView categoryName;
        public ImageView banner;
        public TextView photoCount;
        public int position;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            banner = (ImageView)itemLayoutView.findViewById(R.id.timeline_imageView);
            categoryName= (TextView)itemLayoutView.findViewById(R.id.titleTextView);
            photoCount= (TextView)itemLayoutView.findViewById(R.id.count_textview);

            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent showGallery =
                    new Intent(context, GalleryActivity.class);
            showGallery.putExtra("cUri", categories.get(position).getCategoryUri());
            Toast.makeText(v.getContext(),categories.get(position).getCategoryUri(), Toast.LENGTH_SHORT).show();
            context.startActivity(showGallery); // start the Activity
            Toast.makeText(context, "pos:" + position,
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View rootView = LayoutInflater.from(context).inflate(R.layout.listitem_category, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        ViewHolder holder = new ViewHolder(rootView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get data from your itemsData at this position
        //holder.banner.setImageBitmap(
        //        CoreUtil.decodeSampledBitmapFromResource(context.getResources(), R.drawable.banner, 30, 100));
        holder.banner.setImageBitmap(categories.get(position).getBanner());
        holder.categoryName.setText(categories.get(position).getCategoryName());
        holder.photoCount.setText("- "+ "" +" -");
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setmItemClickListener(final RecyclerView.OnItemTouchListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

}
