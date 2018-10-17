package com.noesis.bhurmalaidanmal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by noesisimac on 9/21/16.
 */
public class ProductCategory extends AppCompatActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_category_appbar);

         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
         setSupportActionBar(toolbar);
         toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");

        TextView product_text=(TextView)findViewById(R.id.cat2);
        TextView category_text=(TextView)findViewById(R.id.cat3);

        Button viewMore_Bangles=(Button)findViewById(R.id.cat_btn1);
        Button viewMore_Rings=(Button)findViewById(R.id.cat_btn2);
        Button viewMore_Earrings=(Button)findViewById(R.id.cat_btn3);
        Button viewMore_Necklaces=(Button)findViewById(R.id.cat_btn4);

        product_text.setTypeface(normal_italic);
        product_text.setTextColor(getResources().getColor(R.color.maroon));

        category_text.setTypeface(bold_italic);
        category_text.setTextColor(getResources().getColor(R.color.maroon));

        viewMore_Bangles.setTypeface(bold_italic);
        viewMore_Rings.setTypeface(bold_italic);
        viewMore_Earrings.setTypeface(bold_italic);
        viewMore_Necklaces.setTypeface(bold_italic);

        viewMore_Bangles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                intentBangles();/*
                String setTitle = "  Bangles  ";
                Intent i = new Intent(ProductCategory.this, FragmentBangles.class).putExtra("product_title", setTitle);
                startActivity(i);*/

            }
        });

         viewMore_Rings.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {

                 intentRings();/*
                 String setTitle="  Rings  ";
                 Intent i=new Intent(ProductCategory.this,FragmentBangles.class).putExtra("product_title",setTitle);
                 startActivity(i);*/

             }
         });
         viewMore_Earrings.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {

                 intentEarRings();/*
                 String setTitle="  Ear Rings  ";
                 Intent i=new Intent(ProductCategory.this,FragmentBangles.class).putExtra("product_title",setTitle);
                 startActivity(i);*/

             }
         });

         viewMore_Necklaces.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {
              intentNecklace();

                 /*String setTitle="  Necklaces  ";
                 Intent i=new Intent(ProductCategory.this,FragmentBangles.class).putExtra("product_title",setTitle);
                 startActivity(i);
*/
             }
         });




     }

    public void intentBangles(){
        String setTitle = "  Bangles  ";
        Intent i = new Intent(ProductCategory.this, FragmentBangles.class).putExtra("product_title", setTitle);
        startActivity(i);

    }

    public void intentRings(){
        String setTitle = "  Rings  ";
        Intent i = new Intent(ProductCategory.this, FragmentBangles.class).putExtra("product_title", setTitle);
        startActivity(i);

    }

    public void intentEarRings(){
        String setTitle = "  Ear Rings  ";
        Intent i = new Intent(ProductCategory.this, FragmentBangles.class).putExtra("product_title", setTitle);
        startActivity(i);

    }

    public void intentNecklace(){
        String setTitle = "  Necklaces  ";
        Intent i = new Intent(ProductCategory.this, FragmentBangles.class).putExtra("product_title", setTitle);
        startActivity(i);

    }
}
