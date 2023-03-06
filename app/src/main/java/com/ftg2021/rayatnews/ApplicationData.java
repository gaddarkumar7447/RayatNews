package com.ftg2021.rayatnews;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class ApplicationData extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("token id", "token:"+token);
                        // Log and toast
                        /*String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });
        }
    }
