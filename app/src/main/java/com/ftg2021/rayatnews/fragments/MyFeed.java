package com.ftg2021.rayatnews.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ftg2021.rayatnews.Adapter.NewsDataAdapterNew;
import com.ftg2021.rayatnews.FirebasePushNotification.FirebaseRegisterToken;
import com.ftg2021.rayatnews.MainActivity;
import com.ftg2021.rayatnews.Model.NewsDataModel;
import com.ftg2021.rayatnews.R;
import com.ftg2021.rayatnews.Scroll.SnapHelperOneByOne;
import com.ftg2021.rayatnews.api.AccessData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyFeed extends Fragment {

    public static int NotificationNewsPosition;
    public View root;
    List<NewsDataModel> newsList = new ArrayList<>();
    LinearLayout loadingBar;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_my_feed, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rvNews);

        loadingBar = root.findViewById(R.id.loadingProgressBar);

        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        fetchAllNews();

//        recyclerView.smoothScrollToPosition(MyFeed.NotificationNewsPosition);
        return root;
    }

    //fetching all news

    private void fetchAllNews() {

        //   loadingBar.setVisibility(View.VISIBLE);
        String data = "";
        try {
            final String savedata = data;
            String URL = AccessData.getApiUrl() + "";
            Log.e("RAYAT API", URL + " " + data);

            //Toast.makeText(getContext(),"Fetching cats"+URL, Toast.LENGTH_SHORT).show();

            requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        //Toast.makeText(getContext(),"Fetching cats data", Toast.LENGTH_SHORT).show();

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data").getJSONArray(0);

                        //JSONArray jsonArray =new  JSONArray(response["data"]);

                        //Log.i("CATS","NO DATA FOUND");

                        MyFeed.NotificationNewsPosition = 0;


                        int size = 70;
                        if (jsonArray.length() < size)
                            size = jsonArray.length();


                        newsList.clear();

                        for (int i = 0; i < size; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            //add advertisement at every 3rd location
                            if (i % 3 == 0 && i != 0)
                                newsList.add(new NewsDataModel(-1, "", "", "", "", "", "", "", true, 0));

                            //add news
                            int newsId = object.getInt("newsId");

                            /*String imageUrl = AccessData.getBaseUrl()+ "/RayatNews-backend_node-master/" + object.getString("imageUrl");*/
                            /*String imageUrl = AccessData.getBaseUrl()+ "api/news/" + object.getString("imageUrl");*/
                            String imageUrl = object.getString("imageUrl");
                            Log.d("image Url", "ImageUrl: "+imageUrl);

                            String heading = object.getString("title");
                            String description = object.getString("description");
                            String publisher = object.getString("publisher");
                            String sourceName = object.getString("sourceName");
                            String sourceUrl = object.getString("source");
                            String date = object.getString("date");

                            newsList.add(new NewsDataModel(newsId, heading, description, imageUrl, date, sourceName, publisher, sourceUrl, false, 0));
                        }
                        for (int i = 0; i < newsList.size(); i++) {
                            MyFeed.NotificationNewsPosition = 0;
                            int newsId = newsList.get(i).getId();

                            if (newsId == MainActivity.position && !newsList.get(i).isAds()) {
                                MyFeed.NotificationNewsPosition = i;
                                Log.i("Notification", "in MF :" + newsId);
                                break;
                            }
                        }

                        loadingBar.setVisibility(View.GONE);

//                        for (int i = 0; i < newsList.size(); i++)
//                            for (int j = 0; j < newsList.size(); j++)
//                                if (newsList.get(i).getId() == newsList.get(j).getId() && i != j)
//                                    Log.e("Duplicate found", "" + newsList.get(i).getId());

                        NewsDataAdapterNew adapter = new NewsDataAdapterNew(root.getContext(), newsList);
                        recyclerView.setHasFixedSize(true);
                        //recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setStackFromEnd(false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
                            @Override
                            public boolean onFling(int velocityX, int velocityY) {
                                if (Math.abs(velocityY) > 100) {
                                    velocityY = (int) (0.2 * (int) Math.signum((double) velocityY));
                                    recyclerView.fling(velocityX, velocityY);
                                    return false;
                                }
                                return true;
                            }
                        });
                        recyclerView.setAdapter(adapter);

                        if (MainActivity.isRefreshclicked)
                            MainActivity.isRefreshclicked = false;
                        else
                            Objects.requireNonNull(recyclerView.getLayoutManager())
                                    .scrollToPosition(MyFeed.NotificationNewsPosition);


                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No internet connection ..", Toast.LENGTH_SHORT).show();
                        Log.d("status", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        Toast.makeText(getContext(), "No internet connection ..", Toast.LENGTH_SHORT).show();
                        Log.i("status", error.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @SuppressLint("NewApi")
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return savedata == null ? null : savedata.getBytes(StandardCharsets.UTF_8);
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            Toast.makeText(getContext(), "No internet connection ..", Toast.LENGTH_SHORT).show();
        }
    }
}

