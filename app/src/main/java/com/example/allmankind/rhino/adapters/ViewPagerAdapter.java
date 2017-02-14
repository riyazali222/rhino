package com.example.allmankind.rhino.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.allmankind.rhino.fragments.FavoriteFragment;
import com.example.allmankind.rhino.fragments.HistoryFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FavoriteFragment();
            case 1:
                return new HistoryFragment();
            default:
                return new FavoriteFragment();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}