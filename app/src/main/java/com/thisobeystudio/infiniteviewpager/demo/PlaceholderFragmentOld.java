package com.thisobeystudio.infiniteviewpager.demo;

/*
 * Created by thisobeystudio on 1/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragmentOld {
}
//
//public class PlaceholderFragmentOld
//        extends CustomFragment
//        implements NestedScrollView.OnScrollChangeListener {
//
//    // todo probar a crear un parelable statico fgobals con os datos de la primera pagina coger el scrol y pasarselo a todos a ver que hace...''''''''''
//    private static final String TAG = "PlaceholderFragment";
//
//    private MainActivity mMainActivity;
//
//    private int mPageIndex = -1;
//    private int mDataIndex = -1;
//    private int mTotalPages = -1;
//    private int mRealTotalPages = -1;
//
//    private CheckBox mCheckBox;
//    private NestedScrollView mNestedScrollView;
//
//    public PlaceholderFragmentOld() {
//    }
//
//    private DemoDataHelper mDemoDataHelper = null;
//
//    public static PlaceholderFragmentOld newInstance(CustomIndexHelper customIndexHelper) {
//        PlaceholderFragmentOld fragment = new PlaceholderFragmentOld();
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_CUSTOM_INDEX_HELPER, customIndexHelper);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @SuppressLint("StringFormatMatches")
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//
//        mMainActivity = ((MainActivity) getActivity());
//        if (mMainActivity == null) return rootView;
//
//        setCustomIndexHelper(getArguments());
//
//        mTotalPages = mMainActivity.getPagesCount();
//        mRealTotalPages = mMainActivity.getRealCount();
//
//        mPageIndex = getPageIndex();
//        mDataIndex = getDataIndex();
//
//        initViews(rootView);
//
//        initDemoDataHelpers();
//
///*        if (mDemoDataHelper != null && (mPageIndex == 0 || mPageIndex == mTotalPages - 1)) {
//            updateCheckBox(mDemoDataHelper.isCheckBoxChecked());
//            scrollToPosY(mDemoDataHelper.getScrollPosY());
//        }*/
//
////        if (mPageIndex == mTotalPages - 1)
////            mMainActivity.updateHelperFirstPageData();
////        else if (mPageIndex == 0)
////            mMainActivity.updateHelperLastPageData();
//
////        if (mPageIndex == mTotalPages - 1) {
////            Toast.makeText(mMainActivity, "mPageIndex == mTotalPages - 1", Toast.LENGTH_SHORT).show();// todo delete this
////            mDemoDataHelper = mMainActivity.getFirstFragmentData();
////            if (mDemoDataHelper != null)
////                mNestedScrollView.scrollTo(0, mDemoDataHelper.getScrollPosY());
////        }
////
////        if (mPageIndex == 0) {
////            Toast.makeText(mMainActivity, "mPageIndex == 0", Toast.LENGTH_SHORT).show();// todo delete this
////            mDemoDataHelper = mMainActivity.getLastFragmentData();
////            if (mDemoDataHelper != null) {
////                mNestedScrollView.scrollTo(0, mDemoDataHelper.getScrollPosY());
////                mCheckBox.setChecked(mDemoDataHelper.isCheckBoxChecked());
////            }
////        }
//
//        return rootView;
//    }
//
//    private void initDemoDataHelpers() {
//        if (mDataIndex == 0 || mDataIndex == mRealTotalPages - 1) {
//            final boolean checked = mCheckBox.isChecked();
//            final int posY = mNestedScrollView.getScrollY();
//
//            if (mPageIndex == 0) {
//                DemoDataHelper fd = mMainActivity.getLastFragmentData();
//                if (fd != null) mDemoDataHelper = fd;
//                else mDemoDataHelper = new DemoDataHelper();
//            }
//
//            if (mPageIndex == mTotalPages - 1) {
//                DemoDataHelper fd = mMainActivity.getFirstFragmentData();
//                if (fd != null) mDemoDataHelper = fd;
//                else mDemoDataHelper = new DemoDataHelper();
//            }
//
//            if (mDemoDataHelper == null) return;
//
//            updateCheckBox(mDemoDataHelper.isCheckBoxChecked());
//            scrollToPosY(mDemoDataHelper.getScrollPosY());
//        }
//    }
//
//    // region UI
//
//    private void initViews(View rootView) {
//        initTextView(rootView);
//        initCheckBox(rootView);
//        initTextView(rootView);
//        initNestedScrollView(rootView);
//    }
//
//    private void initCheckBox(View rootView) {
//        mCheckBox = rootView.findViewById(R.id.check_box);
//        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (mDemoDataHelper != null) {
//
//                    // todo test with just 1
//                    // since data only should be edited/modified on real pages,
//                    // check current page index using getPageIndex() instead of getDataIndex()
//                    //  first real data page index is 1 and (viewPager.getRealCount() == 1)
//
//                    if (mPageIndex == 0) {
////                        if (mDemoDataHelper.isCheckBoxChecked() != mCheckBox.isChecked())
//                        updateCheckBox(mDemoDataHelper.isCheckBoxChecked());
//                    } else if (mPageIndex == mTotalPages - 1) {
////                        if (mDemoDataHelper.isCheckBoxChecked() != mCheckBox.isChecked())
//                        updateCheckBox(mDemoDataHelper.isCheckBoxChecked());
//                    } else {
//                        if (mPageIndex == 1 && mPageIndex == mRealTotalPages) {
//                            mDemoDataHelper.setCheckBoxChecked(isChecked);
//                            mMainActivity.setFirstPageDataCallbacks(PlaceholderFragmentOld.this);
//                            mMainActivity.setLastPageDataCallbacks(PlaceholderFragmentOld.this);
//                        } else if (mPageIndex == 1) {                               // first real data page
//                            mDemoDataHelper.setCheckBoxChecked(isChecked);
//                            mMainActivity.setFirstPageDataCallbacks(PlaceholderFragmentOld.this);
//                        } else if (mPageIndex == mRealTotalPages) {                 // last real data page
//                            mDemoDataHelper.setCheckBoxChecked(isChecked);
//                            mMainActivity.setLastPageDataCallbacks(PlaceholderFragmentOld.this);
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    private void initTextView(View rootView) {
//        TextView textView = rootView.findViewById(R.id.section_label);
//        setAutoSizeTextTypeWithDefaults(textView, AUTO_SIZE_TEXT_TYPE_UNIFORM);
//        String txt = "PageIndex: " + mPageIndex +
//                "\n\nDataIndex: " + mDataIndex +
//                "\n\nRealPages: " + mRealTotalPages +
//                "\n\nTotalPages: " + mTotalPages;
//        textView.setText(txt);
//    }
//
//    private boolean shouldCatchOnScrollListener() {
//        return mPageIndex == 0 ||                                               // last helper
//                mPageIndex == 1 ||                                              // first real
//                mPageIndex == mTotalPages - 2 ||                                // last real
//                mPageIndex == mTotalPages - 1;
//    }
//
//    private void initNestedScrollView(View rootView) {
//        if (shouldCatchOnScrollListener()) {
//            mNestedScrollView = rootView.findViewById(R.id.demo_scroll_view);
//            mNestedScrollView.setOnScrollChangeListener(this);
//
////            Toast.makeText(mMainActivity, "mPageIndex == mTotalPages - 1", Toast.LENGTH_SHORT).show();// todo delete this
////            DemoDataHelper data = ((DemoDataHelper) mMainActivity.getFirstFragmentData());
////            if (data != null) {
////                mDemoDataHelper = data;
////                mNestedScrollView.scrollTo(0, mDemoDataHelper.getScrollPosY());
////            }
//
//        }
//    }
//
//    // endregion UI
//
//    // region Callbacks
//
//    @Override
//    public DemoDataHelper getPageData() {
//        // TODO: test all callbacks that only triggers when it should and no more...
////        Toast.makeText(mMainActivity, "getPageData mPageIndex: " + mPageIndex, Toast.LENGTH_SHORT).show();
//
//        //todo if we want to clear data call clearLastPageData() and/or clearFirstPageData()
//        if (mPageIndex == 1 || mPageIndex == mTotalPages - 2) return mDemoDataHelper;
//
//        return null;
//    }
//
//    @Override
//    public void setHelperPageData(Object data) {
//        // TODO: test all callbacks that only triggers when it should and no more...
////        Toast.makeText(mMainActivity, "setHelperPageData mPageIndex: " + mPageIndex, Toast.LENGTH_SHORT).show();
//
//        if (data == null) return; // todo allow nulls ???
//        mDemoDataHelper = (DemoDataHelper) data;    // cast data as DemoDataHelper
//
//        updateHelpersData();
//    }
//
//    private void updateHelpersData() {
//        if (mDemoDataHelper == null) return;
////        if (mDemoDataHelper.isCheckBoxChecked() != mCheckBox.isChecked())
//        updateCheckBox(mDemoDataHelper.isCheckBoxChecked());
//        scrollToPosY(mDemoDataHelper.getScrollPosY());
//    }
//
//    @Override
//    public void onScrollChange(NestedScrollView v,
//                               int scrollX,
//                               int scrollY,
//                               int oldScrollX,
//                               int oldScrollY) {
//
//        // fixme ???
//        // if more than one pages are visible at the same time here we can
//        // force scrollPosY to keep/lock current pos for Helpers Pages if needed
//        lockFirstAndLastHelpersPages();
//
//        onFirstOrLastPageScrollChange(scrollY);
//    }
//
//    private void lockFirstAndLastHelpersPages() {
//        if (mDemoDataHelper == null) return;
//
//        int posY = mDemoDataHelper.getScrollPosY();
//        if (mPageIndex == 1 && mPageIndex == mRealTotalPages) {
//            autoScrollLastHelperPage(posY);
//            autoScrollFirstHelperPage(posY);
//        } else if (mPageIndex == 0) {
//            autoScrollLastHelperPage(posY);
//        } else if (mPageIndex == mTotalPages - 1) {
//            autoScrollFirstHelperPage(posY);
//        }
//    }
//
//    private void onFirstOrLastPageScrollChange(int scrollY) {
//        //  first real data page index is 1 and (viewPager.getRealCount() == 1)
//        if (mPageIndex == 1 && mPageIndex == mTotalPages - 2) {
//            updateFirstAndLastHelpersScrollData(scrollY);
//            autoScrollFirstHelperPage(scrollY);
//            autoScrollLastHelperPage(scrollY);
//        } else if (mPageIndex == 1) {                                // first real data page
//            updateFirstHelperScrollData(scrollY);
//            autoScrollFirstHelperPage(scrollY);
//        } else if (mPageIndex == mTotalPages - 2) {                  // last real data page
//            updateLastHelperScrollData(scrollY);
//            autoScrollLastHelperPage(scrollY);
//        }
//    }
//
//    // endregion Callbacks
//
//    // region IndicatorPosition Pages
//
//    private void updateFirstAndLastHelpersScrollData(int scrollY) {
//        if (mDemoDataHelper == null) return;
//        mDemoDataHelper.setScrollPosY(scrollY);
//        mMainActivity.setFirstPageDataCallbacks(this);
//        mMainActivity.setLastPageDataCallbacks(this);
//    }
//
//    private void updateFirstHelperScrollData(int scrollY) {
//        if (mDemoDataHelper == null) return;
//        mDemoDataHelper.setScrollPosY(scrollY);
//        mMainActivity.setFirstPageDataCallbacks(this);
//    }
//
//    private void updateLastHelperScrollData(int scrollY) {
//        if (mDemoDataHelper == null) return;
//        mDemoDataHelper.setScrollPosY(scrollY);
//        mMainActivity.setLastPageDataCallbacks(this);
//    }
//
//    private void autoScrollFirstHelperPage(int scrollY) {
////        PlaceholderFragmentOld first = mMainActivity.getHelperFirstFragment();
////        if (first != null && first.mNestedScrollView != null)
////            first.scrollToPosY(scrollY);
//    }
//
//    private void autoScrollLastHelperPage(int scrollY) {
////        PlaceholderFragmentOld last = mMainActivity.getHelperLastFragment();
////        if (last != null && last.mNestedScrollView != null)
////            last.scrollToPosY(scrollY);
//    }
//
//    // endregion IndicatorPosition Pages
//
//    public void updateCheckBox(final boolean checked) {
//        if (mCheckBox == null) return;
//        if (mCheckBox.isChecked() != checked) mCheckBox.setChecked(checked);
//    }
//
//    public void scrollToPosY(final int posY) {
//        if (mNestedScrollView == null) return;
//        mNestedScrollView.smoothScrollTo(0, posY);
//
////        mNestedScrollView.post(new Runnable() {
////            @Override
////            public void run() {
////                mNestedScrollView.scrollTo(0, posY);
////            }
////        });
//    }
//
////
////    @Override
////    public void onAttachFragment(Fragment childFragment) {
////        super.onAttachFragment(childFragment);
////        Log.i(TAG, "onAttachFragment dataIndex: " + mDataIndex + " - pageIndex: " + mPageIndex);
////    }
////
////    @Override
////    public void onDetach() {
////        super.onDetach();
////        Log.i(TAG, "onDetach dataIndex: " + mDataIndex + " - pageIndex: " + mPageIndex);
////    }
////
////    @Override
////    public void onDestroy() {
////        super.onDestroy();
////        Log.i(TAG, "onDestroy dataIndex: " + mDataIndex + " - pageIndex: " + mPageIndex);
////    }
////
////    @Override
////    public void onDestroyView() {
////        super.onDestroyView();
////        Log.i(TAG, "onDestroyOptionsMenu dataIndex: " + mDataIndex + " - pageIndex: " + mPageIndex);
////    }
//}