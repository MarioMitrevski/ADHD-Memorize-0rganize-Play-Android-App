package com.example.myfirstapp.ui.content.relief;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentAudioReliefBinding;
import com.example.myfirstapp.databinding.FragmentVideoReliefBinding;

import java.util.ArrayList;


public class AudioReliefFragment extends Fragment {

    private FragmentAudioReliefBinding binding;
    private AudioAdapter audioAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAudioReliefBinding.inflate(inflater, container, false);
        binding.audioRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        audioAdapter = new AudioAdapter(this.initAudioFiles());
        binding.audioRecyclerView.setAdapter(audioAdapter);

        return binding.getRoot();
    }


    ArrayList<AudioFile> initAudioFiles() {
        ArrayList<AudioFile> audioFiles = new ArrayList<>();
        audioFiles.add(new AudioFile("", "Song 1", "", ""));
        audioFiles.add(new AudioFile("", "Song 2", "", ""));
        audioFiles.add(new AudioFile("", "Song 3", "", ""));
        audioFiles.add(new AudioFile("", "Song 4", "", ""));

        return audioFiles;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}