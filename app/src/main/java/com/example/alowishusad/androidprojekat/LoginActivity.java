package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.RetrofitObject;
import services.UserService;

public class LoginActivity extends AppCompatActivity {

    //Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading default preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void btnStartsPostsActivity (View view) throws IOException {
        final String userName = ((EditText) findViewById(R.id.userNameInput)).getText().toString().trim();
        final String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString().trim();

        UserService userService = RetrofitObject.retrofit.create(UserService.class);
        Call<User> call = userService.checkLogin(userName, password);

        // ProgressDialog
        final ProgressDialog progressDialog = Gadgets.getProgressDialog(this);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss();
                User user = response.body();
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "Neuspjelo logovanje!!! ", Toast.LENGTH_LONG).show();
                } else {
                    // Zapisati u sharedPref
                    SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt("userId", user.getId());
                    editor.putString("username", user.getUsername());
                    editor.commit();
                    // Predji na posts stranicu
                    Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "ERROR FETCHING DATA!!! ", Toast.LENGTH_LONG).show();
            }
        });

        /// OVO MENJATI
        /*
        UserService userServiceInterface = RetrofitObject.retrofit.create(UserService.class);
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
        */



    }
}
