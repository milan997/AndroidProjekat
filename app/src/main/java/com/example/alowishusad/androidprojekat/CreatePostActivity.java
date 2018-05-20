package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.PostService;
import services.RetrofitObject;


public class CreatePostActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
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
        } else if (id == R.id.btnConfirm) {
            createPost();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createPost() {
        TextView tvTitle = findViewById(R.id.etTitle);
        TextView tvDescription = findViewById(R.id.etDescription);

        String title = tvTitle.getEditableText().toString();
        String description = tvDescription.getEditableText().toString();

        if(title.isEmpty() || description.isEmpty()){
            Gadgets.showSimpleOkDialog(this, "All fields are mandatory!!!");
            return;
        }

        Post post = new Post();
        User user = new User();
        user.setId(getSharedPreferences("sp", MODE_PRIVATE).getInt("userId", 1));
        post.setTitle(title);
        post.setDescription(description);
        post.setAuthor(user);

        PostService postService = RetrofitObject.retrofit.create(PostService.class);
        Call<Post> call = postService.createPost(post);
        // ProgressDialog
        final ProgressDialog progressDialog = Gadgets.getProgressDialog(this);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                progressDialog.dismiss();
                Post post = response.body();
                if (post == null) {
                    Toast.makeText(getApplicationContext(), "nesto je poslo po zluu!!! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Post created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), PostsActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "greska prilikom kreiranja posta ", Toast.LENGTH_LONG).show();
            }
        });
    }


    // Function to be called when clicked on "photo" button
    public void btnOpenCamera(View view) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Checking if there's at least one camera app
        if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCameraIntent, 1);
        }
    }

    public void btnOpenGallery(View view) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (openGalleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openGalleryIntent, 1);
        }
    }
}
