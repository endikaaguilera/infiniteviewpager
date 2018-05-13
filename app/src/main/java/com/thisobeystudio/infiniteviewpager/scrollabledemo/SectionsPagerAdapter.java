package com.thisobeystudio.infiniteviewpager.scrollabledemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.customviewpager.viewpager.CustomIndexHelper;
import com.customviewpager.viewpager.CustomPagerAdapter;

/**
 * A {@link CustomPagerAdapter} that returns a fragment corresponding to one of the pages.
 */
public class SectionsPagerAdapter extends CustomPagerAdapter {

    private final int[] mDemoColors;

    SectionsPagerAdapter(FragmentManager fm, int[] demoColors) {
        super(fm);
        this.mDemoColors = demoColors;
    }

    @Override
    public Fragment getItem(CustomIndexHelper customIndexHelper) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        return PlaceholderFragment.newInstance(customIndexHelper, mDemoColors[customIndexHelper.getDataPosition()]);
    }

    // determines the real number of 'unique' pages, (first and last are duplicated)
    @Override
    public int getRealCount() {
        if (mDemoColors == null) return 0;
        return mDemoColors.length;
    }

}