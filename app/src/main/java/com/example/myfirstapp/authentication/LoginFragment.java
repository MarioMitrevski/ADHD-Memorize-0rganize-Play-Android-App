package com.example.myfirstapp.authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    interface LoginListener {
        void onAuthenticationSuccess(String email, String password);

        void toRegisterFragment();
    }

    FragmentLoginBinding loginBinding;
    LoginListener loginListener;
    String email;
    String password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoginButtonListener();
        setBackToLoginListener();
    }

    private void setBackToLoginListener() {
      loginBinding.textViewRegisterLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              loginListener.toRegisterFragment();
          }
      });
    }

    private void setLoginButtonListener() {
        loginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    public void validateFields() {
        email = loginBinding.editTextLoginEmail.getText().toString();
        password = loginBinding.editTextLoginPassword.getText().toString();
        if (checkUserCredentials(email, password)) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                signInUserWithEmailAndPassword();
            } else {
                currentUser.reload();
                if (currentUser.isEmailVerified()) {
                    signInUserWithEmailAndPassword();
                } else {
                    Toast.makeText(getActivity(), "Check email to verify!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            loginBinding.editTextLoginEmail.setError("Please fill all fields correctly!");
            Toast.makeText(getActivity(), "Invalidate Input!", Toast.LENGTH_SHORT).show();
        }
    }

    public void signInUserWithEmailAndPassword() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loginListener.onAuthenticationSuccess(email, password);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Authentication fail!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkUserCredentials(String email, String password) {
        return !email.equals("") && !password.equals("")
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener)
            loginListener = (LoginListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement Login Listener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loginBinding = null;
    }
}
