package com.noesis.bhurmalaidanmal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by noesisimac on 9/20/16.
 */
public class TEST2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testhorizontal);

        ImageView popup=(ImageView)findViewById(R.id.lightBox_image_click);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(TEST2.this,PopWindow.class);
                startActivity(i);
            }
        });

    }
}
