package com.salam.youtubetest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.youtubetest.Models.VideoList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ListView videos_Listview;
        ArrayList<VideoList> Video_List;
        ListAdapter listAdapter;
        String PageToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videos_Listview = findViewById(R.id.Videos);
        Video_List = new ArrayList<>();
        listAdapter = new com.salam.youtubetest.Adapter.ListAdapter(MainActivity.this, Video_List);

        ShowVideoList();
    }
    private void ShowVideoList() {
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyDg0nWE-tNw5HToiFJVm4u332vees-KfAo&channelId=UC_A--fhX5gea0i4UtpD99Gg&part=snippet,id&order=date&maxResults=20";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    String pagetoken = jsonObject.getString("nextPageToken");
                    PageToken = pagetoken;
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonobjectSnippit = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonobjectDefault = jsonobjectSnippit.getJSONObject("thumbnails").getJSONObject("medium");
                        String vide_id = jsonVideoId.getString("videoId");
                        VideoList VL = new VideoList();
                        VL.setVideoid(vide_id);
                        VL.setTitle(jsonobjectSnippit.getString("title"));
                        VL.setUrl(jsonobjectDefault.getString("url"));
                        VL.setDescription(jsonobjectSnippit.getString("description"));
                        Video_List.add(VL);
                        videos_Listview.setAdapter(listAdapter);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);


        //Scroll Listner for new items added
        videos_Listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount  == totalItemCount && totalItemCount!=0)
                {
                  final int position2 = videos_Listview.getFirstVisiblePosition();
                        final RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
                    String url2 = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyDg0nWE-tNw5HToiFJVm4u332vees-KfAo&channelId=UC_A--fhX5gea0i4UtpD99Gg&part=snippet,id&pageToken="+PageToken+"&order=date&maxResults=20";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                                    String pagetoken = jsonObject.getString("nextPageToken");
                                    PageToken = pagetoken;
                                    for(int i =0; i<20; i++){
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                                        JSONObject jsonobjectSnippit = jsonObject1.getJSONObject("snippet");
                                        JSONObject jsonobjectDefault = jsonobjectSnippit.getJSONObject("thumbnails").getJSONObject("medium");

                                        String vide_id = jsonVideoId.getString("videoId");
                                        VideoList VL = new VideoList();
                                        VL.setVideoid(vide_id);
                                        VL.setTitle(jsonobjectSnippit.getString("title"));
                                        VL.setUrl(jsonobjectDefault.getString("url"));
                                        VL.setDescription(jsonobjectSnippit.getString("description"));

                                        Video_List.add(VL);
                                        videos_Listview.setAdapter(listAdapter);
                                    }

                                    videos_Listview.setSelection(position2);
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });



                        Video_Queue.add(stringRequest);
                    }
            }});
        }

    }
