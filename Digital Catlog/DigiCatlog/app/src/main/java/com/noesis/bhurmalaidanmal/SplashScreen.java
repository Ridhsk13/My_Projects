package com.noesis.bhurmalaidanmal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by noesisimac on 10/17/16.
 */
public class SplashScreen extends Activity {

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);


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
