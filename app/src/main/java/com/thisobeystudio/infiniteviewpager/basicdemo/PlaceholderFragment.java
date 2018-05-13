package com.thisobeystudio.infiniteviewpager.basicdemo;

/*
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customviewpager.viewpager.CustomIndexHelper;
import com.thisobeystudio.infiniteviewpager.R;

import static com.thisobeystudio.infiniteviewpager.demodata.DemoDataManager.getDarkerColor;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section for this fragment.
     */
    private static final String ARG_SECTION_HELPER = "section_helper";
    private static final String ARG_DEMO_COLOR = "demo_color";

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
        args.putParcelable(ARG_SECTION_HELPER, customIndexHelper);
        args.putInt(ARG_DEMO_COLOR, demoColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_basic, container, false);

        if (getArguments() != null &&
                getArguments().containsKey(ARG_SECTION_HELPER) &&
                getArguments().containsKey(ARG_DEMO_COLOR)) {

            CustomIndexHelper indexHelper = getArguments().getParcelable(ARG_SECTION_HELPER);
            int color = getArguments().getInt(ARG_DEMO_COLOR);

            int darkerColor = getDarkerColor(color);
            rootView.setBackgroundColor(darkerColor);

            CardView cardView = rootView.findViewById(R.id.demo_card_view);
            cardView.setCardBackgroundColor(color);

            if (indexHelper != null) {
                String pagerPosition = String.valueOf(indexHelper.getPagerPosition());
                String dataPosition = String.valueOf(indexHelper.getDataPosition());

                TextView pageIndexLabel = rootView.findViewById(R.id.section_page_index_label);
                pageIndexLabel.setText(pagerPosition);

                TextView dataIndexLabel = rootView.findViewById(R.id.section_data_index_label);
                dataIndexLabel.setText(dataPosition);
                dataIndexLabel.setTextColor(darkerColor);
            }
        }
        return rootView;
    }
}