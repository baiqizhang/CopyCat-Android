package com.copycat.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.copycat.controller.GalleryActivity;
import com.copycat.controller.PhotoViewActivity;
import com.copycat.model.Category;
import com.copycat.model.Photo;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

import java.util.List;

/**
 * Created by baiqizhang on 11/18/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>  {
    private static final int RESULT_LOAD_IMAGE = 666;
    public List<Photo> photos;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GalleryAdapter(List<Photo> photos, Context context) {
        this.photos = photos;
        this.context = context;
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
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                context.startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
            } else {
                Toast.makeText(context, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(context, PhotoViewActivity.class);
                context.startActivity(loginIntent);
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
        ImageView mImageView = holder.imageView;
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setPadding(2, 2, 2, 2);

        Photo photo = photos.get(position);
        String uri = photo.getPhotoUrl();
        Log.d("uri", uri.substring(0, 4));
        if (uri.substring(0,4).equals("draw")){
            int id = Integer.valueOf(uri.substring(7));
            holder.imageView.setImageBitmap(
                    CoreUtil.decodeSampledBitmapFromResource(context.getResources(),id, 30, 30));

        }

        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}