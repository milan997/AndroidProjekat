package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import adapters.PostAdapter;
import model.Comment;
import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.CommentService;
import services.PostService;
import services.RetrofitObject;


public class ReadPostActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Toolbar toolbar;

    // POst that the activity is showing
    private int postId;
    private Post activityPost;

    private ReadPostFragment rpf;
    private ReadCommentsFragment rcf;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        postId = getIntent().getExtras().getInt("postId");
        //this.activityPost = post;


/*
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
*/
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        /*
        mViewPager.setAdapter(mSectionsPagerAdapter);
*/
        tabLayout = findViewById(R.id.tabs);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    @Override
    protected void onResume() {
        super.onResume();
        PostService postService = RetrofitObject.retrofit.create(PostService.class);
        Call<Post> call = postService.getOne(postId);

        final ProgressDialog progressDialog = Gadgets.getProgressDialog(this);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                progressDialog.dismiss();
                activityPost = response.body();

                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                mViewPager.setAdapter(mSectionsPagerAdapter);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "nije uspijelo", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.makeComment) {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.write_comment, null);

            final EditText etCommentTitle = view.findViewById(R.id.etCommentTitle);
            final EditText etCommentDescription = view.findViewById(R.id.etCommentDescription);

            final AlertDialog commentDialog = new AlertDialog.Builder(this)
                    .setTitle("Comment: ")
                    .setCancelable(true)
                    .setView(view)
                    // Override-ujemo defaultno ponasanje dugmica, dugmici su najbolji bend
                    .setPositiveButton("Finish", null)
                    .setNegativeButton("Cancel", null)
                    .create();

            commentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {

                    Button buttonPositive = commentDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    buttonPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String commentTitle = etCommentTitle.getText().toString();
                            String commentDescription = etCommentDescription.getText().toString();

                            if(commentDescription.isEmpty() || commentTitle.isEmpty()){
                                Toast.makeText(getApplicationContext(), "nis popunio", Toast.LENGTH_SHORT).show();
                            } else {
                                Comment comment = new Comment();
                                comment.setDescription(commentDescription);
                                comment.setTitle(commentTitle);
                                Post post = new Post();
                                post.setId(activityPost.getId());
                                comment.setPost(post);
                                User user = new User();
                                user.setId(getSharedPreferences("sp", MODE_PRIVATE).getInt("userId", 1));
                                comment.setAuthor(user);

                                //uploading comment
                                CommentService commentService = RetrofitObject.retrofit.create(CommentService.class);
                                Call<Comment> call = commentService.createComment(comment);

                                call.enqueue(new Callback<Comment>() {
                                    @Override
                                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                                        Comment comment = response.body();
                                        if (comment == null) {
                                            Toast.makeText(getApplicationContext(), "nesto je poslo po zluu!!! ", Toast.LENGTH_LONG).show();
                                            commentDialog.cancel();
                                            //onResume();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Comment created", Toast.LENGTH_SHORT).show();
                                            commentDialog.cancel();
                                            onResume();
                                            mViewPager.arrowScroll(View.FOCUS_RIGHT);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Comment> call, Throwable t) {
                                        //progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "nije uspjeo", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                    Button buttonNegative = commentDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    buttonNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            commentDialog.cancel();
                        }
                    });
                }
            });
            commentDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        // The post that the adapter is showing
        private Post fragmentPost;
        private ReadPostFragment fragmentRPF;
        private ReadCommentsFragment fragmentRCF;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentPost = activityPost;
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", this.fragmentPost);
            switch (position) {
                case 0:
                    fragmentRPF = new ReadPostFragment();
                    fragmentRPF.setArguments(bundle);
                    return fragmentRPF;
                case 1:
                    fragmentRCF = new ReadCommentsFragment();
                    fragmentRCF.setArguments(bundle);
                    return fragmentRCF;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
