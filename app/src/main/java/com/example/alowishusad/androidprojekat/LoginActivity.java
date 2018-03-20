package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btnStartsPostsActivity (View view) {
        String userName = ((EditText) findViewById(R.id.userNameInput)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();


        Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
        startActivity(intent);
    }
}
