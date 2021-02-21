package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myfirstapp.authentication.AuthenticationActivity;
import com.example.myfirstapp.content.ContentFragment;
import com.example.myfirstapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ContentFragment.OnLogoutListener {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onLogout() {
        Intent loginIntent = new Intent(this, AuthenticationActivity.class);
        Toast.makeText(MainActivity.this, "Успешно се одјавивте!", Toast.LENGTH_LONG).show();
        startActivity(loginIntent);
        finish();
    }
}
