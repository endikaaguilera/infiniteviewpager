package com.customviewpager.viewpager;

/*
 * Created by thisobeystudio on 8/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

// todo add java docs
// todo test if not using callback a normal fragment...
// todo disable user interaction on helper pages (or) share data in both ways... from realFirst to helperFirst and viceversa

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.customviewpager.indicator.CustomIndicator;
import com.customviewpager.indicator.IndicatorsRecyclerViewAdapter;

import java.util.ArrayList;

// todo suggestion setOffscreenPageLimit(1) by default. Is the best option if using 'Editable' Views
public class CustomViewPager extends ViewPager
        implements IndicatorsRecyclerViewAdapter.IndicatorCallbacks {

//    private static final String TAG = "CustomViewPager";
//    private static final boolean DEBUG = true;

    /**
     * A flag to determine if using {@link CustomViewPagerCallbacks} or not.
     * <p>{@code default} is {@code true}
     */
    private boolean mUsingCallbacks = true;

    /**
     * Set using {@link CustomViewPagerCallbacks},
     * to update the duplicated pages data.
     * <p>Since we are duplicating real first and last pages,
     * this option should be enabled {@code true} if pages contains Interactive content such as:
     * <p>NestedScrollView, CheckBox, Spinner, EditText, etc...
     * <p>
     * <p>Notice! That when {@code true} the placeholder Fragment must extend {@link CustomFragment}
     * , otherwise we will get a {@link ClassCastException}
     *
     * @param useCallbacks A flag to determine if using {@link CustomViewPagerCallbacks} or not.
     *                     <p>{@code default} is {@code true}
     */
    public void useHelpersCallbacks(boolean useCallbacks) {
        mUsingCallbacks = useCallbacks;
    }

    /**
     * @return {@code true} if using callbacks, {@code false} otherwise.
     * <p>{@code default} is {@code true}
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isUsingCallbacks() {
        return mUsingCallbacks;
    }

    public CustomViewPager(@NonNull Context context) {
        super(context);
        initCustomViewPager();
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomViewPager();
    }

    @Nullable
    @Override
    public CustomPagerAdapter getAdapter() {
        return (CustomPagerAdapter) super.getAdapter();
    }

    // tested this, ACTION_UP, ACTION_MOVE anf ACTION_DOWN can be recognized, not tested all...
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Toast.makeText(getContext(), "ACTION_DOWN", Toast.LENGTH_SHORT).show();
//
//            checkHelperFirstPage();
//            checkHelperLastPage();
//
//            this.getParent().requestDisallowInterceptTouchEvent(true);
//            super.performClick();
//            return super.onTouchEvent(event);
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//
//            Toast.makeText(getContext(), "ACTION_UP", Toast.LENGTH_SHORT).show();
//
//            checkHelperFirstPage();
//            checkHelperLastPage();
//
//            this.getParent().requestDisallowInterceptTouchEvent(true);
//            super.performClick();
//            return super.onTouchEvent(event);
//        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//
//            Toast.makeText(getContext(), "ACTION_MOVE", Toast.LENGTH_SHORT).show();
//
//            Log.i("AAAATTTTTT", "ACTION_MOVE");
//
//            checkHelperFirstPage();
//            checkHelperLastPage();
//
//            this.getParent().requestDisallowInterceptTouchEvent(true);
//            super.performClick();
//            return super.onTouchEvent(event);
//        } else {
//            return super.onTouchEvent(event);
//        }
//    }
//
//    @Override
//    public boolean performClick() {
//        return super.performClick();
//    }
////
////    @Override
////    public void setOnTouchListener(OnTouchListener l) {
////        super.setOnTouchListener(l);
////    }
////
////    @Override
////    public boolean onTouchEvent(MotionEvent ev) {
////        return false;
////    }
////
////    @Override
////    public boolean performClick() {
////        return false;
////    }
////
////    @Override
////    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        return false;
////    }

    private void initCustomViewPager() {

        // todo this might be important....
        // todo force it to one!! create a checker and throw an exception if not ONE
        setOffscreenPageLimit(1);// todo test this with higher values!!!

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int posOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                CustomViewPager.this.onPageSelected();
                updateIndicatorPosition(getRealPosition(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                CustomViewPager.this.onPageScrollStateChanged(state);
            }
        });

        if (getAdapter() != null) getAdapter().notifyDataSetChanged();// todo is this necessary??

    }

    // region OnPageChangeListener

    private void onPageSelected() {
        if (!isUsingCallbacks()) return;
        updateHelperFirstPageData();
        updateHelperLastPageData();
    }

    // set pages
    private void onPageScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE && isFirstHelperPageSelected()) {
            setCurrentItem(getRealCount() - 1, false);
        } else if (state == SCROLL_STATE_IDLE && isLastHelperPageSelected()) {
            setCurrentItem(getRealFirstPageIndex() - 1, false);
        }
    }

    // endregion OnPageChangeListener

    public ArrayList<Fragment> getFragments() {
        CustomPagerAdapter adapter = getAdapter();
        if (adapter == null) return null;
        return adapter.getFragments();
    }

    public Fragment getFragment(int index) {
        ArrayList<Fragment> fragments = getFragments();
        if (fragments == null || index < 0 || index >= fragments.size()) return null;
        return fragments.get(index);
    }

    public Fragment getRealFirstFragment() {
        return getFragment(getRealFirstPageIndex());
    }

    public Fragment getHelperFirstFragment() {
        return getFragment(getHelperFirstPageIndex());
    }

    public Fragment getRealLastFragment() {
        return getFragment(getRealLastPageIndex());
    }

    public Fragment getHelperLastFragment() {
        return getFragment(getHelperLastPageIndex());
    }

    // todo must check here that getCount and GetRealCount and x1 = (x2 + 2) osea que si un total es 10 el otro 12
    // todo must check here that getCount and GetRealCount and x1 = (x2 + 2) osea que si un total es 10 el otro 12
    // todo must check here that getCount and GetRealCount and x1 = (x2 + 2) osea que si un total es 10 el otro 12
    // todo must check here that getCount and GetRealCount and x1 = (x2 + 2) osea que si un total es 10 el otro 12
    public int getCount() {
        if (getRealCount() <= 0) return 0; // just return 0
        return getRealCount() + 2;
    }

    public int getRealCount() {
        CustomPagerAdapter adapter = getAdapter();
        if (adapter == null || adapter.getRealCount() <= 0) return 0; // just return 0
        return adapter.getRealCount();
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        if (adapter == null) return;
        String msg = "PagerAdapter Not Implemented! " +
                "Please use (CustomPagerAdapter.java:17) instead.";
        throw new RuntimeException(msg);
    }

    public void setAdapter(CustomPagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    public final boolean isFirstRealPageSelected() {
        return super.getCurrentItem() == getRealFirstPageIndex();
    }

    public final boolean isLastRealPageSelected() {
        return super.getCurrentItem() == getRealLastPageIndex();
    }

    private boolean isFirstHelperPageSelected() {
        return super.getCurrentItem() == getHelperLastPageIndex();
    }

    private boolean isLastHelperPageSelected() {
        return super.getCurrentItem() == getHelperFirstPageIndex();
    }

    private int getRealFirstPageIndex() {
        return 1;
    }

    private int getRealLastPageIndex() {
        return getRealCount();
    }

    private int getHelperFirstPageIndex() {
        return getCount() - 1;
    }

    private int getHelperLastPageIndex() {
        return 0;
    }

    /* NEW */

    // region First Page Data

    private Object mFirstPageData = null;

    public Object getFirstPageData() {
        return mFirstPageData;
    }

//    public void setFirstPageData(Object firstPageData) {
//
//        if (!isUsingCallbacks()) return;
//
//        mFirstPageData = firstPageData; // update data
//
//        Fragment firstFragment = getHelperFirstFragment();
//
//        if (firstFragment != null)
//            mFirstPageCallbacks = (CustomFragment) firstFragment;
//
//        if (mFirstPageCallbacks != null && firstFragment != null) {
//            if (mFirstPageCallbacks.getHelperFirstPageData() != mFirstPageData) {// todo use this if to avoid unnecessary usage ???
//                mFirstPageCallbacks.setHelperFirstPageData(mFirstPageData);
//            }
//        }
//        //setFirstPageData();
//    }
//
//
//    private void setFirstPageData() {
//        Fragment firstFragment = getHelperFirstFragment();
//
//        if (firstFragment != null)
//            mFirstPageCallbacks = (CustomFragment) firstFragment;
//
//        if (mFirstPageCallbacks != null && firstFragment != null) {
//            Object data = getFirstPageData();
//            if (mFirstPageCallbacks.getHelperFirstPageData() != data) {// todo use this if to avoid unnecessary usage ???
//                mFirstPageCallbacks.setHelperFirstPageData(data);
//            }
//        }
//    }

    public void clearPagesData() {
        clearFirstPageData();
        clearLastPageData();
    }

    public void clearFirstPageData() {
        if (!isUsingCallbacks()) return;
        mFirstPageData = null;
    }

    // todo this can be simplified passing page index or a boolean flag (isFirst)
    public void setFirstPageData(CustomViewPagerCallbacks callbacks) {
        //if (!isUsingCallbacks() || callbacks == null) return;
        if (!isUsingCallbacks() || callbacks == null || callbacks.getPageData() == null) return;
        mFirstPageData = callbacks.getPageData(); // update data
        updateHelperFirstPageData();
    }

    public void updateHelperFirstPageData() {
        if (mFirstPageData == null)             return;

        Fragment firstHelperFragment = getHelperFirstFragment();
        // todo catch ClassCastException ??
        if (firstHelperFragment != null) {
            CustomViewPagerCallbacks helperCallbacks = (CustomFragment) firstHelperFragment;
            helperCallbacks.setHelperPageData(mFirstPageData);
        }
    }
    // endregion First Page Data

    // region Last Page Data

    private Object mLastPageData = null;

    public Object getLastPageData() {
        return mLastPageData;
    }

    public void clearLastPageData() {
        if (!isUsingCallbacks()) return;
        mLastPageData = null;
    }

    public void setLastPageData(CustomViewPagerCallbacks callbacks) {
        //if (!isUsingCallbacks() || callbacks == null) return;
        if (!isUsingCallbacks() || callbacks == null || callbacks.getPageData() == null) return;
        mLastPageData = callbacks.getPageData(); // update data
        updateHelperLastPageData();
    }

    public void updateHelperLastPageData() {
        if (mLastPageData == null)             return;

        Fragment lastHelperFragment = getHelperLastFragment();
        // todo catch ClassCastException ??
        if (lastHelperFragment != null) {
            CustomViewPagerCallbacks helperLastPageCallbacks = (CustomFragment) lastHelperFragment;
            helperLastPageCallbacks.setHelperPageData(mLastPageData);
        }
    }

//    public void setLastPageData(Object lastPageData) {
//
//        if (!isUsingCallbacks()) return;
//
//        mLastPageData = lastPageData; // update data
//
//        CustomFragment lastFragment = (CustomFragment) getHelperLastFragment();
//
//        if (lastFragment != null)
//            mLastPageCallbacks = lastFragment;
//
//        if (mLastPageCallbacks != null && lastFragment != null) {
//            if (mLastPageCallbacks.getHelperLastPageData() != mLastPageData) {// todo use this if to avoid unnecessary usage ???
//                mLastPageCallbacks.setHelperLastPageData(mLastPageData);
//            }
//        }
//        //setLastPageData();
//    }
//
//    public void setLastPageData() {
//        Fragment lastFragment = getHelperLastFragment();
//
//        if (lastFragment != null)
//            mLastPageCallbacks = (CustomFragment) lastFragment;
//
//        if (mLastPageCallbacks != null && lastFragment != null) {
//            Object data = getLastPageData();
//            if (mLastPageCallbacks.getHelperLastPageData() != data) {// todo use this if to avoid unnecessary usage ???
//                mLastPageCallbacks.setHelperLastPageData(data);
//            }
//        }
//    }

    // endregion Last Page Data

    private int getRealPosition(int position) {
        CustomPagerAdapter adapter = getAdapter();
        if (adapter == null) return 0;
        return adapter.getRealPosition(position);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item + 1, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item + 1);
    }

    @Override
    public int getCurrentItem() {
        String errMsg = "ERROR! getCurrentItem() not implemented!" +
                " Please use getRealCurrentItem() instead.";
        throw new RuntimeException(errMsg);
    }

    // todo user must use getRealCurrentItem Instead of getCurrentItem()
    public int getRealCurrentItem() {
        return getRealPosition(super.getCurrentItem());
    }

    // region Indicators

    private CustomIndicator mCustomIndicator;

    //todo test this with any kind of parent as View ??
    public void initViewPagerIndicators(Context context, ConstraintLayout parent) {

        if (context == null) return;

        mCustomIndicator = new CustomIndicator(context, parent, this);

        // set the indicator callbacks for onIndicatorClick
        mCustomIndicator.setIndicatorCallbacks(this);
    }

    public void setIndicatorsMode(int position, int adjustMode, int maxRows) {
        if (mCustomIndicator == null) return;
        mCustomIndicator.setIndicatorsMode(position, adjustMode);
        mCustomIndicator.setMaxVisibleIndicatorRows(maxRows);
    }

    public void setIndicatorsMode(int position, int adjustMode) {
        if (mCustomIndicator == null) return;
        mCustomIndicator.setIndicatorsMode(position, adjustMode);
    }

    public void setMaxVisibleIndicatorRows(int maxRows) {
        if (mCustomIndicator == null) return;
        mCustomIndicator.setMaxVisibleIndicatorRows(maxRows);
    }

    private void updateIndicatorPosition(int position) {
        if (mCustomIndicator == null) return;
        mCustomIndicator.updateSelection(position);
    }

    @Override
    public void onIndicatorClick(int position) {
        setCurrentItem(position, true);
    }

    // endregion Indicators

    private void showToast(String msg) {
        if (TextUtils.isEmpty(msg) || getContext() == null) return;
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void notifyDataSetChanged() {
        CustomPagerAdapter adapter = getAdapter();
        if (adapter == null) return;
        adapter.notifyDataSetChanged();
        if (mCustomIndicator == null) return;
        mCustomIndicator.setCount(getContext(), this, getRealCount());
    }

//    private void showToast(String msg) {
//        if (DEBUG && !TextUtils.isEmpty(msg))
//            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
}
