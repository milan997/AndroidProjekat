package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
    }

    public void btnStartsCreatePostActivity (View view) {
        Intent intent = new Intent(PostsActivity.this, CreatePostActivity.class);
        startActivity(intent);
    }

    public void btnStartsReadPostActivity (View view) {
        Intent intent = new Intent(PostsActivity.this, ReadPostActivity.class);
        startActivity(intent);
    }

    public void btnStartsSettingsActivity (View view) {
        Intent intent = new Intent(PostsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
