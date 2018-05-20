package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.PostAdapter;
import model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.PostService;
import services.RetrofitObject;

public class PostsActivity extends AppCompatActivity  /*implements NavigationView.OnNavigationItemSelectedListener  */{

    private List<Post> posts;
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

        listView = findViewById(R.id.listViewPosts);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = (Post) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ReadPostActivity.class);
                intent.putExtra("postId", post.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
/*
        posts = Data.posts;
        sortPostsByPreference(posts);
        PostAdapter adapter = new PostAdapter(this, posts);
        listView = findViewById(R.id.listViewPosts);
        listView.setAdapter(adapter);
*/
        PostService postService = RetrofitObject.retrofit.create(PostService.class);
        Call<List<Post>> call = postService.getAll();

        final ProgressDialog progressDialog = Gadgets.getProgressDialog(this);


        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                progressDialog.dismiss();
                posts = response.body();
                sortPostsByPreference();
                PostAdapter adapter = new PostAdapter(getApplicationContext(), posts);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "nije uspjeo", Toast.LENGTH_LONG).show();
            }
        });

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
        if (id == R.id.btnSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnCreatePost) {
            startActivity(new Intent(this, CreatePostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Function to sort posts list by the user's preference stored in ListPreference lpSortPostsBy
     *
     */
    public void sortPostsByPreference(){
        // PO cemu da sortiramo ???  ?? ? ?
        if(posts == null || posts.isEmpty())
            return;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPostsBy = sp.getString("lpSortPostsBy", "default123");

        // Sortiramo
        if(sortPostsBy.equals("Date")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post2, Post post1) {
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


    /**
     * Method that should logout the user on the click of logout button from navigation drawer
     * @param view View that created the event
     */
    public void btnLogout(View view){
        Toast.makeText(this, "ovo je novo", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PostsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
