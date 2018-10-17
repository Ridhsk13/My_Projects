package app.com.mychat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatMedia extends AppCompatActivity {
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    MyRecyclerViewAdaper myRecyclerViewAdaper;
    ArrayList<String> imageList = new ArrayList<>();
    DatabaseHandler db;
    ProgressBar progressBar;
    TextView textNoMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_media);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        db = new DatabaseHandler(this);

        recyclerView = (RecyclerView) findViewById(R.id.chat_media_recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.media_progressbar);
        textNoMedia = (TextView) findViewById(R.id.media_tv_no_media);

        imageList = getImageList();

        if (imageList.isEmpty()) {
            textNoMedia.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            textNoMedia.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            myRecyclerViewAdaper = new MyRecyclerViewAdaper(this, imageList);
            recyclerView.setAdapter(myRecyclerViewAdaper);
        }


    }

    public ArrayList<String> getImageList() {
        ArrayList<String> imgaeUrl = new ArrayList<>();

        List<ChatModel> contacts = db.getAllContacts();
        progressBar.setVisibility(View.VISIBLE);
        for (ChatModel cn : contacts) {
            if (cn.get_type().equals("image")) {
                imageList.add(cn.get_file());
                if (!cn.get_file().isEmpty())
                    Log.d("Imageurl ",cn.get_file());
            }
        }
        progressBar.setVisibility(View.INVISIBLE);
        return imgaeUrl;
    }

}
