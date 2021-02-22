package com.example.myfirstapp.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding registerBinding;
    FirebaseUser currentUser;

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
        initClickListeners();
    }


    private void initClickListeners() {
        registerBinding.buttonRegister.setOnClickListener(view -> validateFields());
        registerBinding.showLoginButton.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment));
    }

    public void registerNewUserToFireBase() {
        DatabaseReference newUserRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentUser.getUid());
        User newUser = new User();
        newUser.setNameSurname("");
        newUser.setUserId(currentUser.getUid());
        newUserRef.setValue(newUser);

        Navigation.findNavController(registerBinding.getRoot()).navigate(R.id.action_registerFragment_to_loginFragment);
    }

    public void validateFields() {
        String email = registerBinding.editTextEmail.getText().toString();
        String password = registerBinding.editTextPassword.getText().toString();
        String repeatPassword = registerBinding.editTextRepeatPassword.getText().toString();

        if (checkUserCredentials(email, password)) {
            if (password.equals(repeatPassword)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(),
                                task -> {
                                    if (task.isSuccessful()) {
                                        sendEmailVerification();
                                    } else {
                                        Toast.makeText(getActivity(), "Регистрацијата е неуспешна. Обидете се повторно!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
            } else {
                registerBinding.editTextRepeatPassword.setError("Пасвордите не се исти!");
                Toast.makeText(getActivity(), "Пасвордите не се исти!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Невалидни полиња!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkUserCredentials(String email, String password) {
        return !email.isEmpty() && !password.isEmpty()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendEmailVerification() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null || getActivity() == null)
            return;
        currentUser.sendEmailVerification()
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        registerNewUserToFireBase();
                        Toast.makeText(getActivity(),
                                "Верификациски меил е испратен на " + currentUser.getEmail(),
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(),
                                "Регистрацијата е неуспешна обидете се повторно",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        registerBinding = null;
    }
}
