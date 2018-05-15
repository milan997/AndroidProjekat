package com.example.alowishusad.androidprojekat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.RetrofitObject;
import services.UserService;
import services.UserServiceInterface;

public class LoginActivity extends AppCompatActivity {

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void btnStartsPostsActivity (View view) {
        final String userName = ((EditText) findViewById(R.id.userNameInput)).getText().toString().trim();
        final String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString().trim();

        /// OVO MENJATI
        UserServiceInterface userServiceInterface = RetrofitObject.retrofit.create(UserServiceInterface.class);
        Call<List<User>> call = userServiceInterface.getAll();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> users, Response<List<User>> response) {
                try {
                    // TODO resi pa ono
                    boolean login = false;
                    for(User user : response.body()){
                        if(user.getUsername().equals(userName) && user.getPassword().equals(password)){
                            login = true;
                            break;
                        }
                    }
                    if(login==true){
                        // Upisat u sharedPreferences

                        Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "ne valja", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<User>> users, Throwable t) {
                //Toast.makeText(getApplicationContext(), "ERROR FETCHING DATA!!!", Toast.LENGTH_LONG).show();
            }
        });



    }
}
