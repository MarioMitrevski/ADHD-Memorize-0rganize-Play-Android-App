package com.example.myfirstapp.ui.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentContentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContentFragment extends Fragment {

    private FragmentContentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUsersNickname();
        binding.plannerCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_contentFragment_to_calendarFragment));
        binding.memoryCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_contentFragment_to_memoryFragment));
        binding.mathCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_contentFragment_to_mathFragment));
    }

    private void setUsersNickname() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersNicknameDatabaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(currentUser.getUid()).child("nickname");
        usersNicknameDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.textView.setText(String.format("%s%s", getString(R.string.welcome_message),
                        snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}