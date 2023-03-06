package com.ftg2021.rayatnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashScreen extends AppCompatActivity {

    public static int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

//            SessionManager sessionManager = new SessionManager(getApplicationContext());
//            sessionManager.fetchData();
        boolean flg = false;
        if (settings.getBoolean("my_first_time", true)) {
            SharedPreferences preferences = getSharedPreferences("Theme", MODE_PRIVATE);
            preferences.edit().putBoolean("isDarkMode", false).apply();
            settings.edit().putBoolean("my_first_time", false).apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            flg = true;
        } else {
//            if (getSharedPreferences("Theme", MODE_PRIVATE).getBoolean("isDarkMode", false))
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            else
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            flg = false;
        }
//        setContentView(R.layout.activity_splash_screen);

        String finalFlg = "1";
        SessionManager sessionManager = new SessionManager(SplashScreen.this);
        sessionManager.fetchData();
        finalFlg = SessionManager.title_t;
        String finalFlg1 = finalFlg;
//        Toast.makeText(getApplicationContext(),""+finalFlg1,Toast.LENGTH_LONG).show();
        new Handler().postDelayed(() -> {
            if (finalFlg1.equals(""))
                startActivity(new Intent(SplashScreen.this, GuideActivity.class));
            else
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();

        }, 2000);
    }

//    @SuppressLint("CommitPrefEdits")
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
}