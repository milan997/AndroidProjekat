package com.example.alowishusad.androidprojekat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Post;


/**
 * Fragment hosted in ReadPostActivity. Represents one tab in activity. Shows post details.
 */
public class ReadPostFragment extends Fragment {

    // Post which the fragment displays
    private Post post;

    private View myInflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myInflatedView = inflater.inflate(R.layout.fragment_read_post, container,false);
        return myInflatedView;
    }

    @Override
    public void onResume() {
        super.onResume();
        post = (Post) getArguments().getSerializable("post");

        TextView postTitle = myInflatedView.findViewById(R.id.postTitle);
        TextView postDescription = myInflatedView.findViewById(R.id.postDescription);
        TextView postAuthor = myInflatedView.findViewById(R.id.postAuthor);
        TextView postDate = myInflatedView.findViewById(R.id.postDate);

        postTitle.setText(post.getTitle());
        postDescription.setText(post.getDescription());
        postAuthor.setText(post.getAuthor() != null ? post.getAuthor().getUsername() : "noauthor");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        String dateString = (post.getDate() != null) ? dateFormat.format(post.getDate()) : "nodate";
        postDate.setText(dateString);
    }
}
