package com.noesis.bhurmalaidanmal;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by noesisimac on 10/3/16.
 */
public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.about_us_appbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aboutUs);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");

        TextView aboutUS=(TextView)findViewById(R.id.text_aboutUs);
        aboutUS.setTypeface(normal_bold);

        TextView para_aboutus=(TextView)findViewById(R.id.content_aboutUs);
        para_aboutus.setTypeface(normal_italic);
        para_aboutus.setText(Html.fromHtml("<html><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>" +
                "<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>" +
                "<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p></html>"));
    }
}
