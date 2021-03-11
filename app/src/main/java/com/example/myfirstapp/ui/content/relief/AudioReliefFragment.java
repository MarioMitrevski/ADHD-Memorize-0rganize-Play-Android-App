package com.example.myfirstapp.ui.content.relief;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentAudioReliefBinding;
import com.example.myfirstapp.network.AudioFile;
import com.example.myfirstapp.network.AudioItemsCollection;

import java.util.ArrayList;


public class AudioReliefFragment extends Fragment {

    private FragmentAudioReliefBinding binding;
    private AudioAdapter audioAdapter;

    private AudioItemsCollection audioItemsCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAudioReliefBinding.inflate(inflater, container, false);

        audioItemsCollection = new AudioItemsCollection();
        binding.audioRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        audioAdapter = new AudioAdapter(audioItemsCollection.getAudioFiles(), audioFile ->
                startActivity(new Intent(requireActivity(), PlayerActivity.class)
                        .putExtra("audioFile", audioFile)));
        binding.audioRecyclerView.setAdapter(audioAdapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}