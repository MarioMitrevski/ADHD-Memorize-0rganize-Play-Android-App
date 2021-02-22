package com.example.myfirstapp.ui.content.memory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.databinding.FragmentMemoryBinding;

public class MemoryFragment extends Fragment {

    private FragmentMemoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbarText.setText("Меморија");
        binding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}