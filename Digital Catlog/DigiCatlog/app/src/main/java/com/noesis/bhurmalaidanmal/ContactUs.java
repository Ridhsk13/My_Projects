package com.noesis.bhurmalaidanmal;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by noesisimac on 10/3/16.
 */
public class ContactUs extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.contactus_appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contactUS);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toobar_color));


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook.otf");
        Typeface normal_bold = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Bold.otf");
        Typeface bold_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-BoldItalic.otf");
        Typeface normal_italic = Typeface.createFromAsset(getAssets(), "fonts/CenturySchoolbook-Italic.otf");

        TextView textContactUs=(TextView)findViewById(R.id.text_contactUs);
        TextView textAddress=(TextView)findViewById(R.id.text_address);
        TextView paraAddress=(TextView)findViewById(R.id.paragraph_addr);
        TextView textFeedback=(TextView)findViewById(R.id.text_feedbackForm);


        EditText edit_name=(EditText)findViewById(R.id.edit_name);
        EditText edit_address=(EditText)findViewById(R.id.edit_addr);
        EditText edit_email=(EditText)findViewById(R.id.edit_emailId);

        Button bnt_send=(Button)findViewById(R.id.feedback_send_butn);

        textContactUs.setTypeface(bold_italic);
        textAddress.setTypeface(normal_bold);
        paraAddress.setTypeface(normal_italic);
        textFeedback.setTypeface(normal_bold);

        edit_name.setTypeface(bold_italic);
        edit_address.setTypeface(bold_italic);
        edit_email.setTypeface(bold_italic);


        bnt_send.setTypeface(normal_bold);

        /*edit_address.setFocusable(false);
        edit_email.setFocusable(false);
        edit_name.setFocusable(false);*/
        paraAddress.setText(Html.fromHtml("<html>Facetleon,Room No.229,<br> 2nd Floor Panchratna Building,<br>Opp. Opera House,<br>Charni Road(E),<br>Mumbai-541321321</html>"));


    }
}
