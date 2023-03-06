package com.ftg2021.rayatnews.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ftg2021.rayatnews.GallerySlider;
import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsCategoryModel;
import com.ftg2021.rayatnews.Model.NewsGalleryModel;
import com.ftg2021.rayatnews.R;
import com.ftg2021.rayatnews.fragments.Category;
import com.ftg2021.rayatnews.fragments.Discover;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;


public class GalleryFragmentAdapter extends RecyclerView.Adapter<GalleryFragmentAdapter.GalleryDataHolder>
{
    private List<NewsGalleryModel> categoryData;
    private Context context;
    private Discover fragment;

    public GalleryFragmentAdapter(Context c, List<NewsGalleryModel> newsList)
    {
        this.context=c;
        this.categoryData = newsList;
        Log.i("data_setting",""+categoryData.size());
    }

    @NonNull
    @Override
    public GalleryDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_gallery_list_single_item_in_fragment,parent,false);
        GalleryDataHolder siteListHolder = new GalleryDataHolder(view);
        return  siteListHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull GalleryDataHolder holder, int position)
    {

        holder.category_name.setText(categoryData.get(position).getGalleryName());

        setFadeAnimation(holder.category_name);

//        Drawable drawable = context.getResources().getDrawable(R.drawable.cat_news);

//        holder.layout.setBackground(Drawable.createFromPath(categoryData.get(position).getGalleryImage()));

        try {
            Log.e("image",categoryData.get(position).getGalleryImage());

            Glide.with(context)
                    .load(categoryData.get(position).getGalleryImage())
                    .fitCenter()
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                            holder.layout.setBackground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

                        }
                    });
        } catch (Exception e) {
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, ""+categoryData.get(position).getGalleryName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, GallerySlider.class);
                intent.putExtra("gi1",categoryData.get(position).getGi1());
                intent.putExtra("gi2",categoryData.get(position).getGi2());
                intent.putExtra("gi3",categoryData.get(position).getGi3());
                intent.putExtra("gi4",categoryData.get(position).getGi4());
                intent.putExtra("gi5",categoryData.get(position).getGi5());
                intent.putExtra("gi6",categoryData.get(position).getGi6());
                intent.putExtra("gi7",categoryData.get(position).getGi7());
                intent.putExtra("gi8",categoryData.get(position).getGi8());
                intent.putExtra("gi9",categoryData.get(position).getGi9());
                intent.putExtra("gi10",categoryData.get(position).getGi10());
                intent.putExtra("gi11",categoryData.get(position).getGi11());
                intent.putExtra("gi12",categoryData.get(position).getGi12());
                intent.putExtra("gi13",categoryData.get(position).getGi13());
                intent.putExtra("gi14",categoryData.get(position).getGi14());
                intent.putExtra("gi15",categoryData.get(position).getGi15());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount()
    {
        return categoryData.size();
    }

    public static class GalleryDataHolder extends  RecyclerView.ViewHolder
    {

        public TextView category_name;
        public LinearLayout layout;

        public GalleryDataHolder(@NonNull View itemView)
        {
            super(itemView);

            category_name  = itemView.findViewById(R.id.categoryNameInMenu);
            layout = itemView.findViewById(R.id.categoryBackLinearLayout);



        }
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }
}
