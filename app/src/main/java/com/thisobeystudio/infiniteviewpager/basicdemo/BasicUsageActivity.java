package com.thisobeystudio.infiniteviewpager.basicdemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.customviewpager.viewpager.CustomViewPager;
import com.thisobeystudio.infiniteviewpager.R;

import static com.thisobeystudio.infiniteviewpager.demodata.DemoDataManager.getDemoColors;

public class BasicUsageActivity extends AppCompatActivity {

    private final static String TAG = BasicUsageActivity.class.getSimpleName();

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
        CustomViewPager viewPager = findViewById(R.id.custom_view_pager);
        // Set up the CustomViewPager with the sections adapter.
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.setCurrentItem(0);
    }
}
