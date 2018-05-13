package com.thisobeystudio.infiniteviewpager.scrollabledemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static final String ARG_DEMO_COLOR = "demo_color";

    private NestedScrollView mNestedScrollView;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     */
    public static PlaceholderFragment newInstance(
            CustomIndexHelper customIndexHelper,
            int demoColor) {

        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CUSTOM_INDEX_HELPER, customIndexHelper); // fixme this is important!! ARG_CUSTOM_INDEX_HELPER
        args.putInt(ARG_DEMO_COLOR, demoColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_scrollable, container, false);

        if (getArguments() == null ||
                getActivity() == null ||
                !getArguments().containsKey(ARG_CUSTOM_INDEX_HELPER) ||
                !getArguments().containsKey(ARG_DEMO_COLOR)) return rootView;

        setCustomIndexHelper(getArguments());        // fixme this is important!!

        if (mCustomIndexHelper == null) return rootView;

        int color = getArguments().getInt(ARG_DEMO_COLOR);

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

            final CustomViewPager viewPager = ((ScrollableActivity) getActivity()).mViewPager;

            if (viewPager == null) return rootView;

            mNestedScrollView = rootView.findViewById(R.id.demo_scroll_view);
            mNestedScrollView.setOnScrollChangeListener(
                    new NestedScrollView.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(NestedScrollView v,
                                                   int scrollX, int scrollY,
                                                   int oldScrollX, int oldScrollY) {
                            if (isRealFirst())
                                viewPager.setFirstPageDataCallbacks(PlaceholderFragment.this);
                            if (isRealLast())
                                viewPager.setLastPageDataCallbacks(PlaceholderFragment.this);
                        }
                    });

            if (isRealFirst()) viewPager.setFirstPageDataCallbacks(this);
            if (isRealLast()) viewPager.setLastPageDataCallbacks(this);

            isHelperFirst(viewPager);
            isHelperLast(viewPager);
        }

        return rootView;
    }

    @Override
    public Object getPageData() {
        if (mNestedScrollView != null && (isRealFirst() || isRealLast())) {
            return mNestedScrollView.getScrollY();
        }
        return null;
    }

    @Override
    public void setHelperPageData(Object data) {
        if (mNestedScrollView != null && (isHelperFirst() || isHelperLast()))
            mNestedScrollView.setScrollY((int) data);
    }

    private void isHelperFirst(CustomViewPager viewPager) {
        if (!isHelperFirst() || viewPager == null || mNestedScrollView == null) return;
        viewPager.setFirstPageDataCallbacks(this);
        Object data = viewPager.getFirstPageData();
        if (data == null) return;
        final int posY = (int) data;
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                mNestedScrollView.setScrollY(posY);
            }
        });

    }

    private void isHelperLast(CustomViewPager viewPager) {
        if (!isHelperLast() || viewPager == null || mNestedScrollView == null) return;
        viewPager.setLastPageDataCallbacks(this);
        Object data = viewPager.getLastPageData();
        if (data == null) return;
        final int posY = (int) data;
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                mNestedScrollView.setScrollY(posY);
            }
        });
    }
}