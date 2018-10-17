package com.noesis.bhurmalaidanmal;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListView exList;
    ViewPager pager;
    //private List<Category> catList;
    String menu1[];
    DrawerLayout drawer;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    ViewPager pager_forhorizontalScroll;
    int[] mResource = {
            R.drawable.ring_1,
            R.drawable.ring_2,
            R.drawable.ring_3,
            R.drawable.ring_4,
            R.drawable.ring_5,
            R.drawable.ring_6
    };
    String[] title = {"Metallica Ring", "Engagement Ring", "Wedding Ring", "Knuckle Ring", "Mourning Ring", "Birthstone Ring"};


    ImageView bannerIndicator1, bannerIndicator2, bannerIndicator3;

    private int imageArra[] = {R.drawable.banner1, R.drawable.banner_2, R.drawable.banner_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Intent b=getIntent();

        StartAnimations();
        //String animation_signal=b.getStringExtra("Animate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PagerAdapter adapter = new InfinitePagerAdapter(new ImagePagerAdapter(this, imageArra));
        // PagerAdapter adapter=new ImagePagerAdapter(this,imageArra);
        pager = (ViewPager) findViewById(R.id.schedule_viewPager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        pager_forhorizontalScroll = (ViewPager) findViewById(R.id.horizon_lightBox_gallery_viewPager);
        InfiniteViewPager_Horizon mCustomPagerAdapter = new InfiniteViewPager_Horizon(this, mResource, title);

        pager_forhorizontalScroll.setAdapter(mCustomPagerAdapter);

        pager_forhorizontalScroll.setPageMargin(20);
        pager_forhorizontalScroll.setPageMarginDrawable(android.R.color.transparent);


        menu1 = new String[]{"Home", "About Us", "Contact Us", "Our Products",};
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundColor(getResources().getColor(R.color.border));


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menuicon);
        //drawer.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        exList = (ExpandableListView) findViewById(R.id.navigationmenu);
        //exList.setIndicatorBounds(7, 7);
        exList.setIndicatorBounds(1, 1);
        exList.setChildDivider(getResources().getDrawable(android.R.color.transparent));
        exList.setDivider(getResources().getDrawable(R.color.lightmaroon));
        exList.setDividerHeight(4);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Home");
        listDataHeader.add("About Us");
        listDataHeader.add("Contact Us");
        listDataHeader.add("Our Products");
        //listDataHeader.add("Contact Us");


        List<String> ourProducts = new ArrayList<String>();
        ourProducts.add("Rings");
        ourProducts.add("Necklaces");
        ourProducts.add("Ear Rings");
        ourProducts.add("Bangles");

        listDataChild.put(listDataHeader.get(3), ourProducts);


        ExpandableAdapter exAdpt = new ExpandableAdapter(this, listDataHeader, listDataChild);


        exList.setAdapter(exAdpt);
        exList.expandGroup(3);
        /*int count = exAdpt.getGroupCount();
        for ( int i = 0; i < count; i++ ) {
            exList.expandGroup(i);
        }*/


        Button viewmore_one = (Button) findViewById(R.id.viewmore_button1);
        Button viewmore_two = (Button) findViewById(R.id.viewmore_button2);
        Button viewmore_three = (Button) findViewById(R.id.viewmore_button3);
        Button viewmore_four = (Button) findViewById(R.id.viewmore_button4);
        Button viewmore_five = (Button) findViewById(R.id.viewmore_button5);
        Button viewmore_six = (Button) findViewById(R.id.viewmore_button6);

        TextView settings1 = (TextView) findViewById(R.id.scroll1);

        TextView settings2 = (TextView) findViewById(R.id.scroll2);
        TextView settings3 = (TextView) findViewById(R.id.scroll3);
        TextView settings4 = (TextView) findViewById(R.id.scroll4);
        TextView settings5 = (TextView) findViewById(R.id.scroll5);
        TextView settings6 = (TextView) findViewById(R.id.scroll6);

        TextView banner_bangles_text = (TextView) findViewById(R.id.rel1_text);
        TextView banner_rings_text = (TextView) findViewById(R.id.rel2_text);
        TextView banner_earrings_text = (TextView) findViewById(R.id.rel3_text);
        TextView banner_necklace_text = (TextView) findViewById(R.id.rel4_text);


        RelativeLayout horizontalView_Image1 = (RelativeLayout) findViewById(R.id.horizontal_layout_img1);
        RelativeLayout horizontalView_Image2 = (RelativeLayout) findViewById(R.id.horizontal_layout_img2);
        RelativeLayout horizontalView_Image3 = (RelativeLayout) findViewById(R.id.horizontal_layout_img3);
        RelativeLayout horizontalView_Image4 = (RelativeLayout) findViewById(R.id.horizontal_layout_img4);
        RelativeLayout horizontalView_Image5 = (RelativeLayout) findViewById(R.id.horizontal_layout_img5);
        RelativeLayout horizontalView_Image6 = (RelativeLayout) findViewById(R.id.horizontal_layout_img6);

        bannerIndicator1 = (ImageView) findViewById(R.id.banner_indicator1);
        bannerIndicator2 = (ImageView) findViewById(R.id.banner_indicator2);
        bannerIndicator3 = (ImageView) findViewById(R.id.banner_indicator3);

        final ImageView scroll_left_horizontalView = (ImageView) findViewById(R.id.left_butn_horizontalView);
        final ImageView scroll_right_horizontalView = (ImageView) findViewById(R.id.right_butn_horizontalView);

        scroll_left_horizontalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager_forhorizontalScroll.getCurrentItem() == 0) {

                    scroll_left_horizontalView.setClickable(true);
                } else {
                    scroll_left_horizontalView.setClickable(true);
                    pager_forhorizontalScroll.setCurrentItem(pager_forhorizontalScroll.getCurrentItem() - 1, true);
                }
            }
        });

        scroll_right_horizontalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pager_forhorizontalScroll.getCurrentItem() == 5) {


                } else {
                    pager_forhorizontalScroll.setCurrentItem(pager_forhorizontalScroll.getCurrentItem() + 1, true);
                }
            }
        });


        final HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.scroll_horizontalView);

        TextView text_pattern_new = (TextView) findViewById(R.id.test_pattern1);
        TextView text_pattern_Products = (TextView) findViewById(R.id.test_pattern2);


        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");


        String example = "<html>New Collections of<br><b>BANGLES</b><br><font color='#000000'>&lt; View Details</font></html>";
        banner_bangles_text.setText(Html.fromHtml(example));
        banner_bangles_text.setTypeface(normal_italic);
        banner_bangles_text.setTextColor(getResources().getColor(R.color.maroon));

        String example2 = "<html>Large Range of<br><b>RINGS</b><br><font color='#000000'>&lt; View Details</font></html>";
        banner_rings_text.setText(Html.fromHtml(example2));
        banner_rings_text.setTypeface(normal_italic);
        banner_rings_text.setTextColor(getResources().getColor(R.color.maroon));

        text_pattern_new.setTypeface(normal_italic);
        text_pattern_Products.setTypeface(bold_italic);


        String example3 = "<html>Diamond<br><b>EAR RINGS</b><br><font color='#000000'>&lt; View Details</font></html>";
        banner_earrings_text.setText(Html.fromHtml(example3));
        banner_earrings_text.setTypeface(normal_italic);
        banner_earrings_text.setTextColor(getResources().getColor(R.color.maroon));


        String example4 = "<html>Collections of<br><b>NECKLACES</b><br><font color='#000000'>&lt; View Details</font></html>";
        banner_necklace_text.setText(Html.fromHtml(example4));
        banner_necklace_text.setTypeface(normal_italic);
        banner_necklace_text.setTextColor(getResources().getColor(R.color.maroon));


       /* viewmore_one.setTypeface(normal_italic);
        viewmore_two.setTypeface(normal_italic);
        viewmore_three.setTypeface(normal_italic);
        viewmore_four.setTypeface(normal_italic);
        viewmore_five.setTypeface(normal_italic);
        viewmore_six.setTypeface(normal_italic);*/


       /* settings1.setTypeface(normal);

        settings2.setTypeface(normal);

        settings3.setTypeface(normal);

        settings4.setTypeface(normal);

        settings5.setTypeface(normal);

        settings6.setTypeface(normal);*/


        /*viewmore_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Metallic Ring";
                String Ruppees = "5,000/-";
                int Image = R.drawable.ring_1;
                String Feature1 = "Gold Purity: 18kt";
                String Feature2 = "Platinium Purity: NA";
                String Feature3 = "Setting: Prong";
                String SellerName = "Kalyan Jewelers";
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                int demo_img1 = R.drawable.thumbnail_ring1;
                int demo_img2 = R.drawable.thumbnail_ring1;
                int demo_img3 = R.drawable.thumbnail_ring1;
                int demo_img4 = R.drawable.thumbnail_ring1;

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });
        horizontalView_Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = "Metallic Ring";
                String Ruppees = "5,000/-";
                int Image = R.drawable.ring_1;
                String Feature1 = "Gold Purity: 18kt";
                String Feature2 = "Platinium Purity: NA";
                String Feature3 = "Setting: Prong";
                String SellerName = "Kalyan Jewelers";
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                int demo_img1 = R.drawable.thumbnail_ring1;
                int demo_img2 = R.drawable.thumbnail_ring1;
                int demo_img3 = R.drawable.thumbnail_ring1;
                int demo_img4 = R.drawable.thumbnail_ring1;

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);
            }
        });

        viewmore_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Engagement Ring";
                String Ruppees = "15,000/-";
                int Image = R.drawable.ring_2;
                String Feature1 = "Gold Purity: 15kt";
                String Feature2 = "Platinium Purity: NA";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring2;
                int demo_img2 = R.drawable.thumbnail_ring2;
                int demo_img3 = R.drawable.thumbnail_ring2;
                int demo_img4 = R.drawable.thumbnail_ring2;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }
        });

        horizontalView_Image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Engagement Ring";
                String Ruppees = "15,000/-";
                int Image = R.drawable.ring_2;
                String Feature1 = "Gold Purity: 15kt";
                String Feature2 = "Platinium Purity: NA";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring2;
                int demo_img2 = R.drawable.thumbnail_ring2;
                int demo_img3 = R.drawable.thumbnail_ring2;
                int demo_img4 = R.drawable.thumbnail_ring2;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }
        });


        viewmore_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Wedding Ring";
                String Ruppees = "17,000/-";
                int Image = R.drawable.ring_3;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 20%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring3;
                int demo_img2 = R.drawable.thumbnail_ring3;
                int demo_img3 = R.drawable.thumbnail_ring3;
                int demo_img4 = R.drawable.thumbnail_ring3;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }
        });
        horizontalView_Image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Wedding Ring";
                String Ruppees = "17,000/-";
                int Image = R.drawable.ring_3;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 20%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring3;
                int demo_img2 = R.drawable.thumbnail_ring3;
                int demo_img3 = R.drawable.thumbnail_ring3;
                int demo_img4 = R.drawable.thumbnail_ring3;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }
        });

        viewmore_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Knuckle Ring";
                String Ruppees = "3,999/-";
                int Image = R.drawable.ring_4;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                int demo_img1 = R.drawable.thumbnail_ring4;
                int demo_img2 = R.drawable.thumbnail_ring4;
                int demo_img3 = R.drawable.thumbnail_ring4;
                int demo_img4 = R.drawable.thumbnail_ring4;

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });

        horizontalView_Image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Knuckle Ring";
                String Ruppees = "3,999/-";
                int Image = R.drawable.ring_4;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                int demo_img1 = R.drawable.thumbnail_ring4;
                int demo_img2 = R.drawable.thumbnail_ring4;
                int demo_img3 = R.drawable.thumbnail_ring4;
                int demo_img4 = R.drawable.thumbnail_ring4;

                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });
        viewmore_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Mourning Ring";
                String Ruppees = "3,999/-";
                int Image = R.drawable.ring_5;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring5;
                int demo_img2 = R.drawable.thumbnail_ring5;
                int demo_img3 = R.drawable.thumbnail_ring5;
                int demo_img4 = R.drawable.thumbnail_ring5;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }

        });


        horizontalView_Image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Mourning Ring";
                String Ruppees = "3,999/-";
                int Image = R.drawable.ring_5;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring5;
                int demo_img2 = R.drawable.thumbnail_ring5;
                int demo_img3 = R.drawable.thumbnail_ring5;
                int demo_img4 = R.drawable.thumbnail_ring5;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);


                startActivity(intent);

            }
        });

        viewmore_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Birthstone Ring";
                String Ruppees = "7,999/-";
                int Image = R.drawable.ring_6;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring6;
                int demo_img2 = R.drawable.thumbnail_ring6;
                int demo_img3 = R.drawable.thumbnail_ring6;
                int demo_img4 = R.drawable.thumbnail_ring6;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });

        horizontalView_Image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title = "Birthstone Ring";
                String Ruppees = "7,999/-";
                int Image = R.drawable.ring_6;
                String Feature1 = "Gold Purity: 5kt";
                String Feature2 = "Platinium Purity: 80%";
                String Feature3 = "Setting: Prong";
                String SellerName = "Chintamani";
                int demo_img1 = R.drawable.thumbnail_ring6;
                int demo_img2 = R.drawable.thumbnail_ring6;
                int demo_img3 = R.drawable.thumbnail_ring6;
                int demo_img4 = R.drawable.thumbnail_ring6;
                String desc = "<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent = new Intent(MainActivity.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });*/

        int[] animator = new int[]{800, 800};
        /*scroll_right_horizontalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_right_horizontalView.setImageResource(R.drawable.right_arrow_hover);
                scroll_left_horizontalView.setImageResource(R.drawable.left_arrow);
                ObjectAnimator animator = ObjectAnimator.ofInt(horizontalScrollView, "scrollX", 2400);
                animator.setDuration(2200);
                animator.start();
                //horizontalScrollView.scrollTo(horizontalScrollView.getScrollX()+100,horizontalScrollView.getScrollY());
            }
        });

        scroll_left_horizontalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_right_horizontalView.setImageResource(R.drawable.right_arrow);
                scroll_left_horizontalView.setImageResource(R.drawable.left_arrow_hover);
                ObjectAnimator animator = ObjectAnimator.ofInt(horizontalScrollView, "scrollX", -800);
                animator.setDuration(1200);
                animator.start();
                //horizontalScrollView.scrollTo(horizontalScrollView.getScrollX() - 100, horizontalScrollView.getScrollY());
            }
        });*/

/*        scroll_right_horizontalView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                horizontalScrollView.scrollTo(horizontalScrollView.getScrollX()+100+100+100,horizontalScrollView.getScrollY());
                return false;
            }
        });*/


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_light);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_dark);

                } else if (position == 1) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_light);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_dark);
                } else if (position == 2) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_light);
                } else if (position % 3 == 0) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_light);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_dark);
                } else if ((position - 1) % 3 == 0) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_light);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_dark);
                } else if ((position - 2) % 3 == 0) {
                    bannerIndicator1.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator2.setImageResource(R.drawable.banner_indicator_dark);
                    bannerIndicator3.setImageResource(R.drawable.banner_indicator_light);
                } else
                    Log.d("Error", "dasba");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        exList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                           @Override
                                           public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                                               RelativeLayout tv = (RelativeLayout) v;
                                               Bundle args = new Bundle();
                                               args.putString("menu_name", menu1[groupPosition]);


                                               String menu_name = exList.getItemAtPosition(groupPosition).toString();


                                               switch (menu_name) {

                                                   case "Home":
                                                       //tv.setBackgroundColor(getResources().getColor(R.color.lightmaroon));
                                                       //Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                                                       Intent i = new Intent(MainActivity.this, MainActivity.class);
                                                       startActivity(i);
                                                       drawer.closeDrawers();


                                                       break;

                                                   case "About Us":
                                                       // about us code

                                                       Intent intent = new Intent(MainActivity.this, AboutUs.class);
                                                       startActivity(intent);
                                                       //drawer.closeDrawers();
                                                       break;

                                                   case "Our Products":
                                                       //expandable code
                                                       //startActivity(new Intent(MainActivity.this,TrailRegister.class).putExtra("title","SCHEDULE").putExtra("name","child 0"));

                                                       //  drawer.closeDrawers();
                                                       break;

                                                   case "Contact Us":
                                                       // Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_LONG).show();

                                                       Intent in = new Intent(MainActivity.this, ContactUs.class);
                                                       startActivity(in);
                                                       // drawer.closeDrawers();
                                                       break;


                                               }
                                               //
                                               int in = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                                               parent.setItemChecked(in, true);

                                               int s = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(3));
                                               parent.setItemChecked(s, false);
                                               // int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(group, child));
                                               return true;

                                           }
                                       }

        );


        exList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long l) {

                if (child == 0) {
                    String setTitle = "  Rings  ";
                    Intent i = new Intent(MainActivity.this, FragmentBangles.class).putExtra("product_title", setTitle);
                    startActivity(i);
                    //  drawer.closeDrawers();

                } else if (child == 1) {
                    String setTitle = "  Necklaces  ";
                    Intent i = new Intent(MainActivity.this, FragmentBangles.class).putExtra("product_title", setTitle);
                    startActivity(i);
                    //drawer.closeDrawers();

                } else if (child == 2) {
                    String setTitle = "  Ear Rings  ";
                    Intent i = new Intent(MainActivity.this, FragmentBangles.class).putExtra("product_title", setTitle);
                    startActivity(i);
                    // drawer.closeDrawers();

                } else if (child == 3) {
                    String setTitle = "  Bangles  ";
                    Intent i = new Intent(MainActivity.this, FragmentBangles.class).putExtra("product_title", setTitle);
                    startActivity(i);
                    // drawer.closeDrawers();

                } else
                    Log.d("Error", "errrorrr");


                int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(group, child));
                expandableListView.setItemChecked(index, true);

                return true;
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            navigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    return insets;
                }
            });
        }


        navigationView.setNavigationItemSelectedListener(this);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    private void StartAnimations() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_in);
        anim.reset();
        ScrollView relativeLayout = (ScrollView) findViewById(R.id.anim_layout);
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(anim);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();

                        }
                    }).setNegativeButton("No", null).show();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

  /*  public void onBackPressed1() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                }).setNegativeButton("No", null).show();
    }*/

    //  private  void postDelayed(Runnable r,long init){}


}
