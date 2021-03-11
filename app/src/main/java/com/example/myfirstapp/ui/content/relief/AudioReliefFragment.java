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
        audioAdapter = new AudioAdapter(this.initAudioFiles(), audioFile ->
                startActivity(new Intent(requireActivity(), PlayerActivity.class)
                        .putExtra("audioFile", audioFile)));
        binding.audioRecyclerView.setAdapter(audioAdapter);

        return binding.getRoot();
    }


    ArrayList<AudioFile> initAudioFiles() {
        ArrayList<AudioFile> audioFiles = new ArrayList<>();
        audioFiles.add(new AudioFile("Enjoy the sunset", "Sunny Artists", R.raw.adhd_audio_1, R.drawable.relax_yoga_1));
        audioFiles.add(new AudioFile("Chill wit the wind", "Wind Artists", R.raw.adhd_audio_2, R.drawable.ic_adhd_audio_cover2));
        audioFiles.add(new AudioFile("Nature Queen", "LoFi HiGroup", R.raw.adhd_audio_3, R.drawable.ic_adhd_audio_cover3));
        audioFiles.add(new AudioFile("Sound of the Sea", "Duke Dumont", R.raw.adhd_audio_4, R.drawable.ic_adhd_audio_cover4));
        audioFiles.add(new AudioFile("Ocean Drive", "GrooveGroup", R.raw.adhd_audio_5, R.drawable.ic_adhd_audio_cover5));
        audioFiles.add(new AudioFile("Here Comes the Sun", "The Beatles", R.raw.adhd_audio_6, R.drawable.ic_adhd_audio_cover6));

        return audioFiles;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}