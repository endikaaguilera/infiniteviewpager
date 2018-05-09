package com.customviewpager.viewpager;

/*
 * Created by thisobeystudio on 8/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Parcel;
import android.os.Parcelable;

// todo add java docs
public class CustomIndexHelper implements Parcelable {

    private final int mPagerPosition;
    private final int mDataPosition;

    public CustomIndexHelper(int pagerPosition, int dataPosition) {
        this.mPagerPosition = pagerPosition;
        this.mDataPosition = dataPosition;
    }

    private CustomIndexHelper(Parcel in) {
        mPagerPosition = in.readInt();
        mDataPosition = in.readInt();
    }

    public static final Creator<CustomIndexHelper> CREATOR = new Creator<CustomIndexHelper>() {
        @Override
        public CustomIndexHelper createFromParcel(Parcel in) {
            return new CustomIndexHelper(in);
        }

        @Override
        public CustomIndexHelper[] newArray(int size) {
            return new CustomIndexHelper[size];
        }
    };

    public int getPagerPosition() {
        return mPagerPosition;
    }

    public int getDataPosition() {
        return mDataPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPagerPosition);
        dest.writeInt(mDataPosition);
    }
}