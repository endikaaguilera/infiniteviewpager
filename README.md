# Notice
Work in progress and undocummented atm.

# InfiniteViewPager
A Custom ViewPager Library for Android.

# Features
  - Infinite Pages (from first to last and vice versa) 
  - Page Indicators
  
# Usage
```java
private void initCustomViewPager() {

    // SectionsPagerAdapter that returns a fragment corresponding to one of the pages
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), mTotalPages);

    // Set up the ViewPager with the sections adapter.
    CustomViewPager viewPager = findViewById(R.id.container);
    viewPager.setAdapter(adapter);

    // Set up the ViewPager Indicators.
    ConstraintLayout parentContainer = findViewById(R.id.main_content);
    viewPager.initViewPagerIndicators(this, parentContainer);
    viewPager.setIndicatorsMode(POSITION_INCLUDE_BOTTOM, MODE_CLAMPED_HEIGHT, 2);  // optional

    // setCurrentItem() should be called after set the indicators (if using them),
    // or the initial selection wont match the real selected page
    viewPager.setCurrentItem(0);
}
```

# Art

## Infinite Scroll
![screen](../master/art/infinite_scroll.gif)

## Indicator Click
![screen](../master/art/indicators_click.gif)
