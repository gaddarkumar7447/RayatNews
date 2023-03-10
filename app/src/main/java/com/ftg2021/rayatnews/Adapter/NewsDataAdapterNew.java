package com.ftg2021.rayatnews.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.ftg2021.rayatnews.BookmarkManager;
import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsDataModel;
import com.ftg2021.rayatnews.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Handler;

public class NewsDataAdapterNew extends RecyclerView.Adapter {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private final List<NewsDataModel> newsData;
    private final Context context;

    public NewsDataAdapterNew(Context c, List<NewsDataModel> newsList) {
        this.context = c;
        this.newsData = newsList;
        Log.i("data_setting", "" + newsData.size());

        if (MainActivity.appBar.getVisibility() == View.VISIBLE) {
            MainActivity.appBar.setVisibility(View.GONE);
            MainActivity.bottomNavigationView.setVisibility(View.GONE);
            MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);
            MainActivity.visibility = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (newsData.get(position).isAds() == true) {
            Log.e("Position", "" + position);
            return 0;
        } else if (newsData.get(position).getDescription().trim().equals("banner"))
            return 1;

        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;


        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.layout_native_ads, parent, false);
            return new NativeAdsViewHolder(view);
        } else if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.layout_image_banner, parent, false);
            return new BannerImageViewHolder(view);
        }

        view = layoutInflater.inflate(R.layout.layout_single_news_view, parent, false);
        return new NewsDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holderR, @SuppressLint("RecyclerView") int position) {
        if (holderR.getItemViewType() == 0 && position != 0) {
            NativeAdsViewHolder holder;
            //bind native google ads
            holder = (NativeAdsViewHolder) holderR;
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
        } else if (holderR.getItemViewType() == 1) {
            BannerImageViewHolder holder = (BannerImageViewHolder) holderR;
            try {
                setFadeAnimation(holder.itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = newsData.get(position).getImage();
            try {
                Glide.with(holderR.itemView)
                        .load(url)
                        .fitCenter()
                        .into(holder.banner_image);
            } catch (Exception e) {}
            String title = newsData.get(position).getHeading().trim();
            String type = newsData.get(position).getHeading().substring(0, 1);//a=local ads / b= banner image
            if (type.equalsIgnoreCase("a")) {
                holder.shareButton1.setVisibility(View.GONE);

                try {
                    if (!title.substring(1).equals("")) {
                        holder.banner_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String urlLink = title.substring(1);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(urlLink));
                                    context.startActivity(i);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                holder.shareButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareBannerIntentCalling(holder, position, v.getContext());
                    }
                });
            }
            Log.e("Banners", newsData.get(position).getDescription());
        } else if (holderR.getItemViewType() == 2 && !newsData.get(position).getHeading().isEmpty() && !newsData.get(position).isAds()) {
            //bind news
            try {
                NewsDataViewHolder holder = null;
                try {
                    holder = (NewsDataViewHolder) holderR;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    setFadeAnimation(holder.itemView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String url = newsData.get(position).getImage();
                Log.e("Image URL", url);
                try {
                    Glide.with(holderR.itemView)
                            .load(url)
                            .fitCenter()
                            .into(holder.news_image);
                } catch (Exception e) {
                    Log.e("image", e.getMessage());
                }

                BookmarkManager bookmarkManager1 = new BookmarkManager(context);
                String bookmark1 = bookmarkManager1.fetchData();

                String[] ar1 = bookmark1.split(",");

                int flg = 0;

                for (int i = 0; i < ar1.length; i++) {
                    if (ar1[i].equals(String.valueOf(newsData.get(position).getId()))) {
//                      Toast.makeText(context,ar1[i]+" "+String.valueOf(newsData.get(position).getId())+" "+bookmark1,Toast.LENGTH_LONG).show();
                        flg = 1;
                    }
                }

                if (flg == 1) {
                    newsData.get(position).setbFlag(1);
                    holder.bookmarkIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_book_24));
                } else {
                    holder.bookmarkIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_bookmark));
                }

                Log.e("Position", "" + position + " " + newsData.get(position).getHeading());

                holder.relativeLayout.setVisibility(View.VISIBLE);

                //holder.newsTitleFrameLayout.setVisibility(View.VISIBLE);

                holder.news_description.setText(newsData.get(position).getDescription());
                holder.news_heading.setText(newsData.get(position).getHeading());

                holder.news_image.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.image_size);
                holder.news_image.setScaleType(ImageView.ScaleType.FIT_XY);

                holder.sourceName.setText("Brief by " + newsData.get(position).getPublisher() + " | source:" + newsData.get(position).getSource());

                holder.news_heading.setClickable(!newsData.get(position).getSourceUrl().trim().isEmpty());

                holder.news_heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!newsData.get(position).getSourceUrl().trim().isEmpty()) {
                            MainActivity.webView.loadUrl(newsData.get(position).getSourceUrl());
                            MainActivity.webView.setVisibility(View.VISIBLE);
                            MainActivity.fm.setVisibility(View.VISIBLE);
                        }
                    }
                });

                try {

                    String dateValue = newsData.get(position).getPostedAt();
                    SimpleDateFormat dateFormatParse = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.000Z'");
                    dateFormatParse.setTimeZone(TimeZone.getTimeZone("IST"));
                    String targetDate = dateValue;
                    Date date = dateFormatParse.parse(targetDate);
                    //dateFormatParse.setTimeZone(TimeZone.getTimeZone("IST"));

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


                if (MainActivity.appBar.getVisibility() == View.VISIBLE) {


                    MainActivity.appBar.setVisibility(View.GONE);
                    MainActivity.bottomNavigationView.setVisibility(View.GONE);
                    MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);

                    MainActivity.visibility = false;
                }


                NewsDataViewHolder finalHolder = holder;
                holder.newsFrameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MainActivity.appBar.getVisibility() == View.VISIBLE) {

                            MainActivity.appBar.setVisibility(View.GONE);
                            MainActivity.bottomNavigationView.setVisibility(View.GONE);
                            MainActivity.blackBoxBehindBottomNav.setVisibility(View.GONE);

                            finalHolder.newsPostedAtLay.setVisibility(View.GONE);

                            MainActivity.visibility = false;
                        } else {
                            MainActivity.appBar.setVisibility(View.VISIBLE);
                            MainActivity.bottomNavigationView.setVisibility(View.VISIBLE);
                            MainActivity.blackBoxBehindBottomNav.setVisibility(View.VISIBLE);

                            finalHolder.newsPostedAtLay.setVisibility(View.VISIBLE);

                            MainActivity.visibility = true;
                        }
                    }
                });

                NewsDataViewHolder finalHolder1 = holder;
                holder.sourceName.setOnClickListener(v -> {
                    if (finalHolder1.newsPostedAtLay.getVisibility() == View.VISIBLE)
                        finalHolder1.newsPostedAtLay.setVisibility(View.INVISIBLE);
                    else
                        finalHolder1.newsPostedAtLay.setVisibility(View.VISIBLE);
                    setFadeAnimation(finalHolder1.newsPostedAtLay);
                });


                NewsDataViewHolder finalHolder2 = holder;
                holder.shareButton.setOnClickListener(v -> {
                    //finalHolder2.newsLogo.setVisibility(View.VISIBLE);
                    shareIntentCalling(finalHolder2, position, v.getContext());
                });

                NewsDataViewHolder finalHolder3 = holder;
                holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (newsData.get(position).getbFlag() == 0) {
                            finalHolder3.bookmarkIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_book_24));
                            newsData.get(position).setbFlag(1);

                            BookmarkManager bookmarkManager = new BookmarkManager(context);
                            String bookmark = bookmarkManager.fetchData();
                            if (bookmark.length() == 0) {
                                bookmark = bookmark + newsData.get(position).getId();
                            } else {
                                bookmark = bookmark + "," + newsData.get(position).getId();
                            }

//                          Toast.makeText(context,""+bookmark,Toast.LENGTH_LONG).show();

                            bookmarkManager.addBookmark(bookmark);

                            Toast.makeText(view.getContext(), "???????????????????????? ????????????", Toast.LENGTH_LONG).show();
                        } else if (newsData.get(position).getbFlag() == 1) {
                            finalHolder3.bookmarkIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_bookmark));
                            newsData.get(position).setbFlag(0);

                            BookmarkManager bookmarkManager = new BookmarkManager(context);
                            String bookmark = bookmarkManager.fetchData();

                            String fBookmark = "";

                            String[] ar = bookmark.split(",");

                            for (int i = 0; i < ar.length; i++) {
                                if (!ar[i].equals(String.valueOf(newsData.get(position).getId()))) {
                                    if (fBookmark.length() == 0) {
                                        fBookmark = fBookmark + ar[i];
                                    } else {
                                        fBookmark = fBookmark + "," + ar[i];
                                    }
                                }
                            }

//                          Toast.makeText(context,""+fBookmark,Toast.LENGTH_LONG).show();
                            bookmarkManager.addBookmark(fBookmark);

                            Toast.makeText(view.getContext(), "???????????????????????? ???????????????????????????????????? ???????????????", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //  holder.container.stopShimmer();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
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
        String shareText = "*" + heading + "*\n\n" + "Rayat News: ???????????????????????? ?????? ????????????????????????\n" + "http://bit.ly/3y3704B";
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

    public void shareIntentCalling(@NonNull NewsDataViewHolder holder, int position, Context context) {
        View savedImage = (View) holder.itemView1;

        holder.newsPostedAtLay.setVisibility(View.VISIBLE);
        holder.shareButton.setVisibility(View.GONE);
        holder.bookmarkButton.setVisibility(View.GONE);

        holder.newsLogo.setVisibility(View.VISIBLE);


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
        savedImage.draw(canvas);

        //return the bitmap
        shareImageandText(returnedBitmap, context, holder.news_heading.getText().toString());

        holder.shareButton.setVisibility(View.VISIBLE);
        holder.bookmarkButton.setVisibility(View.VISIBLE);
        holder.newsLogo.setVisibility(View.INVISIBLE);
    }

    public void shareBannerIntentCalling(@NonNull BannerImageViewHolder holder, int position, Context context) {
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
        int v1 = holder.shareButton1.getVisibility();
        holder.shareButton1.setVisibility(View.GONE);
        savedImage.draw(canvas);
        //return the bitmap

        shareImageandText(returnedBitmap, context, newsData.get(position).getHeading().trim());
        holder.shareButton1.setVisibility(View.VISIBLE);
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 0.0f);
        anim.setDuration(5);
        view.startAnimation(anim);
    }

    static class NativeAdsViewHolder extends RecyclerView.ViewHolder {
        public TemplateView templateView;

        public NativeAdsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            templateView = itemView.findViewById(R.id.ads_template);
        }
    }

    static class NewsDataViewHolder extends RecyclerView.ViewHolder {
        public TextView news_heading;
        public MaterialTextView sourceName, postedAt;
        public MaterialTextView news_description;
        public ImageView news_image;
        public FrameLayout newsFrameLayout;
        public RelativeLayout relativeLayout;
        public FrameLayout imageLayout, newsTitleFrameLayout, newsHideLayout;
        public LinearLayout newsPostedAtLay, newsHeadLay;
        public View shareButton, bookmarkButton;
        public ImageView shareIcon;
        public ImageView bookmarkIcon;
        public View itemView1;
        public RelativeLayout title_t, share_t;
        public ImageView newsLogo;

        public NewsDataViewHolder(@NonNull @NotNull View itemView) {
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

            shareIcon = itemView.findViewById(R.id.shareIcon);

            bookmarkIcon = itemView.findViewById(R.id.bookmarkIcon);

            bookmarkButton = itemView.findViewById(R.id.bookmark_btn);

            newsLogo = itemView.findViewById(R.id.news_logo);

            title_t = itemView.findViewById(R.id.title_t);
            share_t = itemView.findViewById(R.id.share_t);

        }
    }

    static class BannerImageViewHolder extends RecyclerView.ViewHolder {

        public View shareButton1;
        public View itemView1;
        ImageView banner_image;

        public BannerImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemView1 = itemView;
            banner_image = itemView.findViewById(R.id.news_image_banner);
            shareButton1 = itemView.findViewById(R.id.shareButton1);
        }
    }
}