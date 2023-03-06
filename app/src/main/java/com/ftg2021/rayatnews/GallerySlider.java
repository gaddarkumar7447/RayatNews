package com.ftg2021.rayatnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ftg2021.rayatnews.Adapter.MainSliderAdapter;
import com.ftg2021.rayatnews.Adapter.NewsDataAdapterNew;
import com.ftg2021.rayatnews.Model.Banner;
import com.ftg2021.rayatnews.Model.NewsGalleryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class GallerySlider extends AppCompatActivity {

    public List<Banner> listBanner=new ArrayList<>();

    private GlideImageLoadingService glideImageLoadingService;

    String gi1,gi2,gi3,gi4,gi5,gi6,gi7,gi8,gi9,gi10,gi11,gi12,gi13,gi14,gi15;

    Slider slider;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_slider);

        Intent intent = getIntent();
        gi1 = intent.getStringExtra("gi1");
        gi2 = intent.getStringExtra("gi2");
        gi3 = intent.getStringExtra("gi3");
        gi4 = intent.getStringExtra("gi4");
        gi5 = intent.getStringExtra("gi5");
        gi6 = intent.getStringExtra("gi6");
        gi7 = intent.getStringExtra("gi7");
        gi8 = intent.getStringExtra("gi8");
        gi9 = intent.getStringExtra("gi9");
        gi10 = intent.getStringExtra("gi10");
        gi11 = intent.getStringExtra("gi11");
        gi12 = intent.getStringExtra("gi12");
        gi13 = intent.getStringExtra("gi13");
        gi14 = intent.getStringExtra("gi14");
        gi15 = intent.getStringExtra("gi15");

        glideImageLoadingService = new GlideImageLoadingService(getApplicationContext());
        Slider.init(glideImageLoadingService);

        slider = findViewById(R.id.banner_slider1);

        if(!gi1.equals(""))
            listBanner.add(new Banner("1",gi1));

        if(!gi2.equals(""))
            listBanner.add(new Banner("2",gi2));

        if(!gi3.equals(""))
            listBanner.add(new Banner("3",gi3));

        if(!gi4.equals(""))
            listBanner.add(new Banner("4",gi4));

        if(!gi5.equals(""))
            listBanner.add(new Banner("5",gi5));

        if(!gi6.equals(""))
            listBanner.add(new Banner("6",gi6));

        if(!gi7.equals(""))
            listBanner.add(new Banner("7",gi7));

        if(!gi8.equals(""))
            listBanner.add(new Banner("8",gi1));

        if(!gi9.equals(""))
            listBanner.add(new Banner("9",gi9));

        if(!gi10.equals(""))
            listBanner.add(new Banner("10",gi10));

        if(!gi11.equals(""))
            listBanner.add(new Banner("11",gi11));

        if(!gi12.equals(""))
            listBanner.add(new Banner("12",gi12));

        if(!gi13.equals(""))
            listBanner.add(new Banner("13",gi13));

        if(!gi14.equals(""))
            listBanner.add(new Banner("14",gi14));

        if(!gi15.equals(""))
            listBanner.add(new Banner("15",gi1));

        floatingActionButton = findViewById(R.id.fab_share);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
                shareIntentCalling();
            }
        });


        slider.setAdapter(new MainSliderAdapter(getApplicationContext(),listBanner));
    }

    public void shareIntentCalling() {
        View savedImage = (View) slider;

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
        int v1 = floatingActionButton.getVisibility();
        floatingActionButton.setVisibility(View.GONE);
        savedImage.draw(canvas);
        floatingActionButton.setVisibility(v1);
        //return the bitmap

        shareImageandText(returnedBitmap);

    }

    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        String shareText = "Rayat News: थोडक्यात पण महत्वाचं\n" + "http://bit.ly/3y3704B";
        intent.putExtra(Intent.EXTRA_TEXT, shareText);

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Rayat News");

        // setting type to image
        intent.setType("image/jpeg");

        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.jpeg");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(getApplicationContext(), "com.anni.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

}