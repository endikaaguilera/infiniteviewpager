package com.thisobeystudio.infiniteviewpager.demodata;

import android.graphics.Color;

import com.thisobeystudio.infiniteviewpager.complexdemo.ComplexDataHelper;

/**
 * Created by thisobeystudio on 12/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */
public class DemoDataManager {

    private final static ComplexDataHelper[] mComplexData = new ComplexDataHelper[]{
            new ComplexDataHelper(0, 0, Color.parseColor("#F44336")),
            new ComplexDataHelper(0, 0, Color.parseColor("#9C27B0")),
            new ComplexDataHelper(0, 0, Color.parseColor("#3F51B5")),
            new ComplexDataHelper(0, 0, Color.parseColor("#03A9F4")),
            new ComplexDataHelper(0, 0, Color.parseColor("#009688")),
            new ComplexDataHelper(0, 0, Color.parseColor("#8BC34A")),
            new ComplexDataHelper(0, 0, Color.parseColor("#FFEB3B")),
            new ComplexDataHelper(0, 0, Color.parseColor("#FF5722"))
    };

    public static ComplexDataHelper[] getComplexDemoData() {
        return mComplexData;
    }

    private final static int[] mDemoColors = new int[]{
            Color.parseColor("#F44336"),
//            Color.parseColor("#E91E63"),
            Color.parseColor("#9C27B0"),
//            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
//            Color.parseColor("#2196F3"),
            Color.parseColor("#03A9F4"),
//            Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"),
//            Color.parseColor("#4CAF50"),
            Color.parseColor("#8BC34A"),
//            Color.parseColor("#CDDC39"),
            Color.parseColor("#FFEB3B"),
//            Color.parseColor("#FFC107"),
            Color.parseColor("#FF5722")
    };

    public static int[] getDemoColors() {
        return mDemoColors;
    }

    public static int getDarkerColor(int baseColor) {
        float[] hsv = new float[3];
        int color = baseColor;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.6f;
        color = Color.HSVToColor(hsv);
        return color;
    }
}
