package app.com.mychat;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RIDHS on 6/8/2017.
 */

public class MyRecyclerViewAdaper extends RecyclerView.Adapter<MyRecyclerViewAdaper.ViewHolder> {

    public ArrayList<String> imageUrl;
    public Context context;
    public int screenWidth;

    public MyRecyclerViewAdaper(Context context, ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_image_adapter_layout_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = imageUrl.get(position);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageUrl.get(position), opts);
        opts.inJustDecodeBounds = false;

        Log.d("Image Url ",url);
        Picasso.with(context)
                .load(url)
                .error(R.drawable.ic_empty)
                .resize(screenWidth/3,300)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_adapter_imageView);
        }
    }
}
