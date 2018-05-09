package com.thisobeystudio.infiniteviewpager.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.customviewpager.viewpager.CustomFragment;
import com.customviewpager.viewpager.CustomIndexHelper;
import com.thisobeystudio.infiniteviewpager.R;

import static android.support.v4.widget.TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM;
import static android.support.v4.widget.TextViewCompat.setAutoSizeTextTypeWithDefaults;

/**
 * Created by thisobeystudio on 6/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */
public class PlaceholderFragment
        extends CustomFragment
        implements CompoundButton.OnCheckedChangeListener, NestedScrollView.OnScrollChangeListener {

    //private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private MainActivity mMainActivity;

    private int mPageIndex = -1;
    private int mDataIndex = -1;
    private int mTotalPages = -1;
    private int mRealTotalPages = -1;

    private CheckBox mCheckBox;
    private NestedScrollView mNestedScrollView;

    public PlaceholderFragment() {
    }

    private DemoDataHelper mDemoDataHelper = null;

    public static PlaceholderFragment newInstance(CustomIndexHelper customIndexHelper) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CUSTOM_INDEX_HELPER, customIndexHelper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMainActivity = ((MainActivity) getActivity());
        if (mMainActivity == null) return rootView;

        setCustomIndexHelper(getArguments());

        setPageData();

        initCheckBox(rootView);
        initTextView(rootView);
        initNestedScrollView(rootView);

        updateDataIfNeeded();
        updateViewsIfNeeded();

        return rootView;
    }

    private void setPageData() {
        mTotalPages = mMainActivity.getPagesCount();
        mRealTotalPages = mMainActivity.getRealCount();
        mPageIndex = getPageIndex();
        mDataIndex = getDataIndex();
    }

    // region UI

    private void initCheckBox(View rootView) {
        if (!isFirstOrLastPage()) return;                                   // just return
        mCheckBox = rootView.findViewById(R.id.check_box);
        mCheckBox.setOnCheckedChangeListener(this);
    }

    private void initTextView(View rootView) {
        TextView textView = rootView.findViewById(R.id.section_label);
        setAutoSizeTextTypeWithDefaults(textView, AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setText(getTextViewText());
    }

    private void initNestedScrollView(View rootView) {
        if (!isFirstOrLastPage()) return;                                   // just return
        mNestedScrollView = rootView.findViewById(R.id.demo_scroll_view);
        mNestedScrollView.setOnScrollChangeListener(this);
    }

    // endregion UI

    private String getTextViewText() {
        return "PageIndex: " + mPageIndex +
                "\n\nDataIndex: " + mDataIndex +
                "\n\nRealPages: " + mRealTotalPages +
                "\n\nTotalPages: " + mTotalPages;
    }

    private void updateDataIfNeeded() {
        if (mPageIndex == 0)                                                // last helper page
            mDemoDataHelper = mMainActivity.getLastFragmentData();          // get stored data
        else if (mPageIndex == mTotalPages - 1)                             // first helper page
            mDemoDataHelper = mMainActivity.getFirstFragmentData();         // get stored data
    }

    private void updateViewsIfNeeded() {

        if (mDemoDataHelper == null || (mPageIndex != 0 && mPageIndex != mTotalPages - 1))
            return;

        mCheckBox.post(new Runnable() {
            @Override
            public void run() {
                mCheckBox.setChecked(mDemoDataHelper.isCheckBoxChecked());
            }
        });

        mNestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                mNestedScrollView.setScrollY(mDemoDataHelper.getScrollPosY());
            }
        });
    }

    private void updateViews() {

        if (mDemoDataHelper == null || (mPageIndex != 0 && mPageIndex != mTotalPages - 1))
            return;

        mCheckBox.setChecked(mDemoDataHelper.isCheckBoxChecked());
        mNestedScrollView.setScrollY(mDemoDataHelper.getScrollPosY());
    }

    // region UI Listeners

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateCheckBoxData();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        updateNestedData();
    }

    // endregion UI Listeners

    @Override
    public Object getPageData() {
        if (mPageIndex == 1 || mPageIndex == mTotalPages - 2) return mDemoDataHelper;
        return null;
    }

    @Override
    public void setHelperPageData(Object data) {
        if (mPageIndex == 0 || mPageIndex == mTotalPages - 1)
            mDemoDataHelper = (DemoDataHelper) data;

        updateViews();
    }

    private void updateCheckBoxData() {

        if (mDemoDataHelper == null) mDemoDataHelper = new DemoDataHelper();

        if (mPageIndex == 0 || mPageIndex == mTotalPages - 1) {

            mCheckBox.setChecked(mDemoDataHelper.isCheckBoxChecked());

        } else if (mRealTotalPages == 1) {

            mDemoDataHelper.setCheckBoxChecked(mCheckBox.isChecked());
            mMainActivity.setFirstPageDataCallbacks(this);
            mMainActivity.setLastPageDataCallbacks(this);

        } else if (mPageIndex == 1) {

            mDemoDataHelper.setCheckBoxChecked(mCheckBox.isChecked());
            mMainActivity.setFirstPageDataCallbacks(this);

        } else if (mPageIndex == mTotalPages - 2) {

            mDemoDataHelper.setCheckBoxChecked(mCheckBox.isChecked());
            mMainActivity.setLastPageDataCallbacks(this);

        }
    }

    private void updateNestedData() {

        if (mDemoDataHelper == null) mDemoDataHelper = new DemoDataHelper();

        if (mPageIndex == 0 || mPageIndex == mTotalPages - 1) {

            mNestedScrollView.setScrollY(mDemoDataHelper.getScrollPosY());

        } else if (mRealTotalPages == 1) {

            mDemoDataHelper.setScrollPosY(mNestedScrollView.getScrollY());
            mMainActivity.setFirstPageDataCallbacks(this);
            mMainActivity.setLastPageDataCallbacks(this);

        } else if (mPageIndex == 1) {

            mDemoDataHelper.setScrollPosY(mNestedScrollView.getScrollY());
            mMainActivity.setFirstPageDataCallbacks(this);

        } else if (mPageIndex == mTotalPages - 2) {

            mDemoDataHelper.setScrollPosY(mNestedScrollView.getScrollY());
            mMainActivity.setLastPageDataCallbacks(this);

        }
    }

    /**
     * @return {@code true} if {@param #mPageIndex} is equals to first or last pages,
     * note that helper pages are also checked
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isFirstOrLastPage() {
        return mPageIndex <= 1 || mPageIndex >= mTotalPages - 2;
    }

    private static Toast mToast;

    public static void showToast(Context context, final String msg) {
        if (context == null || TextUtils.isEmpty(msg)) return;
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}