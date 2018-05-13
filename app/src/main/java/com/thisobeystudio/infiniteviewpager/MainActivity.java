package com.thisobeystudio.infiniteviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thisobeystudio.infiniteviewpager.basicdemo.BasicUsageActivity;
import com.thisobeystudio.infiniteviewpager.basicdemo.IndicatorsActivity;
import com.thisobeystudio.infiniteviewpager.complexdemo.ComplexActivity;
import com.thisobeystudio.infiniteviewpager.scrollabledemo.ScrollableActivity;

/**
 * Created by thisobeystudio on 10/5/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.app_name));

        findViewById(R.id.basic_btn).setOnClickListener(this);
        findViewById(R.id.indicators_btn).setOnClickListener(this);
        findViewById(R.id.complex_btn).setOnClickListener(this);
        findViewById(R.id.scrollable_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        final int id = v.getId();

        switch (id) {
            case R.id.basic_btn:
                intent = new Intent(MainActivity.this, BasicUsageActivity.class);
                startActivity(intent);
                break;
            case R.id.indicators_btn:
                intent = new Intent(MainActivity.this, IndicatorsActivity.class);
                startActivity(intent);
                break;
            case R.id.scrollable_btn:
                intent = new Intent(MainActivity.this, ScrollableActivity.class);
                startActivity(intent);
                break;
            case R.id.complex_btn:
                intent = new Intent(MainActivity.this, ComplexActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
