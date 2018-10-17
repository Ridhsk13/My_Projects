package com.ridhs.myblackboard;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "Splash_Screen";
    public boolean logInStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    logInStatus = true;
                    //Intent intentLogin = new Intent(SplashScreen.this, Login.class);
                    //startActivity(intentLogin);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    logInStatus = false;
                    //Intent intentMain = new Intent(SplashScreen.this, MainActivity.class);
                    //startActivity(intentMain);
                }
            }
        };


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!logInStatus) {
                    Intent intentLogin = new Intent(SplashScreen.this, Login.class);
                    startActivity(intentLogin);
                } else {
                    Intent intentMain = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentMain);
                }
            }
        }, 2500);
    }

    /*private boolean checkFirebaseAuth() {
        final boolean[] logInStatus = {false};

        return logInStatus[0];
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
