package com.copycat.view;

/**
 * Created by baiqizhang on 12/8/15.
 */
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.copycat.model.Category;
import com.copycat.model.Post;
import com.copycat.model.User;
import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class TimelineAdapter extends UltimateViewAdapter<TimelineAdapter.SimpleAdapterViewHolder> {
    public List<Post> posts;
    private Context context;


    public TimelineAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, final int position) {
        Log.d("bind","pos:" + position);
        if (position < getItemCount()
                && (customHeaderView != null ? position <= posts.size() : position < posts.size())
                && (customHeaderView != null ? position > 0 : true)) {
            final int pos = position;
//            holder.imageView.getViewTreeObserver()
//                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                        // Wait until layout to call Picasso
//                        @Override
//                        public void onGlobalLayout() {
//                            // Ensure we call this only once
//                            holder.imageView.getViewTreeObserver()
//                                    .removeOnGlobalLayoutListener(this);
//
//
//                            Picasso.with(context)
//                                    .load(posts.get(pos).getPhotoURI())
//                                    .resize(holder.imageView.getWidth(),0)
//                                    .into(holder.imageView,new com.squareup.picasso.Callback() {
//                                        @Override
//                                        public void onSuccess() {
//                                            if (holder.loadingTextView != null) {
//                                                holder.loadingTextView.setVisibility(View.GONE);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onError() {
//                                            holder.imageView.setImageBitmap(null);
//                                        }
//                                    });
//                        }
//                    });

            Picasso.with(context)
                    .load(posts.get(pos).getPhotoURI())
                    .resize(600, 0)
                    .into(holder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (holder.loadingTextView != null) {
                                holder.loadingTextView.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            holder.imageView.setImageBitmap(null);
                        }
                    });

            holder.usernameTextView.setText(posts.get(position).getUser().getName());
            holder.geoTagTextView.setText(posts.get(position).getGeoTag());
            holder.likeCountTextView.setText(String.valueOf(posts.get(position).getLikeCount()));
            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (posts.get(position).isLiked()) {
                        posts.get(position).decreaseLikeCount();
                        posts.get(position).flipLiked();
                    } else {
                        posts.get(position).incrementLikeCount();
                        posts.get(position).flipLiked();
                    }
                    holder.likeCountTextView.setText(String.valueOf(posts.get(position).getLikeCount()));
                }
            });
            holder.pinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

//            ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
            if (mDragStartListener != null) {
//                ((ViewHolder) holder).imageViewSample.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                            mDragStartListener.onStartDrag(holder);
//                        }
//                        return false;
//                    }
//                });

                ((SimpleAdapterViewHolder) holder).imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }

    }

    @Override
    public int getAdapterItemCount() {
        return posts.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_timeline, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }


    public void insert(Post post, int position) {
        insert(posts, post, position);
    }
//
    public void remove(int position) {
        remove(posts, position);
    }

    public void clear() {
        clear(posts);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


//    public void swapPositions(int from, int to) {
//        swapPositions(stringList, from, to);
//    }


    @Override
    public long generateHeaderId(int position) {
//        if (getItem(position).length() > 0)
//            return getItem(position).charAt(0);
//        else
            return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_category, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

//        holder.image.setImageBitmap(
//                CoreUtil.decodeSampledBitmapFromResource(context.getResources(), R.drawable.img1_1, 30, 100));

//        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
//        textView.setText(String.valueOf(getItem(position).charAt(0)));
////        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
//        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);
//
//        SecureRandom imgGen = new SecureRandom();
//        switch (imgGen.nextInt(3)) {
//            case 0:
//                imageView.setImageResource(R.drawable.test_back1);
//                break;
//            case 1:
//                imageView.setImageResource(R.drawable.test_back2);
//                break;
//            case 2:
//                imageView.setImageResource(R.drawable.test_back);
//                break;
//        }

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
//        swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        super.onItemDismiss(position);
    }

    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }


    public class SimpleAdapterViewHolder extends UltimateRecyclerviewViewHolder {

        //public TextView tvtinfo_text;
        public ImageView imageView;
        //user name
        public TextView usernameTextView;
        //Profile image
        public CircleImageView profileImageView;
        //likes
        public TextView likeCountTextView;
        public ImageButton likeButton;
        public ImageButton pinButton;
        //geotag
        public TextView geoTagTextView;
        //loading
        public TextView loadingTextView;

        boolean liked = false;

        public  SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
            if (isItem) {
                imageView = (ImageView)itemView.findViewById(R.id.imageView);
                profileImageView= (CircleImageView)itemView.findViewById(R.id.userImageView);

                likeCountTextView = (TextView)itemView.findViewById(R.id.likeTextView);
                likeButton = (ImageButton)itemView.findViewById(R.id.likeButton);
                pinButton = (ImageButton)itemView.findViewById(R.id.pinButton);

                geoTagTextView = (TextView)itemView.findViewById(R.id.geoTagTextView);
                loadingTextView = (TextView)itemView.findViewById(R.id.loadingTextView);
                usernameTextView = (TextView)itemView.findViewById(R.id.usernameTextView);
            }

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public Post getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < posts.size())
            return posts.get(position);
        else return null;
    }

}

