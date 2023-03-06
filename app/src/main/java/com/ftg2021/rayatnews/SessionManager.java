package com.ftg2021.rayatnews;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    private static final String PREFER_NAME = "Rayat News";

    // Context
    private  static Context context;

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Shared pref mode
    int PRIVATE_MODE = 0;


    public static String title_t="";

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME , Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    //Create login session
    public void storeTitleT(){

        SessionManager.title_t = "1";

        // Storing name in pref
        editor.putString("title_t", "1");

        // commit changes
        editor.commit();
    }


    public void fetchData(){

        //initialize the variables
        SessionManager.title_t =  pref.getString("title_t","");

        //Toast.makeText(context,""+SessionManager.userId,Toast.LENGTH_LONG).show();

    }

}
