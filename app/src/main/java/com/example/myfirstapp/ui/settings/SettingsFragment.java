package com.example.myfirstapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.authentication.AuthenticationActivity;
import com.example.myfirstapp.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickListeners();
    }

    private void initClickListeners() {
        binding.buttonLogout.setOnClickListener(view -> {
            getActivity().getSharedPreferences("com.example.myfirstapp.userstore",
                    Context.MODE_PRIVATE).edit().clear().apply();
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(requireContext(), "Успешно се одјавивте!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(requireContext(), AuthenticationActivity.class));
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}