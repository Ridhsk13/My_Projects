package com.ridhs.myparkingbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity{
    public static final String MyPREFERENCES = "MyPrefs";
    Button LoginButton, RegisterButton, GuestButton;

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        LoginButton =(Button)findViewById(R.id.activity_welcomescreen_LoginButton);
        RegisterButton=(Button)findViewById(R.id.activity_welcomescreen_RegisterButton);
        //GuestButton = (Button)findViewById(R.id.activity_welcomescreen_GuestButton);

        LoginButton.setOnClickListener(onClickListener);
        RegisterButton.setOnClickListener(onClickListener);
        //GuestButton.setOnClickListener(onClickListener);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String typeOfUser="";
            SharedPreferences.Editor editor= sharedpreferences.edit();
            switch(v.getId()){
                case R.id.activity_welcomescreen_LoginButton:
                    //Toast.makeText(WelcomeScreen.this,"Login Click",Toast.LENGTH_SHORT ).show();
                    Intent intentLogin = new Intent(WelcomeScreen.this,LoginActivity.class);
                    startActivity(intentLogin);
                    break;
                case R.id.activity_welcomescreen_RegisterButton:
                    Intent intentRegister = new Intent(WelcomeScreen.this,RegisterActivity.class);
                    startActivity(intentRegister);
                    break;
                default:
                    //nothing
            }
        }
    };

}
