package com.example.myfirstapp.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.ActivityAuthenticationBinding;

public class AuthenticationActivity extends AppCompatActivity {

    ActivityAuthenticationBinding authenticationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(authenticationBinding.getRoot());
    }

}