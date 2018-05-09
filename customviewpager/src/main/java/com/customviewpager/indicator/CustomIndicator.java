package com.customviewpager.indicator;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.customviewpager.viewpager.CustomViewPager;
import com.customviewpager.R;

/**
 * Created by thisobeystudio on 9/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */
public class CustomIndicator {

    private static final String TAG = "CustomIndicator";

    private IndicatorsRecyclerViewAdapter mAdapter;

    private RecyclerView mRecyclerView;

    // if mIndicatorsHeightMode is set to WRAP, this param will be ignored.
    public int mMaxVisibleIndicatorRows = 2;

    public void setMaxVisibleIndicatorRows(int maxVisibleIndicatorRows) {
        this.mMaxVisibleIndicatorRows = maxVisibleIndicatorRows;
    }

    public int getMaxVisibleIndicatorRows() {
        return this.mMaxVisibleIndicatorRows;
    }

    private int mIndicatorsPositionMode = POSITION_INCLUDE_BOTTOM;
    private int mIndicatorsAdjustMode = MODE_CLAMPED_HEIGHT;

    public void setIndicatorsMode(int indicatorsPositionMode, int indicatorsAdjustMode) {
        this.mIndicatorsPositionMode = indicatorsPositionMode;
        this.mIndicatorsAdjustMode = indicatorsAdjustMode;
    }

    public static final int POSITION_FLOAT_TOP = 0;
    public static final int POSITION_FLOAT_BOTTOM = 1;
    public static final int POSITION_INCLUDE_TOP = 2;
    public static final int POSITION_INCLUDE_BOTTOM = 3;

    // not using enums
    public static final int MODE_WRAP_HEIGHT = 4;       // from 1 to infinite based on rows count
    public static final int MODE_FIXED_HEIGHT = 5;      // itemHeight * (margin * 2) * maxVisibleIndicatorRows
    public static final int MODE_CLAMPED_HEIGHT = 6;    // from 1 to maxVisibleIndicatorRows

    private final ConstraintLayout mParent;

    public CustomIndicator(Context context, ConstraintLayout parent, CustomViewPager viewPager) {

        if (context == null || parent == null || viewPager == null) {
            Log.e(TAG, "Can NOT init CustomIndicator. " +
                    "One or more of the Constructors parameters are null.");
            this.mParent = null;
            return;
        }

        this.mParent = parent;
        initRecyclerView(context, viewPager);
    }

    private void initRecyclerView(final Context context,
                                  final CustomViewPager viewPager) {

        if (context == null || mParent == null || viewPager == null) return;

        LayoutInflater inflater = LayoutInflater.from(context);

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.indicators_view, mParent, false);

        if (mRecyclerView == null) {
            Log.e(TAG, "Can NOT find a Layout named id:indicators_view.");
            return;
        }

        // set layout manager
        // initial spawn count to ONE
        final GridLayoutManager glm = new GridLayoutManager(context, 1);
        mRecyclerView.setLayoutManager(glm);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // this makes scroll smoothly
        mRecyclerView.setNestedScrollingEnabled(false);

        final int totalCount = viewPager.getRealCount();
        int selection = viewPager.getRealCurrentItem();

        // specify an adapter
        mAdapter = new IndicatorsRecyclerViewAdapter(context, totalCount, selection);

        // set recyclerView adapter
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.scrollToPosition(selection);

        // set recyclerView VISIBLE
        mRecyclerView.setVisibility(View.VISIBLE);


        calcItemsPerRow(context, viewPager, totalCount);

//        setupConstraintLayout(context, parent);

    }

    private void calcItemsPerRow(final Context context,
                                 final ViewPager viewPager,
                                 final int totalCount) {

        if (mRecyclerView == null || context == null || viewPager == null || totalCount <= 0)
            return;

        // Since the indicators container width is based on viewPagers width,
        // use post to get viewPagers real width.
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                // Update SpanCount, if maxItemsPerRow is not bigger than 1 just return
                // maxItemsPerRow will be set, based on:
                // ViewPager.width, indicatorsItems.width and R.dimen.indicator_horizontal_margin

                if (viewPager.getAdapter() == null || totalCount <= 1) {
                    return;
                }

                int indicatorItemSize = getDimension(context, R.dimen.indicator_item_size);
                int margin = getDimension(context, R.dimen.indicator_horizontal_margin) * 2;

                final int width = viewPager.getWidth();
                final int maxPossibleWidth = width - margin;
                int maxItemsPerRow = maxPossibleWidth / indicatorItemSize;

                // this will keep indicators centered horizontally
                if (maxItemsPerRow > totalCount) maxItemsPerRow = totalCount;

                if (maxItemsPerRow < 1) return;

                // set final spawn count
                ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(maxItemsPerRow);

                updateIndicatorsContainerHeight(context,
                        indicatorItemSize,
                        totalCount,
                        maxItemsPerRow);

                if (!mRecyclerView.isAttachedToWindow()) updateConstraints(viewPager);

            }
        });
    }

    public void updateSelection(int newSelection) {
        mRecyclerView.scrollToPosition(newSelection);
        if (mAdapter != null) mAdapter.updateSelection(newSelection);
    }

    private void updateIndicatorsContainerHeight(final Context context,
                                                 final int indicatorItemSize,
                                                 final int totalCount,
                                                 final int maxItemsPerRow) {

        if (context == null || mIndicatorsAdjustMode == MODE_WRAP_HEIGHT) return;

        int height;
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        if (params == null) return;

        int padding = getDimension(context, R.dimen.indicator_vertical_padding) * 2;

        switch (mIndicatorsAdjustMode) {
            case MODE_FIXED_HEIGHT:
                height = indicatorItemSize * mMaxVisibleIndicatorRows + (padding);
                params.height = height;
                break;
            case MODE_CLAMPED_HEIGHT:
                int rows = (int) Math.ceil(totalCount / (maxItemsPerRow + 0f));
                if (rows < mMaxVisibleIndicatorRows) {
                    height = rows * indicatorItemSize + padding;
                    params.height = height;
                } else {
                    height = indicatorItemSize * mMaxVisibleIndicatorRows + (padding);
                    params.height = height;
                }
                break;
            case MODE_WRAP_HEIGHT:
            default:
                // nothing to do here, this should not be called...
                break;
        }
    }

    private int getDimension(Context context, @DimenRes int dimenID) {
        if (context == null) return 0;
        return context.getResources().getDimensionPixelOffset(dimenID);
    }

    private int getFixedIndicatorItemSize(Context context, int indicatorItemSize) {

        if (context == null) return 12;

        if (indicatorItemSize < 1) {
            DisplayMetrics displayMetrics = new DisplayMetrics();

            WindowManager windowmanager = (WindowManager) context.getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            if (windowmanager == null) {
                return 12;
            }

            windowmanager.getDefaultDisplay().getMetrics(displayMetrics);

            return Math.round(12 * displayMetrics.density);
        }

        return indicatorItemSize;
    }

    public IndicatorsRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setIndicatorCallbacks(IndicatorsRecyclerViewAdapter.IndicatorCallbacks indicatorCallbacks) {
        if (getAdapter() == null || indicatorCallbacks == null) return;
        getAdapter().setIndicatorCallbacks(indicatorCallbacks);
    }

    public void notifyDataSetChanged() {
        if (mAdapter == null) return;
        mAdapter.notifyDataSetChanged();
    }

    public void setCount(Context context, ViewPager viewPager, int count) {
        if (mAdapter == null) return;
        calcItemsPerRow(context, viewPager, count);
        mAdapter.setCount(count);
        notifyDataSetChanged();
    }

    private void setupConstraintLayout(final Context context,
                                       final ConstraintLayout parent) {

        if (context == null || parent == null) return;

        final ConstraintSet constraintSet = new ConstraintSet();


        TransitionManager.beginDelayedTransition(parent);

        final int margin = 0; // no margin to get full parent size

        constraintSet.connect(mRecyclerView.getId(),
                ConstraintSet.BOTTOM,
                parent.getId(),
                ConstraintSet.BOTTOM,
                margin);

        constraintSet.connect(mRecyclerView.getId(),
                ConstraintSet.START,
                parent.getId(),
                ConstraintSet.START,
                margin);

        constraintSet.connect(mRecyclerView.getId(),
                ConstraintSet.END,
                parent.getId(),
                ConstraintSet.END,
                margin);

        constraintSet.connect(mRecyclerView.getId(),
                ConstraintSet.TOP,
                parent.getId(),
                ConstraintSet.TOP,
                margin);


        Log.i(TAG, "setupConstraintLayout");

        constraintSet.applyTo(parent);

    }


    private void updateConstraints(final ViewPager pager) {

        if (mRecyclerView == null || mParent == null || pager == null) return;


        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mParent);


//                TransitionManager.beginDelayedTransition(mParent);

        connectConstraintToParent(constraintSet, ConstraintSet.START);
        connectConstraintToParent(constraintSet, ConstraintSet.END);

        constraintSet.centerHorizontally(mRecyclerView.getId(), pager.getId());
//                constraintSet.centerVertically(mRecyclerView.getId(), parent.getId());
        constraintSet.constrainWidth(mRecyclerView.getId(), mRecyclerView.getLayoutParams().width);
        constraintSet.constrainHeight(mRecyclerView.getId(), mRecyclerView.getLayoutParams().height);
//                constraintSet.setMargin(mRecyclerView.getId(), ConstraintSet.START, margin/2);

        switch (mIndicatorsPositionMode) {
            case POSITION_FLOAT_TOP:
                connectConstraintToParent(constraintSet, ConstraintSet.TOP);
                connectConstraint(constraintSet, pager, ConstraintSet.TOP);
                break;
            case POSITION_FLOAT_BOTTOM:
                connectConstraintToParent(constraintSet, ConstraintSet.BOTTOM);
                connectConstraint(constraintSet, pager, ConstraintSet.BOTTOM);
                break;
            case POSITION_INCLUDE_TOP:
                connectConstraintToParent(constraintSet, ConstraintSet.TOP);
                connectIndicatorsToPageTop(constraintSet, pager);
                break;
            case POSITION_INCLUDE_BOTTOM:
                connectConstraintToParent(constraintSet, ConstraintSet.BOTTOM);
                connectIndicatorsToPageBottom(constraintSet, pager);
                break;
            default:
                // todo throw an error
                break;
        }

        connectConstraint(constraintSet, pager, ConstraintSet.START);
        connectConstraint(constraintSet, pager, ConstraintSet.END);

        mParent.addView(mRecyclerView);

        constraintSet.applyTo(mParent);
    }

    private void connectConstraintToParent(ConstraintSet constraintSet, int position) {
        if (constraintSet == null) return;
        constraintSet.connect(mRecyclerView.getId(), position, mParent.getId(), position, 0);
    }

    private void connectConstraint(ConstraintSet constraintSet, ViewPager pager, int position) {
        if (constraintSet == null || pager == null) return;
        constraintSet.connect(mRecyclerView.getId(), position, pager.getId(), position, 0);
    }

    private void connectIndicatorsToPageBottom(ConstraintSet constraintSet, ViewPager pager) {
        if (constraintSet == null || pager == null) return;
        constraintSet.connect(pager.getId(),
                ConstraintSet.BOTTOM,
                mRecyclerView.getId(),
                ConstraintSet.TOP,
                0);
    }

    private void connectIndicatorsToPageTop(ConstraintSet constraintSet, ViewPager pager) {
        if (constraintSet == null || pager == null) return;
        constraintSet.connect(pager.getId(),
                ConstraintSet.TOP,
                mRecyclerView.getId(),
                ConstraintSet.BOTTOM,
                0);
    }
}
