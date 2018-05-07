package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import adapters.PostAdapter;
import model.Data;
import model.Post;

public class PostsActivity extends AppCompatActivity {

    private ArrayList<Post> posts;
    private ListView listView;

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


        // Preuzimamo dummy listu iz klase Data
        posts = Data.posts;

        // Pozivamo funkciju za sortiranje
        sortPostsByPreference(posts);

        //Collections.sort(posts);

        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume(){
        super.onResume();

        posts = Data.posts;
        sortPostsByPreference(posts);
        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id. btnSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to sort posts list by the user's preference stored in ListPreference lpSortPostsBy
     * @param posts - ArrayList<Post> a list to sort
     */
    public void sortPostsByPreference(ArrayList<Post> posts){
        // PO cemu da sortiramo ???  ?? ? ?
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPostsBy = sp.getString("lpSortPostsBy", "default123");

        // Sortiramo
        if(sortPostsBy.equals("Date")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post1, Post post2) {
                    return post1.getDate().compareTo(post2.getDate());
                }
            });
        } else if (sortPostsBy.equals("Popularity")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post2, Post post1) {
                    if(post1.getPopularity() > post2.getPopularity()){
                        return 1;
                    } else if (post1.getPopularity() < post2.getPopularity()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(this, "Sorting went wrong, posts unsorted!\n" + sortPostsBy, Toast.LENGTH_LONG).show();
        }
    }

}
