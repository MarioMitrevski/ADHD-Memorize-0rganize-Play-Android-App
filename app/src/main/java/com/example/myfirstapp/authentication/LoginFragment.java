package com.example.myfirstapp.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentLoginBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding loginBinding;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.sharedPreferences =
                getActivity().getSharedPreferences("com.example.myfirstapp.userstore", Context.MODE_PRIVATE);

        initClickListeners();
    }

    private void initClickListeners() {
        loginBinding.registerButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));
        loginBinding.buttonLogin.setOnClickListener(view -> validateFields());
    }

    public void validateFields() {
        String email = loginBinding.editTextLoginEmail.getText().toString();
        String password = loginBinding.editTextLoginPassword.getText().toString();

        if (checkUserCredentials(email, password)) {
            signInUserWithEmailAndPassword(email, password);
        } else {
            Toast.makeText(getActivity(), "Пополнете ги полињата соодветно!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInUserWithEmailAndPassword(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user.isEmailVerified()) {
                        navigateToMainScreen(email, password);
                    } else {
                        Toast.makeText(getActivity(), "Проверете ја меил адресата за да ја завршите регистрацијата!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Автентикацијата е неуспешна!", Toast.LENGTH_SHORT).show());
    }

    private void navigateToMainScreen(String email, String password) {
        sharedPreferences.edit().putString("email", email).apply();
        sharedPreferences.edit().putString("password", password).apply();
        Intent homeIntent = new Intent(requireContext(), MainActivity.class);
        startActivity(homeIntent);
        requireActivity().finish();
    }

    public boolean checkUserCredentials(String email, String password) {
        return !email.isEmpty() && !password.isEmpty()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginBinding = null;
    }
}
