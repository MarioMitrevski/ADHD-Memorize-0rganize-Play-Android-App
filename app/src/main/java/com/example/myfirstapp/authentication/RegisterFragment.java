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

import com.example.myfirstapp.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {
    public interface RegisterListener {
        void onRegisterSuccess(String email, String password);
        void toLoginFragment();
    }

    FragmentRegisterBinding registerBinding;
    FirebaseUser currentUser;
    RegisterListener registerListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        registerBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return registerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        setRegisterButtonListener();
        setBackToLoginListener();
    }

    private void setBackToLoginListener() {
        registerBinding.textViewLoginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerListener.toLoginFragment();
            }
        });
    }

    private void setRegisterButtonListener() {
        registerBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    public void registerNewUserToFireBase() {
        DatabaseReference newUserRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentUser.getUid());
        User newUser = new User();
        newUser.setNameSurname("");
        newUserRef.setValue(newUser);
    }

    public void validateFields() {
        if (getActivity() == null)
            return;
        final String email = registerBinding.editTextEmail.getText().toString();
        final String password = registerBinding.editTextPassword
                .getText().toString();
        final String repeatPassword = registerBinding.editTextRepeatPassword
                .getText().toString();
        if (checkUserCredentials(email, password)) {
            if (password.equals(repeatPassword)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            sendEmailVerification();
                                            registerNewUserToFireBase();
                                            registerListener.onRegisterSuccess(email, password);
                                        } else {
                                            Toast.makeText(getActivity(), "Authentication fail!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
            } else {
                registerBinding.editTextRepeatPassword.setError("Password don't match!");
                Toast.makeText(getActivity(), "Incompatible password matching!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Invalidate Input!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkUserCredentials(String email, String password) {
        return !email.equals("") && !password.equals("")
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendEmailVerification() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null || getActivity() == null)
            return;
        currentUser.sendEmailVerification()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),
                                    "Verification email sent to " + currentUser.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegisterListener)
            registerListener = (RegisterListener) context;
        else
            throw new ClassCastException(context.toString() + " must implement RegisterListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        registerListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        registerBinding = null;
    }
}
