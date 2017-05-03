package com.henceforth.rhino.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.henceforth.rhino.fragments.ProfileFragment;
import com.henceforth.rhino.fragments.ServiceProviderFragment;
import com.henceforth.rhino.fragments.NotificationFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ServiceProviderFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new NotificationFragment();
            default:
                return new ServiceProviderFragment();
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}