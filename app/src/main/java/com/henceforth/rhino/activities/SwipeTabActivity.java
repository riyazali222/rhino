package com.henceforth.rhino.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.dialoges.SettingDialogFragment;
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
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

       /* tabLayout.getTabAt(0).setText(R.string.raise_request).setIcon(R.drawable.selector_service);
        *//*TextView txt1= (TextView)tabLayout.getChildAt(0).findViewById(android.R.id.TextView1);
        txt1.setTextSize(25);*//*
        tabLayout.getTabAt(1).setText(R.string.profile).setIcon(R.drawable.ic_user);
        tabLayout.getTabAt(2).setText(R.string.notification).setIcon(R.drawable.selector_notification);*/
    }
    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.raise_request);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_service, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.profile);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_user, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.notification);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.selector_notification, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ENABLE_LOCATION)
            sendBroadcast(new Intent("LocationEnableRequest"));
    }

    @Override
    public void onClick(View v) {

        CommonMethods.showDialogFragmentFullScreen(SwipeTabActivity.this,new SettingDialogFragment(),"settings");

    }
}
