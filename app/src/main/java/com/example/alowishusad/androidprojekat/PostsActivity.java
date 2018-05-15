package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import java.util.List;

import adapters.PostAdapter;
import model.Data;
import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.RetrofitObject;

public class PostsActivity extends AppCompatActivity  /*implements NavigationView.OnNavigationItemSelectedListener  */{

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


        // OVO RADI DO DOLE GADJAM USERE
        /*
        UserService userService = RetrofitObject.retrofit.create(UserService.class);
        Call<List<User>> call = userService.getAll();
        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> users, Response<List<User>> response){
                List <User> us = new ArrayList<>();
                try {
                    us = response.body();
                } catch (Exception e) { e.printStackTrace(); }
                Toast.makeText(getApplicationContext(), us.get(0).getUsername(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<User>> users ,Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR FETCHING DATA!!!", Toast.LENGTH_LONG).show();
            }
        });
        */
        // OVO SVE FUL GORE

        // OVO JE SAMO JEDAN OBJEKAT PRIMA
        /*
        UserService userService = RetrofitObject.retrofit.create(UserService.class);
        Call<User> call = userService.getOne(3);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> users, Response<User> response){
                User us = null;
                try {
                    us = response.body();
                } catch (Exception e) { e.printStackTrace(); }
                Toast.makeText(getApplicationContext(), (us==null)?"user nije nadjen":us.getUsername(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> user ,Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR FETCHING DATA!!!", Toast.LENGTH_LONG).show();
            }
        });
        */

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
