package com.thisobeystudio.infiniteviewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT = 1f;
    private static final float MIN_SCALE = 0.90f;
    private static final float MIN_ALPHA = 0.6f;

    public void transformPage(@NonNull View view, float position) {
        int width = view.getWidth();
        int height = view.getHeight();
        // Position of page relative to the current front-and-center position of the pager.
        // 0 is front and center.
        // 1 is one full page position to the right
        // -1 is one page position to the left.

        if (position == 0 || position > 1f || position < -1f) {
            view.setScaleX(DEFAULT);
            view.setScaleY(DEFAULT);
            view.setAlpha(DEFAULT);
        } else {

            float scaleFactor = Math.max(MIN_SCALE, DEFAULT - Math.abs(position));
            float marginV = height * (DEFAULT - scaleFactor) / 2;
            float marginH = width * (DEFAULT - scaleFactor) / 2;

            if (position < 0) {
                final float posX = marginH - marginV / 2;
                view.setTranslationX(posX);
            } else if (position > 0) {
                final float posX = -marginH + marginV / 2;
                view.setTranslationX(posX);
            } else {
                scaleFactor = 1;
                view.setTranslationX(0);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            float alpha = MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (DEFAULT - MIN_SCALE) *
                            (DEFAULT - MIN_ALPHA);
            view.setAlpha(alpha);
        }
    }
}
