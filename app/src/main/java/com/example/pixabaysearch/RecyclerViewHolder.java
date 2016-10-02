package com.example.pixabaysearch;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gemtek on 9/29/16.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImageView;
    public TextView mTextView;
    public CardView mCardView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        mImageView = (ImageView) itemView.findViewById(R.id.image_res);
        mTextView = (TextView) itemView.findViewById(R.id.image_description);
        mCardView = (CardView) itemView.findViewById(R.id.image_card);
    }
}