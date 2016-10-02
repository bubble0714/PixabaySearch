package com.example.pixabaysearch;

import android.graphics.drawable.Drawable;

/**
 * Created by gemtek on 9/29/16.
 */

public class ItemObject {

    public Drawable mImageDrawable;
    public String mImageDescription;
    public String mImageUrl;

    public ItemObject(Drawable image, String desc, String url) {
        mImageDrawable = image;
        mImageDescription = desc;
        mImageUrl = url;
    }
}