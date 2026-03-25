package com.example.moviebookingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.moviebookingapp.ComingSoonFragment;
import com.example.moviebookingapp.NowShowingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new NowShowingFragment();
        } else {
            return new ComingSoonFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}