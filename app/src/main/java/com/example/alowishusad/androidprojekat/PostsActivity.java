package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.PostAdapter;
import model.Post;

public class PostsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawerLayout);


        ArrayList<Post> posts = new ArrayList<>();
        Post post = new Post("Naslov", "Deskrip");
        Post post1 = new Post("Naslov 1", "Deskrip");
        Post post2 = new Post("Naslov 2", "Deskrip");
        Post post3 = new Post("Naslov 3", "Deskrip");
        Post post4 = new Post("Naslov 4", "Deskrip");
        Post post5 = new Post("Naslov 5", "Deskrip");
        Post post6 = new Post("Naslov t6", "Deskrip");
        Post post7 = new Post("Naslov 7", "Deskrip");

        posts.add(post);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
        posts.add(post6);
        posts.add(post7);

        PostAdapter adapter = new PostAdapter(this, posts);
        ListView listView = (ListView) findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.drawerBtnCreatePost:
                Toast.makeText(this, "text", 1);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
