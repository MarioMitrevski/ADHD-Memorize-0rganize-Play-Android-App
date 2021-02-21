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

public class AuthenticationActivity extends AppCompatActivity implements
        LoginFragment.LoginListener, RegisterFragment.RegisterListener {
    ActivityAuthenticationBinding authenticationBinding;
    FragmentManager fragmentManager;
    RegisterFragment registerFragment;
    LoginFragment loginFragment;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(authenticationBinding.getRoot());
        initializeFragments();
    }

    private void initializeFragments() {
        fragmentManager = getSupportFragmentManager();
        loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_login);
        registerFragment = new RegisterFragment();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout_register, registerFragment)
                .hide(registerFragment)
                .commit();
        sharedPreferences = getSharedPreferences("com.example.myfirstapp",
                Context.MODE_PRIVATE);
    }

    @Override
    public void onAuthenticationSuccess(String email, String password) {
        sharedPreferences.edit().putString("email", email).apply();
        sharedPreferences.edit().putString("password", password).apply();
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void toRegisterFragment() {
        fragmentManager.beginTransaction().hide(loginFragment).show(registerFragment).commit();
    }

    @Override
    public void onRegisterSuccess(String email, String password) {
        loginFragment.loginBinding.editTextLoginEmail.setText(email);
        loginFragment.loginBinding.editTextLoginPassword.setText(password);
        fragmentManager.beginTransaction().hide(registerFragment).show(loginFragment).commit();
    }

    @Override
    public void toLoginFragment() {
        fragmentManager.beginTransaction().hide(registerFragment).show(loginFragment).commit();
    }
}