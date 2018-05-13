package com.thisobeystudio.infiniteviewpager.scrollabledemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.customviewpager.viewpager.CustomViewPager;
import com.thisobeystudio.infiniteviewpager.R;

import static com.customviewpager.indicator.CustomIndicator.MODE_CLAMPED_HEIGHT;
import static com.customviewpager.indicator.CustomIndicator.POSITION_FLOAT_BOTTOM;
import static com.thisobeystudio.infiniteviewpager.demodata.DemoDataManager.getDemoColors;

public class ScrollableActivity extends AppCompatActivity {

    private final static String TAG = ScrollableActivity.class.getSimpleName();

    public CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demos);

        setTitle(TAG);

        initCustomViewPager();
    }

    private void initCustomViewPager() {
        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager(), getDemoColors());

        // The CustomViewPager that will host the section contents.
        mViewPager = findViewById(R.id.custom_view_pager);

        // Enable helper pages callbacks to share scroll posY
        mViewPager.useHelpersCallbacks(true);

        // Set up the CustomViewPager with the sections adapter.
        mViewPager.setAdapter(sectionsPagerAdapter);

        // Indicators must be initialized before set the initial CustomViewPager current item.
        mViewPager.initIndicators(this, POSITION_FLOAT_BOTTOM, MODE_CLAMPED_HEIGHT);

        // Set initial selection
        mViewPager.setCurrentItem(0);
    }
}
