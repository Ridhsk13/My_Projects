package app.com.mychat.menuActivity.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import app.com.mychat.ChatRoomActivity;
import app.com.mychat.R;

public class WallpaperActivity extends AppCompatActivity {

    private Intent intent, myIntent;
    //private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        getSupportActionBar().setTitle("Color Solid");

        GridView gridView = (GridView) findViewById(R.id.rgridView_wallpaper);
        gridView.setAdapter(new AdapterWallpaper(this));

        //myIntent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String rec_id = bundle.getString("RECIPIENT_ID");
        String img = bundle.getString("img");
        intent = new Intent(WallpaperActivity.this, ChatRoomActivity.class);
        //Bundle b = bundle;
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("RECIPIENT_ID", rec_id);
        b.putString("img",img);
        b.putString("where","in");
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    intent.putExtra("wallpaper", "#2C3D4D");
                    startActivity(intent);
                } else if (position == 1){
                    intent.putExtra("wallpaper", "#7EFFD4");
                    startActivity(intent);
                } else if (position == 2){
                    intent.putExtra("wallpaper", "#FFCCDB");
                    startActivity(intent);
                } else if (position == 3){
                    intent.putExtra("wallpaper", "#BA160C");
                    startActivity(intent);
                } else if (position == 4){
                    intent.putExtra("wallpaper", "#FBF5A9");
                    startActivity(intent);
                }
            }
        });

    }
}
