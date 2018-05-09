package com.thisobeystudio.infiniteviewpager.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.customviewpager.viewpager.CustomViewPager;
import com.customviewpager.viewpager.CustomViewPagerCallbacks;
import com.thisobeystudio.infiniteviewpager.R;

import static com.customviewpager.indicator.CustomIndicator.MODE_CLAMPED_HEIGHT;
import static com.customviewpager.indicator.CustomIndicator.POSITION_INCLUDE_BOTTOM;

public class MainActivity extends AppCompatActivity {

//    private static final boolean DEBUG = false;
    /**
     * The {@link CustomViewPager} that will host the section contents.
     */
    private CustomViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int mTotalPages = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();

        // todo temporal
//        setPages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            if (mSectionsPagerAdapter != null) {
                mTotalPages++;
                mSectionsPagerAdapter.setCount(mTotalPages);
                mViewPager.notifyDataSetChanged();
                PlaceholderFragment.showToast(MainActivity.this, "mTotalPages: " + mTotalPages);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewPager() {

        // CustomFragmentStatePagerAdapter that returns a fragment corresponding to one of the pages
        mSectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager(), mTotalPages);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//        mViewPager.setClipChildren(true);
//        mViewPager.setClipToPadding(false);
////        mViewPager.setPageMargin(40);
//        mViewPager.setPadding(40, 40, 40, 40);

        ConstraintLayout parentContainer = findViewById(R.id.main_content);
        mViewPager.initViewPagerIndicators(this, parentContainer);
        mViewPager.setIndicatorsMode(POSITION_INCLUDE_BOTTOM, MODE_CLAMPED_HEIGHT, 2);

//        initViewPagerIndicators();

        // setCurrentItem() should be called after set the indicators (if using them),
        // or the initial selection wont match the real selected page
        mViewPager.setCurrentItem(0);
    }

    // todo temporal
    private void setPages() {
        int pages = 3;
        int margin = 40;
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = width / pages + (margin * 2 / pages);
        mViewPager.setClipChildren(true);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(padding, margin, padding, margin);
        mViewPager.setPageMargin(margin);
    }

    public void setFirstPageDataCallbacks(CustomViewPagerCallbacks callbacks) {
        mViewPager.setFirstPageData(callbacks);
    }

    public DemoDataHelper getFirstFragmentData() {
        if (mViewPager == null) return null;
        return (DemoDataHelper) mViewPager.getFirstPageData();
    }

    public void setLastPageDataCallbacks(CustomViewPagerCallbacks callbacks) {
        mViewPager.setLastPageData(callbacks);
    }

    public DemoDataHelper getLastFragmentData() {
        if (mViewPager == null) return null;
        return (DemoDataHelper) mViewPager.getLastPageData();
    }

    public int getPagesCount() {
        return mViewPager.getCount();
    }

    public int getRealCount() {
        return mViewPager.getRealCount();
    }

//    public PlaceholderFragment getHelperFirstFragment() {
//        return (PlaceholderFragment) mViewPager.getHelperFirstFragment();
//    }
//
//    public PlaceholderFragment getHelperLastFragment() {
//        return (PlaceholderFragment) mViewPager.getHelperLastFragment();
//    }
//
//    public void updateHelperFirstPageData(){
//        mViewPager.updateHelperFirstPageData();
//    }
//
//    public void updateHelperLastPageData(){
//        mViewPager.updateHelperLastPageData();
//    }


    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(getString(R.string.action_settings));
        alertDialogBuilder.setMessage(getString(R.string.app_name));
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTotalPages++;
                mSectionsPagerAdapter.setCount(mTotalPages);
                mViewPager.notifyDataSetChanged();
                PlaceholderFragment.showToast(MainActivity.this, "mTotalPages: " + mTotalPages);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setCancelable(true);
//        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}
