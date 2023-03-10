package com.ftg2021.rayatnews.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftg2021.rayatnews.R;
import com.ftg2021.rayatnews.URL.OpenURLInBrowser;


public class AboutUs extends Fragment {

    View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       root = inflater.inflate(R.layout.fragment_about_us, container, false);

        TextView website = root.findViewById(R.id.websiteButton);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                new OpenURLInBrowser(getContext(),"http://www.rayatnews.com");
                }catch (Exception e){}
            }
        });

        TextView email = root.findViewById(R.id.emailButton);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","rayatnews4@gmail.com", null));
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }catch (Exception e){}
            }
        });

       return root;
    }

}