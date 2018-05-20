package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Post;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.PostService;
import services.RetrofitObject;
import services.UserService;


/**
 * Fragment hosted in ReadPostActivity. Represents one tab in activity. Shows post details.
 */
public class ReadPostFragment extends Fragment {

    // Post which the fragment displays
    private Post post;

    private View myInflatedView;

    private TextView postTitle;
    private TextView postDescription;
    private TextView postAuthor;
    private TextView postDate;
    private TextView postPopularity;

    private ImageButton btnLike;
    private boolean btnLikePressed;
    private ImageButton btnDislike;
    private boolean btnDislikePressed;

    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myInflatedView = inflater.inflate(R.layout.fragment_read_post, container,false);

        // Initializing text views
        postTitle = myInflatedView.findViewById(R.id.postTitle);
        postDescription = myInflatedView.findViewById(R.id.postDescription);
        postAuthor = myInflatedView.findViewById(R.id.postAuthor);
        postDate = myInflatedView.findViewById(R.id.postDate);
        postPopularity = myInflatedView.findViewById(R.id.postPopularity);
        btnLike = myInflatedView.findViewById(R.id.btnPostLike);
        btnDislike = myInflatedView.findViewById(R.id.btnPostDislike);


        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLikePressed();
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDislikePressed();
            }
        });

        return myInflatedView;
    }

    private void btnLikePressed() {
        // Provera da li je ulogovani korisnik autora posta, ako jeste ne moze da lajkuje-dislajkuje
        if(userId == 0)
            userId = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
        if(userId == post.getAuthor().getId()){
            Gadgets.showSimpleOkDialog(getContext(), "Ne mozete lajkovati svoju objavu!!!");
            return;
        }

        // Ako nije vec kliknuto izvrsava se, u suprotnom kulira
        if(!btnLikePressed){
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.likePost(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.body().close();

                    post.like();
                    postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnLikePressed = true;
                    btnLike.setBackground(getResources().getDrawable(R.drawable.like_pressed));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void btnDislikePressed() {
        // Provera da li je ulogovani korisnik autora posta, ako jeste ne moze da lajkuje-dislajkuje
        if(userId == 0)
            userId = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
        if(userId == post.getAuthor().getId()){
            Gadgets.showSimpleOkDialog(getContext(), "Ne mozete dislajkovati svoju objavu!!!");
            return;
        }

        if(!btnDislikePressed){
            PostService postService = RetrofitObject.retrofit.create(PostService.class);
            Call<ResponseBody> call = postService.dislikePost(post.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.body().close();

                    post.dislike();
                    postPopularity.setText(Integer.toString(post.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnDislikePressed = true;
                    btnDislike.setBackground(getResources().getDrawable(R.drawable.dislike_pressed));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        post = (Post) getArguments().getSerializable("post");

        postTitle.setText(post.getTitle());
        postDescription.setText(post.getDescription());
        postAuthor.setText(post.getAuthor() != null ? post.getAuthor().getUsername() : "noauthor");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        String dateString = (post.getDate() != null) ? dateFormat.format(post.getDate()) : "nodate";
        postDate.setText(dateString);

        // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
        postPopularity.setTextColor((post.getPopularity() >= 0) ? Color.GREEN : Color.RED);
        postPopularity.setText(Integer.toString(post.getPopularity()));
    }
}
