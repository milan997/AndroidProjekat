package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
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
