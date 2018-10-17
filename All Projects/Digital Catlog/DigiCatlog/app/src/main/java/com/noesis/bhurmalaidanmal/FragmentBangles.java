package com.noesis.bhurmalaidanmal;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by noesisimac on 9/23/16.
 */
public class FragmentBangles extends AppCompatActivity {

    private FragGridViewAdapter gridAdapter;

    String[] title={"Classical Switches","Restro Switches","Classic Switches","Classic Switches","Restro Switches",
            "Classic Switches","Classical Switches","Restro Switches","Classic Switches","Classic Switches","Restro Switches","Classic Switches"};

    int[] image={
            R.drawable.ring_1,
            R.drawable.ring_2,
            R.drawable.ring_3,
            R.drawable.ring_4,
            R.drawable.ring_5,
            R.drawable.ring_6,
            R.drawable.ring_1,
            R.drawable.ring_2,
            R.drawable.ring_3,
            R.drawable.ring_4,
            R.drawable.ring_5,
            R.drawable.ring_6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_bangles_appbar);
        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");

        TextView text_Rings=(TextView)findViewById(R.id.two);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent n=getIntent();
        String get_Product_title=n.getStringExtra("product_title");
        text_Rings.setText(get_Product_title);
        text_Rings.setTypeface(bold_italic);


        gridAdapter=new FragGridViewAdapter(this,title,image);
        GridView gridView=(GridView)findViewById(R.id.frag_bangles_gridView);

        gridView.setFocusable(false);
        gridView.setAdapter(gridAdapter);

    }

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_bangles_layout,container,false);

        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");

        TextView text_Rings=(TextView)rootView.findViewById(R.id.two);
        text_Rings.setTypeface(bold_italic);

        gridAdapter=new FragGridViewAdapter(this,title,image);
        GridView gridView=(GridView)rootView.findViewById(R.id.frag_bangles_gridView);

        gridView.setFocusable(false);
        gridView.setAdapter(gridAdapter);



        return rootView;*/
   // }
}
