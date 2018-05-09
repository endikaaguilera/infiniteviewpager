package com.thisobeystudio.infiniteviewpager.demo;

/*
 * Created by thisobeystudio on 4/5/18.
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

    private int mCount;

    public SectionsPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.mCount = count;
    }

    @Override
    public Fragment getItem(CustomIndexHelper customIndexHelper) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        return PlaceholderFragment.newInstance(customIndexHelper);
    }

    // determines the real number of 'unique' pages, (first and last are duplicated)
    @Override
    public int getRealCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }
}