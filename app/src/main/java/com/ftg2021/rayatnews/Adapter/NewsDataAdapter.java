package com.ftg2021.rayatnews.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsDataModel;
import com.ftg2021.rayatnews.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class NewsDataAdapter extends RecyclerView.Adapter<NewsDataAdapter.NewsDataHolder> {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private List<NewsDataModel> newsData;
    private Context context;

    public NewsDataAdapter(Context c, List<NewsDataModel> newsList) {
        this.context = c;
        this.newsData = newsList;
        Log.i("data_setting", "" + newsData.size());
    }

    @NonNull
    @Override
    public NewsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_news_view, parent, false);
        NewsDataHolder siteListHolder = new NewsDataHolder(view);


        return siteListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsDataHolder holder, int position) {


            setFadeAnimation(holder.itemView);
            String url = newsData.get(position).getImage();
            try {
                Glide.with(holder.itemView)
                        .load(url)
                        .fitCenter()
                        .into(holder.news_image);
            } catch (Exception e) {
            }

//            manage ads section
        if(  position % 4 == 0 && position != 0 ) {

            Log.e("Position",""+position);


            holder.newsHideLayout.setVisibility(View.GONE);
            holder.templateView.setVisibility(View.VISIBLE);


            MobileAds.initialize(context);
            AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-8261870350533174/5594185411")
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(ContextCompat.getColor(context, R.color.white))).build();

                            holder.templateView.setStyles(styles);
                            holder.templateView.setNativeAd(nativeAd);
                        }
                    })
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }
//        end ads section


        Log.e("Position",""+position+" "+newsData.get(position).getHeading());

            if (newsData.get(position).getDescription().trim().equals("banner")) {
                Log.d("Banners", newsData.get(position).getDescription());

                holder.relativeLayout.setVisibility(View.GONE);
                holder.newsTitleFrameLayout.setVisibility(View.GONE);
                holder.shareButton1.setVisibility(View.VISIBLE);

                holder.imageLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                holder.news_image.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


            } else if (!newsData.get(position).isAds()) {
                holder.relativeLayout.setVisibility(View.VISIBLE);
                holder.newsTitleFrameLayout.setVisibility(View.VISIBLE);
                holder.shareButton1.setVisibility(View.GONE);

                holder.news_description.setText(newsData.get(position).getDescription());
                holder.news_heading.setText(newsData.get(position).getHeading());

                holder.news_image.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.image_size);
                holder.news_image.setScaleType(ImageView.ScaleType.FIT_XY);

                holder.sourceName.setText("Brief by " + newsData.get(position).getPublisher() + " | source:" + newsData.get(position).getSource());


                holder.news_heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.webView.loadUrl(newsData.get(position).getSourceUrl());
                        MainActivity.webView.setVisibility(View.VISIBLE);
                        MainActivity.fm.setVisibility(View.VISIBLE);

                    }
                });


                try {

                    String dateValue = newsData.get(position).getPostedAt();

                    SimpleDateFormat dateFormatParse = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
                    dateFormatParse.setTimeZone(TimeZone.getTimeZone("IST"));
                    String targetDate = dateValue;
                    Date date = dateFormatParse.parse(targetDate);
//                dateFormatParse.setTimeZone(TimeZone.getTimeZone("IST"));

                    Calendar dateString = Calendar.getInstance();
                    dateString.setTime(date);

                    int num = dateString.get(Calendar.MONTH);
                    String month = " ";

                    DateFormatSymbols dfs = new DateFormatSymbols();
                    String[] months = dfs.getMonths();
                    if (num >= 0 && num <= 11) {
                        month += months[num];
                    }


                    String day = " " + dateString.get(Calendar.DAY_OF_MONTH);
                    String year = " " + dateString.get(Calendar.YEAR);

                    int hrs = dateString.get(Calendar.HOUR_OF_DAY);
                    if (dateString.get(Calendar.HOUR_OF_DAY) > 12)
                        hrs -= 12;
                    String mnt = "" + dateString.get(Calendar.MINUTE);
                    int amPm = dateString.get(Calendar.AM_PM);
                    String amPmData = "";
                    if (amPm == Calendar.PM)
                        amPmData = "AM";
                    else
                        amPmData = "PM";


                    holder.postedAt.setText("posted on" + day + month + year + " at " + hrs + ":" + mnt + " " + amPmData);

                } catch (Exception e) {
                    Log.i("time", "" + e.getMessage());
                }


                //  setFadeAnimation(holder.news_image);


            }


            if (MainActivity.appBar.getVisibility() == View.VISIBLE) {


                MainActivity.appBar.setVisibility(View.GONE);
                MainActivity.bottomNavigationView.setVisibility(View.GONE);
                MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);

                MainActivity.visibility = false;
            }


            holder.newsFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.appBar.getVisibility() == View.VISIBLE) {


                        MainActivity.appBar.setVisibility(View.GONE);
                        MainActivity.bottomNavigationView.setVisibility(View.GONE);
                        MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);

                        holder.newsPostedAtLay.setVisibility(View.GONE);

                        MainActivity.visibility = false;
                    } else {
                        MainActivity.appBar.setVisibility(View.VISIBLE);
                        MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                        MainActivity.blackBoxBehindBottomNav.setVisibility(View.VISIBLE);

                        holder.newsPostedAtLay.setVisibility(View.VISIBLE);

                        MainActivity.visibility = true;
                    }
                }
            });

            holder.sourceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.newsPostedAtLay.getVisibility() == View.VISIBLE) {
                        holder.newsPostedAtLay.setVisibility(View.GONE);
                        setFadeAnimation(holder.newsPostedAtLay);
                    } else {
                        holder.newsPostedAtLay.setVisibility(View.VISIBLE);
                        setFadeAnimation(holder.newsPostedAtLay);
                    }
                }
            });


            holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareIntentCalling(holder, position, v.getContext());
                }
            });

            holder.shareButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareIntentCalling(holder, position, v.getContext());
                }
            });
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    private void shareImageandText(Bitmap bitmap, Context context, String heading) {
        Uri uri = getmageToShare(bitmap, context);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        String shareText = "*" + heading + "*\n\n" + "Rayat News1: थोडक्यात पण महत्वाचं\n" + "http://bit.ly/3y3704B";
        intent.putExtra(Intent.EXTRA_TEXT, shareText);

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Rayat News");

        // setting type to image
        intent.setType("image/jpeg");

        // calling startactivity() to share
        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap, Context context) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.jpeg");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(context, "com.anni.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    public void shareIntentCalling(@NonNull NewsDataHolder holder, int position, Context context) {
        View savedImage = (View) holder.itemView1;

        Bitmap returnedBitmap = Bitmap.createBitmap(savedImage.getWidth(), savedImage.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = savedImage.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else {  //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
            Log.d("Share", "setting white");
        }
        // draw the view on the canvas
        int v1 = holder.shareButton.getVisibility();
        int v2 = holder.shareButton1.getVisibility();
        holder.shareButton.setVisibility(View.GONE);
        holder.shareButton1.setVisibility(View.GONE);
        savedImage.draw(canvas);
        holder.shareButton.setVisibility(v1);
        holder.shareButton1.setVisibility(v2);
        //return the bitmap

        shareImageandText(returnedBitmap, context, holder.news_heading.getText().toString());


    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    public static class NewsDataHolder extends RecyclerView.ViewHolder {
        public TextView news_heading;
        public MaterialTextView sourceName, postedAt;
        public MaterialTextView news_description;
        public ImageView news_image;
        public FrameLayout newsFrameLayout;
        public RelativeLayout relativeLayout;
        public FrameLayout imageLayout, newsTitleFrameLayout,newsHideLayout;
        public LinearLayout newsPostedAtLay, newsHeadLay;
        public View shareButton, shareButton1;
        public ImageView shareIcon;
        public View itemView1;

        public TemplateView templateView;


        // public WebView webView;

        public NewsDataHolder(@NonNull View itemView) {
            super(itemView);
            itemView1 = itemView;
            news_heading = itemView.findViewById(R.id.news_heading);
            news_description = itemView.findViewById(R.id.news_description);

            news_image = itemView.findViewById(R.id.news_image);
            newsFrameLayout = itemView.findViewById(R.id.news_frame_layout);
            newsHideLayout = itemView.findViewById(R.id.news_hide_layout);

            sourceName = itemView.findViewById(R.id.news_source);


            relativeLayout = itemView.findViewById(R.id.image_layR);
            imageLayout = itemView.findViewById(R.id.images_layout);

            newsTitleFrameLayout = itemView.findViewById(R.id.fl_shadow_container);

            newsPostedAtLay = itemView.findViewById(R.id.news_postedAtLayout);
            newsHeadLay = itemView.findViewById(R.id.news_headingLayout);

            postedAt = itemView.findViewById(R.id.news_postedAt);

            shareButton = itemView.findViewById(R.id.shareButton);
            shareButton1 = itemView.findViewById(R.id.shareButton1);

            shareIcon = itemView.findViewById(R.id.shareIcon);

            templateView = itemView.findViewById(R.id.ads_template);

        }
    }
}