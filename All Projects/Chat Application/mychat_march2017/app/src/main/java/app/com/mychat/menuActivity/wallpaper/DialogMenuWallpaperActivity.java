package app.com.mychat.menuActivity.wallpaper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.com.mychat.ChatRoomActivity;
import app.com.mychat.R;

public class DialogMenuWallpaperActivity extends Dialog implements View.OnClickListener {

    private TextView txtGalery;
    private TextView txtSolidColor;
    private TextView txtDefaultWallpaper;
    private TextView txtNoWallpaper;

    private String name,rec_id, where,img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private Dialog dialog;
    private Activity activity;

    public DialogMenuWallpaperActivity(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public int REQUEST_PICK_IMAGE = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_menu_wallpaper);

        txtGalery = (TextView) findViewById(R.id.galery_wallpaper);
        txtSolidColor = (TextView) findViewById(R.id.solid_color_wallpaper);
        txtDefaultWallpaper = (TextView) findViewById(R.id.default_wallpaper);
        txtNoWallpaper = (TextView) findViewById(R.id.no_wallpaper);

        txtGalery.setOnClickListener(this);
        txtSolidColor.setOnClickListener(this);
        txtDefaultWallpaper.setOnClickListener(this);
        txtNoWallpaper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("RECIPIENT_ID", rec_id);
        b.putString("img",img);
        b.putString("where","in");

        switch (v.getId()){
            case R.id.galery_wallpaper:
                Intent intent;
                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                } else {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                }
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
                //intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return-data", true);
                activity.startActivityForResult(intent, REQUEST_PICK_IMAGE);
                dismiss();
                break;
            case R.id.solid_color_wallpaper:
                Intent intentWallpaper = new Intent(activity, WallpaperActivity.class);
                intentWallpaper.putExtras(b);
                activity.startActivity(intentWallpaper);
                dismiss();
                break;
            case R.id.default_wallpaper:
                Intent intentDefaul = new Intent(activity, ChatRoomActivity.class);
                intentDefaul.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentDefaul.putExtra("wallpaper", "#FFF7F8");
                intentDefaul.putExtras(b);
                activity.startActivity(intentDefaul);
                dismiss();
                break;
            case R.id.no_wallpaper:
                Intent intentNoWalpaper = new Intent(activity, ChatRoomActivity.class);
                intentNoWalpaper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentNoWalpaper.putExtra("wallpaper", "#FFF7F8");
                intentNoWalpaper.putExtras(b);
                activity.startActivity(intentNoWalpaper);
                dismiss();
                break;
        }
        dismiss();
    }
}
