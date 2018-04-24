package com.example.alowishusad.androidprojekat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import model.Post;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadPostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPostFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myInflatedView = inflater.inflate(R.layout.fragment_read_post, container,false);
////////////
        Post post = (Post) getArguments().getSerializable("post");
        post.setDate(new Date());
////////////////
        TextView postTitle = (TextView) myInflatedView.findViewById(R.id.postTitle);
        postTitle.setText(post.getTitle());

        TextView postDescription = (TextView) myInflatedView.findViewById(R.id.postDescription);
        postDescription.setText(post.getDescription());

        TextView postDate = (TextView) myInflatedView.findViewById(R.id.postDate);
        String dateString = post.getDate().toString().substring(3, 16);
        postDate.setText(dateString);

        return myInflatedView;
    }

}
