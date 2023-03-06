package com.ftg2021.rayatnews;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ftg2021.rayatnews.Adapter.MainSliderAdapter;
import com.ftg2021.rayatnews.Adapter.MainSliderAdapter2;
import com.ftg2021.rayatnews.Model.Banner;
import com.ftg2021.rayatnews.Model.Banner2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class GuideActivity extends AppCompatActivity {

    public List<Banner2> listBanner = new ArrayList<>();
    String gi1, gi2, gi3, gi4, gi5;
    Slider slider;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        SessionManager sessionManager = new SessionManager(GuideActivity.this);
        sessionManager.storeTitleT();

//        Toast.makeText(this, "HIT", Toast.LENGTH_SHORT).show();

        gi1 = "test";
        gi2 = "test";
        gi3 = "test";
        gi4 = "test";
        gi5 = "test";

        GlideImageLoadingService glideImageLoadingService = new GlideImageLoadingService(getApplicationContext());
        Slider.init(glideImageLoadingService);

        slider = findViewById(R.id.banner_slider);

        if (!gi1.equals(""))
            listBanner.add(new Banner2("1", R.drawable.b1));

        if (!gi2.equals(""))
            listBanner.add(new Banner2("2", R.drawable.b2));

        if (!gi3.equals(""))
            listBanner.add(new Banner2("3", R.drawable.b3));

        if (!gi4.equals(""))
            listBanner.add(new Banner2("4", R.drawable.b4));

//        if (!gi5.equals(""))
//            listBanner.add(new Banner("5", gi5));


        floatingActionButton = findViewById(R.id.fab_next);
        floatingActionButton.setOnClickListener(view -> {
//                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
//                shareIntentCalling();
            Intent i = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(i);
            //finish();
        });
        slider.setAdapter(new MainSliderAdapter2(getApplicationContext(), listBanner));
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