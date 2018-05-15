# Notice
Work in progress and pending to properly document.

# InfiniteViewPager
A Custom `ViewPager` Library for `Android`.

# Features
  - Infinite Pages (from first to last and vice versa) 
  - Page Indicators
  - Single Page Supported
  
# Download

## Gradle

- Step 1. Add the JitPack repository to your build file. Add it in your root `build.gradle` at the end of repositories:
```
  allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
  }
```

- Step 2. Add the dependency
```
  dependencies {
            implementation 'com.github.EndikaAguilera:InfiniteViewPager:v1.0-beta.3'
  }
```

## Maven
- Step 1. Add the JitPack repository to your build file.
```
  dependencies {
            implementation 'com.github.EndikaAguilera:InfiniteViewPager:v1.0-beta.3'
  }
```
- Step 2. Add the dependency
```
	<dependency>
	    <groupId>com.github.EndikaAguilera</groupId>
	    <artifactId>InfiniteViewPager</artifactId>
	    <version>v1.0-beta.1</version>
	</dependency>
```

# Usage

## Requirements:

- Replace
	- `ViewPager` to `CustomViewPager`
	- `PagerAdapter` to `CustomPagerAdapter`
	
- Optional
	- `Fragment` must extend `CustomFragment` when using callbacks for (first and last helper pages)
    
## XML
```xml
<com.customviewpager.viewpager.CustomViewPager
    android:id="@+id/container"
    android:layout_width="0dp"
    android:layout_height="0dp"
    android:overScrollMode="never"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
## Java
```java
private void initCustomViewPager() {

    // SectionsPagerAdapter that returns a fragment corresponding to one of the pages
    SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), YOUR_DATA);

    // Set up the ViewPager with the sections adapter.
    CustomViewPager viewPager = findViewById(R.id.YOUR_CUSTOM_VIEW_PAGER_ID);
    viewPager.setAdapter(adapter);

    // Set up the ViewPager Indicators. Note that requires a ConstraintLayout as parent.
    ConstraintLayout parentContainer = findViewById(R.id.YOUR_PARENT_CONSTRAINT_LAYOUT);
    viewPager.initViewPagerIndicators(context, parentContainer);
    final int maxIndicatorRows = 2;
    viewPager.setIndicatorsMode(POSITION_INCLUDE_BOTTOM, MODE_CLAMPED_HEIGHT, maxIndicatorRows);  // optional

    // setCurrentItem() should be called after set the indicators (if using them),
    // or the initial selection wont match the real selected page
    viewPager.setCurrentItem(0);
}
```

# Page Indicators Features
## Options
  - Page Selection.
  - AutoScroll to selected item when needed.
  - Customization. 
    - Size.
    - Position. (x4)
    - Height Adjust Modes. (x3)
    - Max items per row.
    - Colors and/or Drawable.

## Position
```java
    - POSITION_FLOAT_TOP        // both view's shares top postion, so indicators are 'inside' the CustomViewPager
    - POSITION_FLOAT_BOTTOM     // both view's shares bottom postion, so indicators are 'inside' the CustomViewPager
    - POSITION_INCLUDE_TOP      // CustomViewPager's top position will be connected to indicators bottom position 
    - POSITION_INCLUDE_BOTTOM   // CustomViewPager's bottom position will be connected to indicators top position
 ```
 
## Height Adjust Mode 
```java
    - MODE_WRAP_HEIGHT          // from 1 to infinite based on rows count
    - MODE_FIXED_HEIGHT         // itemHeight * (margin * 2) * maxVisibleIndicatorRows
    - MODE_CLAMPED_HEIGHT       // from 1 to maxVisibleIndicatorRows
```

## Indicators Cutomization
You can override any of the following resouces as desired.

### Colors
```java
    <color name="indicatorNormal">YOUR_COLOR</color>
    <color name="indicatorPressed">YOUR_COLOR</color>
    <color name="indicatorSelected">YOUR_COLOR</color>
```

### Dimens
```java
    <dimen name="indicator_vertical_padding">YOUR_VALUE</dimen>
    <dimen name="indicator_horizontal_margin">YOUR_VALUE</dimen>
    <dimen name="indicator_item_size">YOUR_VALUE</dimen>
    <dimen name="indicator_item_padding">YOUR_VALUE</dimen>
```

### Drawable
```java
    ic_indicator.xml
```

# Art

![alt-text-1](https://raw.githubusercontent.com/EndikaAguilera/MyReposAssets/master/infinite_view_pager/basic.gif "title-1") ![alt-text-2](https://raw.githubusercontent.com/EndikaAguilera/MyReposAssets/master/infinite_view_pager/indicators.gif "title-2")

## Basic
![ezgif com-optimize](https://raw.githubusercontent.com/EndikaAguilera/MyReposAssets/master/infinite_view_pager/basic.gif)

## Indicators
![ezgif com-optimize](https://raw.githubusercontent.com/EndikaAguilera/MyReposAssets/master/infinite_view_pager/indicators.gif)

## Scroll (using callbacks)
![ezgif com-optimize](https://raw.githubusercontent.com/EndikaAguilera/MyReposAssets/master/infinite_view_pager/scroll.gif)
