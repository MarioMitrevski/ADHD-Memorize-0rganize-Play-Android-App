package com.example.myfirstapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.authentication.AuthenticationActivity;
import com.example.myfirstapp.authentication.SplashScreenActivity;
import com.example.myfirstapp.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

public class SettingsFragment extends Fragment {

    public interface SettingsStateListener {
        void onSoundChange();

        void onThemeChange();
    }

    public static FragmentSettingsBinding binding;
    private FirebaseUser currentUser;
    private DatabaseReference nicknameDatabaseRef;
    private SharedPreferences sharedPreferences;
    private SettingsStateListener settingsStateListener;

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
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        setInitialNickname();
        initClickListeners();
        sharedPreferences =
                getActivity().getSharedPreferences("com.example.myfirstapp.userstore", Context.MODE_PRIVATE);
        setCheckedSoundSwitch();
        setCheckedDarkThemeSwitch();
    }

    private void setCheckedDarkThemeSwitch() {
        binding.switchEnableDarkTheme.setChecked(sharedPreferences.getBoolean("darkTheme", false));
    }

    private void setCheckedSoundSwitch() {
        binding.switchEnableSound.setChecked(sharedPreferences.getBoolean("soundOn", true));
    }

    private void setNicknameChangeListener() {
        binding.editTextNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(binding.editTextNickname.getText().toString().trim())) {
                    binding.imageViewEditNicknameIcon.setImageResource(R.drawable.ic_save_foreground);
                } else {
                    binding.imageViewEditNicknameIcon.setImageResource(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setInitialNickname() {
        nicknameDatabaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(currentUser.getUid()).child("nickname");
        nicknameDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isVisible()) {
                    binding.editTextNickname.setText(String.valueOf(snapshot.getValue()));
                    setNicknameChangeListener();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        binding.imageViewEditNicknameIcon.setOnClickListener(view -> {
            if (binding.imageViewEditNicknameIcon.getDrawable().getConstantState() ==
                    getResources().getDrawable(R.drawable.ic_save_foreground).getConstantState()) {
                nicknameDatabaseRef.setValue(binding.editTextNickname.getText().toString());
                binding.imageViewEditNicknameIcon.setImageResource(R.drawable.ic_pencil_foreground);
                binding.editTextNickname.setCursorVisible(false);
                if (getActivity() == null)
                    return;
                UIUtil.hideKeyboard(getActivity());
            } else if (binding.imageViewEditNicknameIcon.getDrawable().getConstantState() ==
                    getResources().getDrawable(R.drawable.ic_pencil_foreground).getConstantState()) {
                binding.editTextNickname.requestFocus();
                binding.editTextNickname.setSelection(binding.editTextNickname.getText().length());
            }
        });

        binding.editTextNickname.setOnClickListener(view -> binding.editTextNickname.setCursorVisible(true));

        binding.switchEnableSound.setOnClickListener(view -> {
            if (binding.switchEnableSound.isChecked()) {
                sharedPreferences.edit().putBoolean("soundOn", true).apply();
            } else {
                sharedPreferences.edit().putBoolean("soundOn", false).apply();
            }
            settingsStateListener.onSoundChange();
        });

        binding.switchEnableDarkTheme.setOnClickListener(view -> {
            if (isVisible()) {
                if (binding.switchEnableDarkTheme.isChecked()) {
                    sharedPreferences.edit().putBoolean("darkTheme", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("darkTheme", false).apply();
                }
                settingsStateListener.onThemeChange();
                Intent intent = new Intent(getActivity().getApplicationContext(), SplashScreenActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof SettingsStateListener) {
            settingsStateListener = (SettingsStateListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement SettingsSoundStateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        settingsStateListener = null;
    }
}