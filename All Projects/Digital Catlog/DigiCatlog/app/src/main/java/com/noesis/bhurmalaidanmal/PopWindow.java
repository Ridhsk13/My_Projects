package com.noesis.bhurmalaidanmal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by noesisimac on 10/4/16.
 */
public class PopWindow extends FragmentActivity
{

    ViewPager mViewPager;
    private ImageView rightButton;
    private ImageView leftButton;

    String[] title = {"Metallica Ring", "Engagement Ring", "Wedding Ring", "Knuckle Ring", "Mourning Ring", "Birthstone Ring"};

    private  int[] mResource={R.drawable.ring_1,R.drawable.ring_2,R.drawable.ring_3,R.drawable.ring_4,R.drawable.ring_5,R.drawable.ring_6};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout(width,(int)(height*.4));
        mViewPager=(ViewPager)findViewById(R.id.lightBox_gallery_viewPager_NEW);
        leftButton = (ImageView) findViewById(R.id.lightBox_gallery_leftButton);
        rightButton = (ImageView) findViewById(R.id.lightBox_gallery_rightButton);

        InfiniteViewPager  mCustomPagerAdapter = new InfiniteViewPager(this,mResource,title);

        mViewPager.setAdapter(mCustomPagerAdapter);

        //skip_button.setVisibility(View.GONE);


        leftButton.setOnClickListener(new View.OnClickListener() {
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

        rightButton.setOnClickListener(new View.OnClickListener() {
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
                    rightButton.setVisibility(View.GONE);
                    leftButton.setVisibility(View.VISIBLE);

                } else if (position == 0) {

                    leftButton.setVisibility(View.GONE);
                    rightButton.setVisibility(View.VISIBLE);
                } else {


                    leftButton.setVisibility(View.VISIBLE);
                    rightButton.setVisibility(View.VISIBLE);
                    // next_button.setLayoutParams(lp);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
}
