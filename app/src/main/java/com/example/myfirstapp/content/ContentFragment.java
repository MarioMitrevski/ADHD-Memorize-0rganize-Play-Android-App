package com.example.myfirstapp.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentContentBinding;

public class ContentFragment extends Fragment {

    private FragmentContentBinding contentBinding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        contentBinding = FragmentContentBinding.inflate(inflater, container, false);
        return contentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        setStartMathGameListener();
    }

    private void setStartMathGameListener() {
        contentBinding.mathCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_contentFragment_to_mathFragment);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentBinding = null;
    }
}