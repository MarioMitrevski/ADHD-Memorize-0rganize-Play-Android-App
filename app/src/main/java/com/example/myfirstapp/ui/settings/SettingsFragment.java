package com.example.myfirstapp.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.authentication.AuthenticationActivity;
import com.example.myfirstapp.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import static android.content.Context.AUDIO_SERVICE;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private FirebaseUser currentUser;
    private DatabaseReference nicknameDatabaseRef;
    private SharedPreferences sharedPreferences;

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

        setCheckedSwitch();
    }

    private void setCheckedSwitch() {
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

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void muteOrUnmute(int adjust) {
        //mute audio
        if (getActivity() == null)
            return;
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, adjust, 0);
    }
}