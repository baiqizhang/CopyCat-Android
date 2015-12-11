package com.copycat.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.copycat.controller.PhotoPreviewActivity;
import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

import java.util.List;

/**
 * Created by baiqizhang on 11/18/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>  {
    private static final int RESULT_LOAD_IMAGE = 666;
    private List<Photo> photos;
    private Context context;
    private String source = "Gallery";

    // Provide a suitable constructor (depends on the kind of dataset)
    public GalleryAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }
    public GalleryAdapter(List<Photo> photos, Context context,String source) {
        this.photos = photos;
        this.context = context;
        this.source = source;
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public SquareImageView imageView;
        public int position;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imageView = (SquareImageView)itemLayoutView.findViewById(R.id.galleryImageView);

            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "pos:" + position,
                    Toast.LENGTH_SHORT).show();

            if (position == 0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ((Activity)context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            } else {
                Toast.makeText(context, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PhotoPreviewActivity.class);
                intent.putExtra("uri",photos.get(position).getPhotoUrl());
                context.startActivity(intent);

            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View rootView = LayoutInflater.from(context).inflate(R.layout.listitem_photo, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        ViewHolder holder = new ViewHolder(rootView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (source.equals("Gallery"))
            position--;
        ImageView mImageView = holder.imageView;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setPadding(1, 1, 1, 1);

        Photo photo = photos.get(position);
        String uri = photo.getPhotoUrl();
        Log.d("uri", uri.substring(0, 4));
        if (position == 0 && source.equals("Gallery")){
            holder.imageView.setImageBitmap(
                    CoreUtil.decodeSampledBitmapFromResource(context.getResources(),R.drawable.addnew, 100, 100));
            return;
        }

        if (uri.substring(0,4).equals("draw")){
            int id = Integer.valueOf(uri.substring(7));
            holder.imageView.setImageBitmap(
                    CoreUtil.decodeSampledBitmapFromResource(context.getResources(),id, 100, 100));
        } else if (uri.substring(0,4).equals("file")){
            Bitmap tempPhoto = BitmapFactory.decodeFile(uri.substring(4));
            holder.imageView.setImageBitmap(tempPhoto);
            return;
        }

        holder.position = position;
    }

    @Override
    public int getItemCount() {
        if (source.equals("Gallery"))
            return photos.size()+1;
        else
            return photos.size();
    }

}
