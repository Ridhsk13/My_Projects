package com.noesis.bhurmalaidanmal;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import junit.framework.TestCase;


/**
 * Created by noesisimac on 9/19/16.
 */
public class Test extends FragmentActivity {

    private ViewPager pager;
 //   private SmartTabLayout indicator;
 private int imageArra[] = { R.drawable.banner1, R.drawable.banner_2,R.drawable.banner_3};




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        PagerAdapter adapter=new InfinitePagerAdapter(new ImagePagerAdapter(this,imageArra));
        pager = (ViewPager) findViewById(R.id.schedule_viewPager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

       /* android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));*/

        Button viewmore_one=(Button)findViewById(R.id.viewmore_button1);
        Button viewmore_two=(Button)findViewById(R.id.viewmore_button2);
        Button viewmore_three=(Button)findViewById(R.id.viewmore_button3);
        Button viewmore_four=(Button)findViewById(R.id.viewmore_button4);
        Button viewmore_five=(Button)findViewById(R.id.viewmore_button5);
        Button viewmore_six=(Button)findViewById(R.id.viewmore_button6);

        TextView settings1=(TextView)findViewById(R.id.scroll1);

        TextView settings2=(TextView)findViewById(R.id.scroll2);
        TextView settings3=(TextView)findViewById(R.id.scroll3);
        TextView settings4=(TextView)findViewById(R.id.scroll4);
        TextView settings5=(TextView)findViewById(R.id.scroll5);
        TextView settings6=(TextView)findViewById(R.id.scroll6);

        TextView banner_bangles_text=(TextView)findViewById(R.id.rel1_text);
        TextView banner_rings_text=(TextView)findViewById(R.id.rel2_text);
        TextView banner_earrings_text=(TextView)findViewById(R.id.rel3_text);
        TextView banner_necklace_text=(TextView)findViewById(R.id.rel4_text);


        TextView text_pattern_new=(TextView)findViewById(R.id.test_pattern1);
        TextView text_pattern_Products=(TextView)findViewById(R.id.test_pattern2);


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



        viewmore_one.setTypeface(normal_italic);
        viewmore_two.setTypeface(normal_italic);
        viewmore_three.setTypeface(normal_italic);
        viewmore_four.setTypeface(normal_italic);
        viewmore_five.setTypeface(normal_italic);
        viewmore_six.setTypeface(normal_italic);


        settings1.setTypeface(normal);

        settings2.setTypeface(normal);

        settings3.setTypeface(normal);

        settings4.setTypeface(normal);

        settings5.setTypeface(normal);

        settings6.setTypeface(normal);



        viewmore_one.setOnClickListener(new View.OnClickListener() {
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

                Intent intent = new Intent(Test.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });

        viewmore_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title="Wedding Ring";
                String Ruppees="15,000/-";
                int Image=R.drawable.ring_2;
                String Feature1="Gold Purity: 15kt";
                String Feature2="Platinium Purity: NA";
                String Feature3="Setting: Prong";
                String SellerName="Chintamani";
                int demo_img1=R.drawable.thumbnail_ring2;
                int demo_img2=R.drawable.thumbnail_ring2;
                int demo_img3=R.drawable.thumbnail_ring2;
                int demo_img4=R.drawable.thumbnail_ring2;
                String desc="<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";

                Intent intent=new Intent(Test.this,JewelDesc.class);

                intent.putExtra("title",Title).putExtra("ruppees", Ruppees).putExtra("img",Image).putExtra("feat1",Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1",demo_img1).putExtra("demo2",demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc",desc);


                startActivity(intent);

            }
        });

        viewmore_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title="Engagement Ring";
                String Ruppees="17,000/-";
                int Image=R.drawable.ring_3;
                String Feature1="Gold Purity: 5kt";
                String Feature2="Platinium Purity: 20%";
                String Feature3="Setting: Prong";
                String SellerName="Chintamani";
                int demo_img1=R.drawable.thumbnail_ring3;
                int demo_img2=R.drawable.thumbnail_ring3;
                int demo_img3=R.drawable.thumbnail_ring3;
                int demo_img4=R.drawable.thumbnail_ring3;
                String desc="<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent=new Intent(Test.this,JewelDesc.class);

                intent.putExtra("title",Title).putExtra("ruppees", Ruppees).putExtra("img",Image).putExtra("feat1",Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1",demo_img1).putExtra("demo2",demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc",desc);


                startActivity(intent);

            }
        });

        viewmore_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title="Knuckle Ring";
                String Ruppees="3,999/-";
                int Image=R.drawable.ring_4;
                String Feature1="Gold Purity: 5kt";
                String Feature2="Platinium Purity: 80%";
                String Feature3="Setting: Prong";
                String SellerName="Chintamani";
                String desc="<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                int demo_img1=R.drawable.thumbnail_ring4;
                int demo_img2=R.drawable.thumbnail_ring4;
                int demo_img3=R.drawable.thumbnail_ring4;
                int demo_img4=R.drawable.thumbnail_ring4;

                Intent intent=new Intent(Test.this,JewelDesc.class);

                intent.putExtra("title",Title).putExtra("ruppees", Ruppees).putExtra("img",Image).putExtra("feat1",Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1",demo_img1).putExtra("demo2",demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc",desc);

                startActivity(intent);

            }
        });

        viewmore_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Title="Mourning Ring";
                String Ruppees="3,999/-";
                int Image=R.drawable.ring_5;
                String Feature1="Gold Purity: 5kt";
                String Feature2="Platinium Purity: 80%";
                String Feature3="Setting: Prong";
                String SellerName="Chintamani";
                int demo_img1=R.drawable.thumbnail_ring5;
                int demo_img2=R.drawable.thumbnail_ring5;
                int demo_img3=R.drawable.thumbnail_ring5;
                int demo_img4=R.drawable.thumbnail_ring5;
                String desc="<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>";


                Intent intent=new Intent(Test.this,JewelDesc.class);

                intent.putExtra("title",Title).putExtra("ruppees", Ruppees).putExtra("img",Image).putExtra("feat1",Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1",demo_img1).putExtra("demo2",demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc",desc);


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


                Intent intent = new Intent(Test.this, JewelDesc.class);

                intent.putExtra("title", Title).putExtra("ruppees", Ruppees).putExtra("img", Image).putExtra("feat1", Feature1).putExtra("feat2", Feature2)
                        .putExtra("feat3", Feature3).putExtra("Sellername", SellerName).putExtra("demo1", demo_img1).putExtra("demo2", demo_img2)
                        .putExtra("demo3", demo_img3).putExtra("demo4", demo_img4).putExtra("desc", desc);

                startActivity(intent);

            }
        });


/*        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/


    }
}
