package com.example.allmankind.rhino.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.adapters.ViewPagerAdapter;

public class ServiceProviderActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //tabLayout.setSelectedTabIndicatorHeight(0);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Creating our pager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Favorites").setIcon(R.drawable.selector_favorite);
        tabLayout.getTabAt(1).setText("History").setIcon(R.drawable.selector_history);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
