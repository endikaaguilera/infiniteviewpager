package com.thisobeystudio.infiniteviewpager.complexdemo;

/*
 * Created by thisobeystudio on 13/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Parcel;
import android.os.Parcelable;

public class ComplexDataHelper implements Parcelable {

    private int posY;
    private float rating;
    private final int color;

    public ComplexDataHelper(int posY, float rating, int color) {
        this.posY = posY;
        this.rating = rating;
        this.color = color;
    }

    private ComplexDataHelper(Parcel in) {
        posY = in.readInt();
        rating = in.readFloat();
        color = in.readInt();
    }

    public static final Creator<ComplexDataHelper> CREATOR = new Creator<ComplexDataHelper>() {
        @Override
        public ComplexDataHelper createFromParcel(Parcel in) {
            return new ComplexDataHelper(in);
        }

        @Override
        public ComplexDataHelper[] newArray(int size) {
            return new ComplexDataHelper[size];
        }
    };

    int getPosY() {
        return posY;
    }

    void setPosY(int posY) {
        this.posY = posY;
    }

    float getRating() {
        return rating;
    }

    void setRating(float rating) {
        this.rating = rating;
    }

    public int getColor() {
        return color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(posY);
        dest.writeFloat(rating);
        dest.writeInt(color);
    }
}
