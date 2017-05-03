package com.henceforth.rhino.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.SettingDialogFragment;
import com.henceforth.rhino.adapters.ViewPagerAdapter;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;

public class SwipeTabActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    ImageView ivSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_tab);
        ivSetting = (ImageView) findViewById(R.id.ivSetting);
        ivSetting.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Creating our pager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.raise_request).setIcon(R.drawable.selector_service);
        tabLayout.getTabAt(1).setText(R.string.profile).setIcon(R.drawable.ic_user);
        tabLayout.getTabAt(2).setText(R.string.notification).setIcon(R.drawable.selector_notification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ENABLE_LOCATION)
            sendBroadcast(new Intent("LocationEnableRequest"));
    }

    @Override
    public void onClick(View v) {

        DialogFragment dialog = SettingDialogFragment.newInstance();
        CommonMethods.showDialogFragmentFullScreen((AppCompatActivity)SwipeTabActivity.this,dialog,"Tag");

    }
}
