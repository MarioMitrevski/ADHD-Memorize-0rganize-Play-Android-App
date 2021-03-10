package com.example.myfirstapp.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.authentication.SplashScreenActivity;
import com.example.myfirstapp.databinding.FragmentContentBinding;
import com.example.myfirstapp.ui.content.relief.ReliefActivity;

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

        binding.plannerCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_contentFragment_to_calendarFragment));
        binding.memoryCardView.setOnClickListener(v -> startActivity(new Intent(requireActivity(),ReliefActivity.class)));
        binding.mathCardView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_contentFragment_to_mathFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}