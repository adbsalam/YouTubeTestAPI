package com.salam.youtubetest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.youtubetest.Adapter.CommentAdapter;
import com.salam.youtubetest.Models.Comments;
import com.salam.youtubetest.Models.VideoList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewDetails extends AppCompatActivity {
        ImageView Video_image;
        TextView title, description, date, duration;
        String pub_txt = "Date Published: ";
        String du_txt = "Duration: ";
        ListView comments_listView;
        ArrayList<Comments> comments_list;
        CommentAdapter listAdapter;
        TextView showmore;
        String getVideoID;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_details);
            Video_image = findViewById(R.id.video_image_D);
            title = findViewById(R.id.Title_D);
            description = findViewById(R.id.description_D);
            date = findViewById(R.id.Date_D);
            duration = findViewById(R.id.duration);
            comments_listView = findViewById(R.id.comments);
            comments_list = new ArrayList<>();
            listAdapter = new com.salam.youtubetest.Adapter.CommentAdapter(ViewDetails.this, comments_list);
            showmore = findViewById(R.id.showmore_dis);
            Intent i = getIntent();
            getVideoID = i.getStringExtra("videoId");

            ShowVideoList();
            ShowDuration();
            ShowComments();

            showmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewDetails.this).create();
                    alertDialog.setTitle("Full Description");
                    alertDialog.setMessage(description.getText());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
    }

    private void ShowVideoList() {
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id="+getVideoID+"&key=AIzaSyDg0nWE-tNw5HToiFJVm4u332vees-KfAo";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for(int i =0; i<1; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonobjectSnippit = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonobjectDefault = jsonobjectSnippit.getJSONObject("thumbnails").getJSONObject("medium");
                        Picasso.get().load(jsonobjectDefault.getString("url")).into(Video_image);
                        date.setText(pub_txt + jsonobjectSnippit.getString("publishedAt"));
                        title.setText(jsonobjectSnippit.getString("title"));
                        description.setText(jsonobjectSnippit.getString("description"));
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
    }

    private void ShowDuration() {
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        String url2 = "https://www.googleapis.com/youtube/v3/videos?id="+getVideoID+"&part=contentDetails&key=AIzaSyDg0nWE-tNw5HToiFJVm4u332vees-KfAo";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for(int i =0; i<1; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonobjectContent = jsonObject1.getJSONObject("contentDetails");

                        String Duration_txt = jsonobjectContent.getString("duration");
                        String format_duration =  Duration_txt.replace("PT","")
                                .replace("H",":")
                                .replace("M","m:")
                                .replace("S","s");

                        duration.setText(du_txt + format_duration);
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
                        Video_Queue.add(stringRequest2);
    }

    private void ShowComments() {
                RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
                String url3 =  "https://www.googleapis.com/youtube/v3/commentThreads?key=AIzaSyDg0nWE-tNw5HToiFJVm4u332vees-KfAo&textFormat=plainText&part=snippet&videoId="+getVideoID+"&maxResults=10&sort=date";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for(int i =0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonobjectSnippit = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonobjectDefault = jsonobjectSnippit.getJSONObject("topLevelComment");
                        JSONObject jsonObject2 = jsonobjectDefault.getJSONObject("snippet");
                        Comments VL = new Comments();
                        VL.setAuthorDisplayName(jsonObject2.getString("authorDisplayName"));
                        VL.setTextDisplay(jsonObject2.getString("textDisplay"));
                        VL.setAuthorProfileImageUrl(jsonObject2.getString("authorProfileImageUrl"));
                        comments_list.add(VL);
                        comments_listView.setAdapter(listAdapter);
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
    }


    private String stringForTime(String ytFormattedTime) {
        return ytFormattedTime
                .replace("PT","")
                .replace("H",":")
                .replace("M",":")
                .replace("S","");


    }
}
