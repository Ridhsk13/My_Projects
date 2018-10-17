package com.noesis.bhurmalaidanmal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


/**
 * Created by noesisimac on 9/13/16.
 */
public class LightBoxGallery extends FragmentActivity {

    private ViewPager pager;

    private Button next_button;
    private Button skip_button;
    //  private int lightbox_imgs[]={R.drawable.ring_1,R.drawable.ring_2,R.drawable.ring_3,R.drawable.ring_4,R.drawable.ring_5,R.drawable.ring_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lightbox_gallery);

        pager = (ViewPager) findViewById(R.id.lightBox_gallery_viewPager);
        // indicator = (SmartTabLayout) findViewById(R.id.indicator);


        // indicator.setViewPager(pager);

        skip_button.setVisibility(View.GONE);

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finishOnboarding();
                if (pager.getCurrentItem() == 0) {

                    //skip_button.setVisibility(View.GONE);

                } else {
                    //skip_button.setVisibility(View.VISIBLE);
                    pager.setCurrentItem(pager.getCurrentItem() - 1, true);
                }
                //onBackPressed();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pager.getCurrentItem() == 5) {
/*
                    next_button.setVisibility(View.GONE);
*/
                    //  finishOnboarding();
                } else {
/*
                    next_button.setVisibility(View.VISIBLE);
*/
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 5) {
                    next_button.setVisibility(View.GONE);

                } else if (position == 0) {

                    skip_button.setVisibility(View.GONE);
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
