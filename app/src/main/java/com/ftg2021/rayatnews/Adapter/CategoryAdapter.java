package com.ftg2021.rayatnews.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsCategoryModel;
import com.ftg2021.rayatnews.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryDataHolder> {
    private List<NewsCategoryModel> categoryData;
    private Context context;


    public CategoryAdapter(Context c, List<NewsCategoryModel> newsList) {
        this.context = c;
        this.categoryData = newsList;
        Log.i("data_setting",""+categoryData.size());
    }

    @NonNull
    @Override
    public CategoryDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_single_item,parent,false);
        CategoryDataHolder siteListHolder = new CategoryDataHolder(view);
        return  siteListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDataHolder holder, int position) {
        final NewsCategoryModel myListData = categoryData.get(position);
        holder.category_name.setText(categoryData.get(position).getCategory_name());
        setFadeAnimation(holder.category_name);
    }


    @Override
    public int getItemCount()
    {
        return categoryData.size();
    }

    public static class CategoryDataHolder extends  RecyclerView.ViewHolder {
        public TextView category_name;

        public CategoryDataHolder(@NonNull View itemView) {
            super(itemView);
            category_name  = itemView.findViewById(R.id.categoryNameInMenu);
        }
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }
}