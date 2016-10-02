package com.example.pixabaysearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gemtek on 9/29/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private Context mContext;
    private List<ItemContent> mImageList;

    public RecyclerAdapter(Context context, List<ItemContent> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        RecyclerViewHolder imageViewHolder = new RecyclerViewHolder(layoutView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.mImageView.setImageDrawable(mImageList.get(position).imgSource);
        holder.mTextView.setText(mImageList.get(position).imgTitle);
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mImageList.get(position).mImageUrl));
//                mContext.startActivity(browserIntent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView mImageView;
    public TextView mTextView;
//    public CardView mCardView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        mImageView = (ImageView) itemView.findViewById(R.id.image_res);
        mTextView = (TextView) itemView.findViewById(R.id.image_description);
//        mCardView = (CardView) itemView.findViewById(R.id.image_card);
    }
}

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





