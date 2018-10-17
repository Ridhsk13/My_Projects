package com.ridhs.jwellery;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Register extends AppCompatActivity {

    final String SiteKey = "6LfE1jQUAAAAAKRc7XpzbtNyw6usSFw22T6SHOKt";
    final String SecretKey  = "6LfE1jQUAAAAAOP_VvqwJRsb4J_h4HEEcGtAMlCJ";
    //private GoogleApiClient mGoogleApiClient;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            Drawable background = this.getResources().getDrawable(R.drawable.actionbar_gredient);
            w.setStatusBarColor(this.getResources().getColor(R.color.transparent));
            w.setBackgroundDrawable(background);
        }
        setContentView(R.layout.activity_register);
    }
}
