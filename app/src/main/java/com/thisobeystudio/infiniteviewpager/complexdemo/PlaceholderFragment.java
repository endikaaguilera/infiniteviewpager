package com.thisobeystudio.infiniteviewpager.complexdemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.customviewpager.viewpager.CustomFragment;
import com.customviewpager.viewpager.CustomIndexHelper;
import com.customviewpager.viewpager.CustomViewPager;
import com.thisobeystudio.infiniteviewpager.R;

import static com.thisobeystudio.infiniteviewpager.demodata.DemoDataManager.getDarkerColor;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends CustomFragment {
    /**
     * The fragment argument representing the section for this fragment.
     */
    private static final String ARG_COMPLEX_DEMO_DATA = "complex_demo_data";

    private NestedScrollView mNestedView;
    private AppCompatRatingBar mRatingBar;

    private ComplexDataHelper mComplexDataHelper;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     */
    public static PlaceholderFragment newInstance(
            CustomIndexHelper customIndexHelper,
            ComplexDataHelper demoColor) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CUSTOM_INDEX_HELPER, customIndexHelper); // fixme this is important!! ARG_CUSTOM_INDEX_HELPER
        args.putParcelable(ARG_COMPLEX_DEMO_DATA, demoColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_complex, container, false);

        if (getArguments() == null ||
                getActivity() == null ||
                !getArguments().containsKey(ARG_CUSTOM_INDEX_HELPER) ||
                !getArguments().containsKey(ARG_COMPLEX_DEMO_DATA)) return rootView;

        setCustomIndexHelper(getArguments());        // fixme this is important!!

        mComplexDataHelper = getArguments().getParcelable(ARG_COMPLEX_DEMO_DATA);

        if (mCustomIndexHelper == null || mComplexDataHelper == null) return rootView;

        int color = mComplexDataHelper.getColor();

        int darkerColor = getDarkerColor(color);
        rootView.setBackgroundColor(darkerColor);

        CardView cardView = rootView.findViewById(R.id.demo_card_view);
        cardView.setCardBackgroundColor(color);

        TextView pageIndexLabel = rootView.findViewById(R.id.section_page_index_label);
        String pagerPositionString = String.valueOf(getPageIndex());
        pageIndexLabel.setText(pagerPositionString);

        TextView dataIndexLabel = rootView.findViewById(R.id.section_data_index_label);
        String dataPositionString = String.valueOf(getDataIndex());
        dataIndexLabel.setText(dataPositionString);
        dataIndexLabel.setTextColor(darkerColor);

        // handle data if we are on first or last page (helpers includes)
        if (isRealFirst() || isRealLast() || isHelperFirst() || isHelperLast()) {

            final CustomViewPager viewPager = ((ComplexActivity) getActivity()).mViewPager;

            if (viewPager == null) return rootView;

            mRatingBar = rootView.findViewById(R.id.rating_bar);
            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (isRealFirst()) {
                        mComplexDataHelper.setRating(rating);
                        viewPager.setFirstPageDataCallbacks(PlaceholderFragment.this);
                    }
                    if (isRealLast()) {
                        mComplexDataHelper.setRating(rating);
                        viewPager.setLastPageDataCallbacks(PlaceholderFragment.this);
                    }
                }
            });

            mNestedView = rootView.findViewById(R.id.demo_scroll_view);
            mNestedView.setOnScrollChangeListener(
                    new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v,
                                                   int scrollX, int scrollY,
                                                   int oldScrollX, int oldScrollY) {
                            if (isRealFirst()) {
                                mComplexDataHelper.setPosY(scrollY);
                                viewPager.setFirstPageDataCallbacks(PlaceholderFragment.this);
                            }
                            if (isRealLast()) {
                                mComplexDataHelper.setPosY(scrollY);
                                viewPager.setLastPageDataCallbacks(PlaceholderFragment.this);
                            }
                        }
                    });

            isHelperFirst(viewPager);
            isHelperLast(viewPager);
        }

        return rootView;
    }

    @Override
    public Object getPageData() {
        if (isRealFirst() || isRealLast()) {
            return mComplexDataHelper;
        }
        return null;
    }

    @Override
    public void setHelperPageData(Object data) {
        if (data == null) return;

        if (isHelperFirst() || isHelperLast()) mComplexDataHelper = ((ComplexDataHelper) data);

        if (mRatingBar != null) mRatingBar.setRating(mComplexDataHelper.getRating());
        if (mNestedView != null) mNestedView.setScrollY(mComplexDataHelper.getPosY());
    }

    private void isHelperFirst(CustomViewPager viewPager) {

        if (!isHelperFirst() || viewPager == null || mNestedView == null || mRatingBar == null)
            return;

        viewPager.setFirstPageDataCallbacks(this);
        Object data = viewPager.getFirstPageData();
        if (data == null) return;
        mComplexDataHelper = (ComplexDataHelper) data;
        viewPager.post(new Runnable() {
            @Override
            public void run() {

                mRatingBar.setRating(mComplexDataHelper.getRating());
                mNestedView.setScrollY(mComplexDataHelper.getPosY());
            }
        });

    }

    private void isHelperLast(CustomViewPager viewPager) {

        if (!isHelperLast() || viewPager == null || mNestedView == null || mRatingBar == null)
            return;

        viewPager.setLastPageDataCallbacks(this);
        Object data = viewPager.getLastPageData();
        if (data == null) return;
        mComplexDataHelper = (ComplexDataHelper) data;
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                mRatingBar.setRating(mComplexDataHelper.getRating());
                mNestedView.setScrollY(mComplexDataHelper.getPosY());
            }
        });
    }
}