package app.com.mychat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by RIDHS on 6/2/2017.
 */

public class CustomImageAdapter extends RecyclerView.Adapter<CustomImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<File> mRecentFiles;

    public CustomImageAdapter(Context mContext, ArrayList<File> mRecentFiles) {
        this.mContext = mContext;
        this.mRecentFiles = mRecentFiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_image_adapter_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String path = mRecentFiles.get(position).getPath();
        //String mimeType = getMimeType(path);
        //Log.d("MIME TYPE", mimeType);
        Log.d("PATH",path);
        //MimeTypeMap.
        if (path.contains("images") ||
                path.contains("image") ||
                path.contains("png") ||
                path.contains("IMG") ||
                path.contains("jpg") ||
                path.contains("jpeg")) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            holder.mImageView.setImageBitmap(bm);
            holder.mVideoIcon.setVisibility(View.GONE);
        }else {
            Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
            if (bmThumbnail != null){
                ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                bmThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
                holder.mImageView.setImageBitmap(bmThumbnail);
                holder.mVideoIcon.setVisibility(View.VISIBLE);
            }
        }

    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
    @Override
    public int getItemCount() {
        return mRecentFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public ImageView mVideoIcon;

        public ViewHolder(View v){
            super(v);
            // Get the widget reference from the custom layout
            mImageView = (ImageView) v.findViewById(R.id.image_adapter_imageView);
            mVideoIcon = (ImageView) v.findViewById(R.id.image_adapter_video_symbol);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private CustomImageAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CustomImageAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
