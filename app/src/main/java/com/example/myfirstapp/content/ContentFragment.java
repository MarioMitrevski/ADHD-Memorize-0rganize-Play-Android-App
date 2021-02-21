package com.example.myfirstapp.content;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.authentication.AuthenticationActivity;
import com.example.myfirstapp.authentication.RegisterFragment;
import com.example.myfirstapp.databinding.FragmentContentBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ContentFragment extends Fragment {
    public interface OnLogoutListener {
        void onLogout();
    }

    private FragmentContentBinding binding;
    OnLogoutListener onLogoutListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLogoutListener();
    }

    private void setLogoutListener() {
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSharedPreferences("com.example.myfirstapp",
                        Context.MODE_PRIVATE).edit().clear().apply();
                FirebaseAuth.getInstance().signOut();
                onLogoutListener.onLogout();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLogoutListener)
            onLogoutListener = (OnLogoutListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement OnLogoutListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLogoutListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}