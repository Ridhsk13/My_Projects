package com.ridhs.mydigicatlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        ImageView splashImage=(ImageView)findViewById(R.id.image_splashScreen);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_in);
        anim.reset();
        splashImage.clearAnimation();
        // l.startAnimation(anim);
        splashImage.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5500) {
                        sleep(100);
                        waited += 100;
                    }

                    Intent intent = new Intent(SplashScreen.this,
                            MainActivity.class).putExtra("Animate","Animation");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in_in, R.anim.fade_out_out);

                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }
            }

        };
        splashTread.start();
    }
}
