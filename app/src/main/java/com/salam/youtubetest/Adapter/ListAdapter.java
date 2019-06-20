package com.salam.youtubetest.Adapter;

import android.app.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.salam.youtubetest.Models.VideoList;
import com.salam.youtubetest.R;
import com.salam.youtubetest.ViewDetails;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

        Activity activity;
        ArrayList<VideoList> videosList;
        LayoutInflater inflater;
        ListAdapter listAdapter;
        private List<VideoList> mvideoList;

    public ListAdapter(Activity activity, ArrayList<VideoList> videosList){
            this.activity = activity;
            this.videosList = videosList;
    }

    public void addListItemToAdapter (List<VideoList> list){
            mvideoList.addAll(list);
            this.notifyDataSetChanged();

    }

    @Override
    public Object getItem(int position) {
        return this.videosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (inflater == null){
                inflater = this.activity.getLayoutInflater();
            }

            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_list, null);
            }
        final VideoList videoList = this.videosList.get(position);
        ImageView imageView = convertView.findViewById(R.id.video_image);
        TextView textView = convertView.findViewById(R.id.video_title);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.item_view);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintnet  = new Intent(activity, ViewDetails.class);
                newintnet.putExtra("videoId", videoList.getVideoid());

                activity.startActivity(newintnet);
            }
        });

        Picasso.get().load(videoList.getUrl()).into(imageView);
        textView.setText(videoList.getTitle());
            return convertView;
    }

    @Override
    public int getCount() {
        return this.videosList.size();
    }

}
