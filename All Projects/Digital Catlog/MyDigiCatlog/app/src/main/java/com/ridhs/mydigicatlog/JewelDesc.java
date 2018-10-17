package com.ridhs.mydigicatlog;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by noesisimac on 9/20/16.
 */
public class JewelDesc extends AppCompatActivity {
/*
    intent.putExtra("title",Title).putExtra("ruppees",Ruppees).putExtra("img",Image).putExtra("feat1",Feature1).putExtra("feat2",Feature2)
    .putExtra("feat3",Feature3).putExtra("Sellername",SellerName);

    */

    private GridViewAdapter gridAdapter;
    private ViewPager pager;
    GridView gridView;
    private ImageView next_button;
    private ImageView skip_button;

    String[] title = {"Metallica Ring", "Engagement Ring", "Wedding Ring", "Knuckle Ring", "Mourning Ring", "Birthstone Ring"};

    String[] title_forgrid = {"Metallica Ring", "Engagement Ring", "Wedding Ring", "Knuckle Ring", "Mourning Ring", "Birthstone Ring"};

    Intent n;

    int[] mResource = {
            R.drawable.ring_1,
            R.drawable.ring_2,
            R.drawable.ring_3,
            R.drawable.ring_4,
            R.drawable.ring_5,
            R.drawable.ring_6
    };
    int[] mResource_forgrid = {
            R.drawable.ring_1,
            R.drawable.ring_2,
            R.drawable.ring_3,
            R.drawable.ring_4,
            R.drawable.ring_5,
            R.drawable.ring_6
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.jewel_description_appbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        n = getIntent();
        int positionoflightboxImg = n.getIntExtra("positionoflightboximg", 0);


        pager = (ViewPager) findViewById(R.id.lightBox_gallery_viewPager);
        InfiniteViewPager mCustomPagerAdapter = new InfiniteViewPager(this, mResource, title);

        pager.setAdapter(mCustomPagerAdapter);
        pager.setCurrentItem(positionoflightboxImg);


        // indicator = (SmartTabLayout) findViewById(R.id.indicator);

        skip_button = (ImageView) findViewById(R.id.previous_btn);
        next_button = (ImageView) findViewById(R.id.next_btn);

        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");


        //next_button.setTypeface(normal_italic);skip_button.setTypeface(normal_italic);

        //skip_button.setVisibility(View.GONE);

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getCurrentItem() == 0) {

                    skip_button.setClickable(true);
                } else {
                    skip_button.setClickable(true);
                    pager.setCurrentItem(pager.getCurrentItem() - 1, true);
                }
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pager.getCurrentItem() == 5) {


                } else {
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
                    // next_button.setVisibility(View.GONE);

                } else if (position == 0) {

                    //  skip_button.setVisibility(View.GONE);
                } else {


                    skip_button.setVisibility(View.VISIBLE);
                    next_button.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.getCurrentItem();

            }
        });

// ,image to add in constructor below
        gridAdapter = new GridViewAdapter(this, title_forgrid, mResource_forgrid);
        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setFocusable(false);
        gridView.setAdapter(gridAdapter);


        n = getIntent();
        String set_Title = n.getStringExtra("title");

        String set_Ruppees = n.getStringExtra("ruppees");

        int set_Image = n.getIntExtra("img", 0);

        int setDemoImg1 = n.getIntExtra("demo1", 0);

        int setDemoImg2 = n.getIntExtra("demo2", 0);

        int setDemoImg3 = n.getIntExtra("demo3", 0);


        String set_Description = n.getStringExtra("desc");

        TextView title = (TextView) findViewById(R.id.setTITLE);
        title.setTypeface(normal_italic);
        TextView ruppee = (TextView) findViewById(R.id.setRUPPEES);
        ruppee.setTypeface(normal_bold);

        final ImageView image = (ImageView) findViewById(R.id.setIMAGE);
        ImageView demoImage1 = (ImageView) findViewById(R.id.demo_img1);
        ImageView demoImage2 = (ImageView) findViewById(R.id.demo_img2);
        ImageView demoImage3 = (ImageView) findViewById(R.id.demo_img3);
        //ImageView demoImage4=(ImageView)findViewById(R.id.demo_img4);

        Button view_catalog = (Button) findViewById(R.id.view_catalog);
        view_catalog.setTypeface(normal_italic);


        TextView description = (TextView) findViewById(R.id.desc);
        description.setText(Html.fromHtml(set_Description));
        description.setTypeface(normal);
        TextView related_product = (TextView) findViewById(R.id.related_product);
        related_product.setTypeface(normal_italic);

        title.setText(set_Title);
        ruppee.setText(set_Ruppees);
        image.setImageResource(set_Image);

        demoImage1.setImageResource(setDemoImg1);
        demoImage2.setImageResource(setDemoImg2);
        demoImage3.setImageResource(setDemoImg3);

        demoImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageResource(R.drawable.ring_1);
            }
        });


        demoImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageResource(R.drawable.ring_3);
            }
        });


        demoImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageResource(R.drawable.ring_6);
            }
        });

        view_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(JewelDesc.this, WebView1.class);

                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.finish();
        // Use exiting animations specified by the parent activity if given
        // Translate left if not specified.
        overridePendingTransition(
                getIntent().getIntExtra("anim id in", R.anim.down_in),
                getIntent().getIntExtra("anim id out", R.anim.down_out));
    }


}
