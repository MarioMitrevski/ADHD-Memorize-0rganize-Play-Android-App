package com.example.myfirstapp.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.myfirstapp",
                Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        if (email.equals(""))
            startActivity(new Intent(SplashScreenActivity.this,
                    AuthenticationActivity.class));
        else
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}
