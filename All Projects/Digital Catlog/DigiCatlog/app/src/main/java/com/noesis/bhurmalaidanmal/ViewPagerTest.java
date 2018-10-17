package com.noesis.bhurmalaidanmal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

/**
 * Created by noesisimac on 10/3/16.
 */
public class ViewPagerTest extends FragmentActivity {

    ViewPager mViewPager;
    private Button next_button;
    private Button skip_button;

    String[] title = {"Metallica Ring", "Engagement Ring", "Wedding Ring", "Knuckle Ring", "Mourning Ring", "Birthstone Ring"};

    private  int[] mResource={R.drawable.ring_1,R.drawable.ring_2,R.drawable.ring_3,R.drawable.ring_4,R.drawable.ring_5,R.drawable.ring_6};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lightbox_gallery);

        mViewPager=(ViewPager)findViewById(R.id.lightBox_gallery_viewPager);
        skip_button = (Button) findViewById(R.id.previous_btn);
        next_button = (Button) findViewById(R.id.next_btn);

      InfiniteViewPager  mCustomPagerAdapter = new InfiniteViewPager(this,mResource,title);

        mViewPager.setAdapter(mCustomPagerAdapter);

        //skip_button.setVisibility(View.GONE);


        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finishOnboarding();
                if (mViewPager.getCurrentItem() == 0) {

                    //skip_button.setVisibility(View.GONE);

                } else {
                    //skip_button.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
                //onBackPressed();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mViewPager.getCurrentItem() == 5) {
/*
                    next_button.setVisibility(View.GONE);
*/
                    //  finishOnboarding();
                } else {
/*
                    next_button.setVisibility(View.VISIBLE);
*/
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 5) {
                    next_button.setVisibility(View.GONE);
                    skip_button.setVisibility(View.VISIBLE);

                } else if (position == 0) {

                    skip_button.setVisibility(View.GONE);
                    next_button.setVisibility(View.VISIBLE);
                } else {


                    skip_button.setVisibility(View.VISIBLE);
                    next_button.setVisibility(View.VISIBLE);
                    // next_button.setLayoutParams(lp);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }




}


