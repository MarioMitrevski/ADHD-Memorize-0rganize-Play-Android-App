package com.example.myfirstapp.ui.content.relief;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myfirstapp.databinding.FragmentVideoReliefBinding;

public class VideoReliefFragment extends Fragment {

    private FragmentVideoReliefBinding binding;
    private VideoViewModel videoViewModel;
    private VideoAdapter videoAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoReliefBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        videoViewModel.init();

        videoAdapter = new VideoAdapter(videoViewModel.getVideoItems(), videoItem -> openCustomTab(videoItem.getUrl()));
        binding.recyclerview.setAdapter(videoAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void openCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireActivity(), Uri.parse(url));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}