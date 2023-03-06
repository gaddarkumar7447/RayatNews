package com.ftg2021.rayatnews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookmarkManager {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private static final String PREFER_NAME = "RayatNews5TechG";

    // Context
    private  static Context context;

    public static String bookmark = "";

    public BookmarkManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME , Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    //Create login session
    public void addBookmark(String bookmark){

        //initialize the variables
        BookmarkManager.bookmark = bookmark;
        // Storing name in pref
        editor.putString("bookmark", bookmark);

        // commit changes
        editor.commit();
    }

    public String fetchData(){
        //initialize the variables
        BookmarkManager.bookmark =  pref.getString("bookmark","");
        return BookmarkManager.bookmark;

        //Toast.makeText(context,""+SessionManager.userId,Toast.LENGTH_LONG).show();
    }


}
