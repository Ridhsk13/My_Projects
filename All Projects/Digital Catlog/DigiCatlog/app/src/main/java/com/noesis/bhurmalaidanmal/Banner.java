package com.noesis.bhurmalaidanmal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Created by noesisimac on 9/19/16.
 */
public class Banner extends FragmentActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        pager = (ViewPager) findViewById(R.id.schedule_viewPager);



        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 3;
            }


            @Override
            public Fragment getItem(int position) {

                switch (position) {

                    case 0:
                        Banner1 myFragment1 = new Banner1();
                        Bundle data = new Bundle();
                        data.putInt("current_page", position+1);
                        myFragment1.setArguments(data);
                        return myFragment1;

                    case 1:
                        return new Banner2();
                    case 2:
                        return new Banner3();


                    default:
                        return null;
                }

            }


        };

        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        // indicator.setViewPager(pager);

       // skip_button.setVisibility(View.GONE);

      /*  skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // finishOnboarding();
                if (pager.getCurrentItem() == 0) {
                    //  finishOnboarding();
                    //skip_button.setText("SKIP");

                    onBackPressed();

                } else {
                    pager.setCurrentItem(pager.getCurrentItem() - 1, true);
                }
                //onBackPressed();
            }
        });*/

/*
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pager.getCurrentItem() == 7) {
                    //  finishOnboarding();
                    onBackPressed();
                } else {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }
            }
        });
*/

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

/*
                if (position == 7) {
                    //skip_button.setVisibility(View.GONE);

                    //next_button.setText("OK");

                } else if(position==0){

                    //skip_button.setVisibility(View.GONE);
                }

                else {


                   // skip_button.setVisibility(View.VISIBLE);

                    // next_button.setLayoutParams(lp);
                   // next_button.setText("Next");

                }
*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }



        });

       // pager.addOnPageChangeListener(new CircularViewPageHandler(pager));



    }
}
