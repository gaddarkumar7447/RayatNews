package com.ftg2021.rayatnews;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ss.com.bannerslider.ImageLoadingService;

public class GlideImageLoadingService implements ImageLoadingService {
    public Context context;

    public GlideImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).fitCenter().into(imageView);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context).load(resource).fitCenter().into(imageView);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context).load(url).fitCenter().placeholder(placeHolder).error(errorDrawable).into(imageView);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}

