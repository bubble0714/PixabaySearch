package com.example.pixabaysearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gemtek on 9/29/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private Context mContext;
    private List<ItemObject> mImageList;
    private boolean mIsGrid = true;

    public RecyclerAdapter(Context context, List<ItemObject> imageList, boolean isGrid) {
        mContext = context;
        mImageList = imageList;
        mIsGrid = isGrid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        if (!mIsGrid)
            layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, null);
        RecyclerViewHolder imageViewHolder = new RecyclerViewHolder(layoutView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.mImageView.setImageDrawable(mImageList.get(position).mImageDrawable);
        holder.mTextView.setText(mImageList.get(position).mImageDescription);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mImageList.get(position).mImageUrl));
//                mContext.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}





