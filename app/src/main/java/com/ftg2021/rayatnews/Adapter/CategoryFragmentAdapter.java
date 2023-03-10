package com.ftg2021.rayatnews.Adapter;


import android.content.Context;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsCategoryModel;
import com.ftg2021.rayatnews.R;
import com.ftg2021.rayatnews.fragments.Category;
import com.ftg2021.rayatnews.fragments.Discover;

import java.util.List;


public class CategoryFragmentAdapter extends RecyclerView.Adapter<CategoryFragmentAdapter.CategoryDataHolder>
{
    private List<NewsCategoryModel> categoryData;
    private Context context;
    private Discover fragment;

    public CategoryFragmentAdapter(Context c, List<NewsCategoryModel> newsList)
    {
        this.context=c;
        this.categoryData = newsList;
        Log.i("data_setting",""+categoryData.size());
    }

    @NonNull
    @Override
    public CategoryDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_single_item_in_fragment,parent,false);
        CategoryDataHolder siteListHolder = new CategoryDataHolder(view);
        return  siteListHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull CategoryDataHolder holder, int position)
    {
        final NewsCategoryModel myListData = categoryData.get(position);
        holder.category_name.setText(categoryData.get(position).getCategory_name());

        int catId = categoryData.get(position).getId();

        setFadeAnimation(holder.category_name);

        Drawable drawable = context.getResources().getDrawable(R.drawable.cat_news);


        switch (holder.category_name.getText().toString().trim()) {
            case "???????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_news);
                break;
            case "?????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_sports);
                break;
            case "?????????????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_entertainment);
                break;
            case "????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_farmer);
                break;
            case "?????????????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_business);
                break;
            case "????????????????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_lakshvedhi);
                break;
            case "???????????????????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_politics);
                break;
            case "???????????????":
                drawable = context.getResources().getDrawable(R.drawable.cat_jobs);
                break;
        }

        holder.layout.setBackground(drawable);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Discover.catListLayout.setVisibility(View.GONE);
                    Discover.newsByCatIdLayout.setVisibility(View.VISIBLE);
                    Discover.fetchByCatId(""+catId);

                }catch (Exception e){
                    Log.i("ERROR1234",e.getMessage());
                }

            }
        });
    }



    @Override
    public int getItemCount()
    {
        return categoryData.size();
    }

    public static class CategoryDataHolder extends  RecyclerView.ViewHolder
    {

        public TextView category_name;
        public LinearLayout layout;

        public CategoryDataHolder(@NonNull View itemView)
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
