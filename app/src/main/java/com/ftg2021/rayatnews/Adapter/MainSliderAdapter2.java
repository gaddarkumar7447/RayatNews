package com.ftg2021.rayatnews.Adapter;

import android.content.Context;

import com.ftg2021.rayatnews.Model.Banner;
import com.ftg2021.rayatnews.Model.Banner2;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter2 extends SliderAdapter {

    private List<Banner2> list;
    private Context c;

    public MainSliderAdapter2(Context c, List<Banner2> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        viewHolder.bindImageSlide(list.get(position).getPath());

    }
}

