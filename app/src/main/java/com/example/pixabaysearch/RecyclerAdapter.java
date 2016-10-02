package com.example.pixabaysearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Milly on 9/29/16.
 */


class ItemContent {

    public Drawable imgSource;
    public String imgTitle;
    public String imgUrl;

    public ItemContent(Drawable image, String title, String url) {
        imgSource = image;
        imgTitle = title;
        imgUrl = url;
    }
}

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<ItemContent> mImageList;

    public RecyclerAdapter(Context context, List<ItemContent> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image_result);
            mTextView = (TextView) itemView.findViewById(R.id.image_size);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        RecyclerViewHolder imageViewHolder = new RecyclerViewHolder(layoutView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        Log.e("Adapter", "*** onBindViewHolder: " + mImageList.get(position).imgTitle);
        holder.mImageView.setImageDrawable(mImageList.get(position).imgSource);
        holder.mTextView.setText(mImageList.get(position).imgTitle);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

}







