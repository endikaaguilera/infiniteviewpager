package com.thisobeystudio.infiniteviewpager.demo;

/*
 * Created by thisobeystudio on 4/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

/**
 * Just a Simple Class that we will use to manage/share data between pages/fragments
 */
public class DemoDataHelper {

    private boolean mCheckBoxChecked;
    private int mScrollPosY;

    public DemoDataHelper() {
        this.mCheckBoxChecked = false;  // set false as default
        this.mScrollPosY = 0;           // set 0 as default
    }

    public boolean isCheckBoxChecked() {
        return mCheckBoxChecked;
    }

    public void setCheckBoxChecked(boolean checkBoxChecked) {
        this.mCheckBoxChecked = checkBoxChecked;
    }

    public void setScrollPosY(int scrollPosY) {
        this.mScrollPosY = scrollPosY;
    }

    public int getScrollPosY() {
        return mScrollPosY;
    }

    /*    @Override
    public String toString() {
        return "DemoDataHelper{" +
                "mCheckBoxChecked='" + mCheckBoxChecked +
                "'," +
                "mScrollPosY='" + mScrollPosY +
                "'}";
    }*/
}
