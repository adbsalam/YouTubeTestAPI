package com.salam.youtubetest.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.salam.youtubetest.Models.Comments;
import com.salam.youtubetest.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Comments> comment_list;
    LayoutInflater inflater;
    ListAdapter listAdapter;
    private List<Comments> mcommentList;

    public CommentAdapter(Activity activity, ArrayList<Comments> comment_list){
        this.activity = activity;
        this.comment_list = comment_list;
    }

    public void addCommentItemToAdapter (List<Comments> list){
        mcommentList.addAll(list);
        this.notifyDataSetChanged();

    }

    @Override
    public Object getItem(int position) {
        return this.mcommentList.get(position);
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
            convertView = inflater.inflate(R.layout.comments, null);
        }
        ImageView profiledp = convertView.findViewById(R.id.profile_image);

        TextView comment_txt = convertView.findViewById(R.id.comments_txt);
        TextView name = convertView.findViewById(R.id.name);
        Comments comment_list = this.comment_list.get(position);
        name.setText(comment_list.getAuthorDisplayName());
        comment_txt.setText(comment_list.getTextDisplay());
        Picasso.get().load(comment_list.getAuthorProfileImageUrl()).into(profiledp);
        return convertView;
    }

    @Override
    public int getCount() {
        return this.comment_list.size();
    }

}
