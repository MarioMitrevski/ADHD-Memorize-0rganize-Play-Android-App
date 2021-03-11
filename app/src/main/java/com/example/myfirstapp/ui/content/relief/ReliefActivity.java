package com.example.myfirstapp.ui.content.relief;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.view.View;


import com.example.myfirstapp.databinding.ActivityReliefBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class ReliefActivity extends AppCompatActivity {

    private ActivityReliefBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReliefBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbarText.setText("Меморија");
        binding.backBtn.setOnClickListener(v -> finish());

        initReliefViewPager();

    }

    private void initReliefViewPager() {
        ViewPager2 reliefViewPager = binding.reliefViewPager;
        TabLayout reliefTabLayout = binding.reliefTabLayout;
        ViewPagerAdapter reliefViewPagerAdapter = new ViewPagerAdapter(this);
        reliefViewPagerAdapter.addFragment(new AudioReliefFragment(), "Аудио");
        reliefViewPagerAdapter.addFragment(new VideoReliefFragment(), "Видео");
        reliefViewPager.setAdapter(reliefViewPagerAdapter);
        new TabLayoutMediator(reliefTabLayout, reliefViewPager, ((tab, position) -> {
            tab.setText(reliefViewPagerAdapter.titles.get(position));
        })).attach();

    }

    public static class ViewPagerAdapter extends FragmentStateAdapter{

        private ArrayList<Fragment> fragments;
        ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }


        void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}