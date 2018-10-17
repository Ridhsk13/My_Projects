package app.com.mychat;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.yqritc.scalablevideoview.ScalableVideoView;

import org.junit.runners.Parameterized;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.graphics.BitmapFactory.decodeFile;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    public Camera mCamera;
    private CameraPreview mPreview;
    private String TAG = "CameraActivity";

    ImageView capture, video, cancel, recapture, correct, recent, flash, rotate;
    VideoView videoView;

    String videoFileName;

    File f;

    private boolean isRecording = false;
    public MediaRecorder mMediaRecorder;
    public String mediaPath;
    public Uri mediaURI;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_GALLERY_IMAGE = 2;
    public static final int MEDIA_TYPE_VIDEO = 3;

    private static final int MIN_CLICK_DURATION = 300;
    private long startClickTime;
    private boolean longClickActive;
    private boolean recording, pause = false;
    private Camera.PictureCallback mPicture;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int FLASH_MODE_ON = 1;
    private static final int FLASH_MODE_AUTO = 0;
    private static final int FLASH_MODE_OFF = -1;
    private int flashMode = FLASH_MODE_AUTO;
    int currentCameraID = -1;

    private FrameLayout preview;

    private VideoPlayerView playerView;
    private ScalableVideoView scalableVideoView;

    private TextView recordMsg;

    private LinearLayout linearLayoutRecTimer;
    private ImageView imageRecTimer;
    private TextView recTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        mCamera = getCameraInstance(this, currentCameraID);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        videoView = (VideoView) findViewById(R.id.video_view);

        capture = (ImageView) findViewById(R.id.camera_capture_button);
        cancel = (ImageView) findViewById(R.id.camera_cancel);
        correct = (ImageView) findViewById(R.id.camera_correct);
        recapture = (ImageView) findViewById(R.id.camera_recapture);
        video = (ImageView) findViewById(R.id.camera_capture_video);
        recent = (ImageView) findViewById(R.id.recent_image);
        flash = (ImageView) findViewById(R.id.camera_flash);
        rotate = (ImageView) findViewById(R.id.camera_rotate);
        recordMsg = (TextView) findViewById(R.id.record_msg);

        linearLayoutRecTimer = (LinearLayout) findViewById(R.id.rec_linear_layout);
        recTimer = (TextView) findViewById(R.id.rec_timer);
        imageRecTimer = (ImageView) findViewById(R.id.rec_img);


        new Thread(new Runnable() {
            @Override
            public void run() {
                getRecentImages();
            }
        }).start();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new CustomImageAdapter(this, recentfiles);

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new CustomImageAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new CustomImageAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String imagePath = recentfiles.get(position).getPath();
                mediaPath = imagePath;
                mediaURI = Uri.parse(mediaPath);
                //String mimeType = getMimeType(mediaPath);
                //Log.d("MIME TYPE", mimeType);
                if (imagePath.contains("images") ||
                        imagePath.contains("image") ||
                        imagePath.contains("png") ||
                        imagePath.contains("IMG") ||
                        imagePath.contains("jpg") ||
                        imagePath.contains("jpeg")) {
                    previewMedia(MEDIA_TYPE_GALLERY_IMAGE, mediaPath);
                } else {
                    previewMedia(MEDIA_TYPE_VIDEO, mediaPath);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        correct.setOnClickListener(this);
        recapture.setOnClickListener(this);
        cancel.setOnClickListener(this);
        flash.setOnClickListener(this);
        rotate.setOnClickListener(this);


        mPicture = getPictureCallback();

        capture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        recordMsg.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        Log.i("ACTION_DOWN", "ACTION_DOWN::" + pause + " " + recording);
                        if (longClickActive == false) {
                            longClickActive = true;
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (longClickActive == true) {
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            if (clickDuration >= MIN_CLICK_DURATION) {
                                video.setVisibility(View.VISIBLE);
                                startRecAnimation();
                                longClickActive = false;
                                if (pause && !recording) {
                                    pause = false;
                                    mediaPath = prepareMediaRecorder();
                                    if (mediaPath != null) {

                                        Log.e("Fail to prapare", "");
                                        finish();
                                    }
                                    // work on UiThread for better performance
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            try {
                                                mMediaRecorder.start();
                                            } catch (final Exception ex) {
                                                // Log.i("---","Exception in thread");
                                            }
                                        }
                                    });
                                    recording = true;
                                } else {
                                    mediaPath = prepareMediaRecorder();
                                    if (mediaPath != null) {

                                        Log.e("Fail in prepare MediaRecorder", "");

                                    }
                                    // work on UiThread for better performance
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            try {
                                                mMediaRecorder.start();

                                            } catch (final Exception ex) {
                                                // Log.i("---","Exception in thread");
                                            }
                                        }
                                    });
                                    recording = true;
                                }
                            } /*else {
                                mCamera.takePicture(null, null, mPicture);
                            }*/
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("ACTION_UP", "ACTION_UP::" + recording);
                        recordMsg.setVisibility(View.GONE);
                        stopRecAnimation();
                        longClickActive = false;
                        video.setVisibility(View.GONE);
                        if (recording) {
                            // stop recording and release camera
                            pause = true;
                            recording = false;
                            releaseMediaRecorder(); // release the MediaRecorder object
                            previewMedia(MEDIA_TYPE_VIDEO, mediaPath);
                        } else {
                            mCamera.takePicture(null, null, mPicture);
                        }
                        break;
                }

                return true;
            }
        });
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public void startRecAnimation() {
        linearLayoutRecTimer.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(CameraActivity.this, R.anim.blink);
        imageRecTimer.startAnimation(animation);

        startTime = SystemClock.uptimeMillis();
        timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    public void stopRecAnimation() {
        imageRecTimer.clearAnimation();
        linearLayoutRecTimer.setVisibility(View.GONE);
    }

    public ArrayList<File> recentfiles = new ArrayList<File>();

    public void getRecentImages() {
        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
        };

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        //String[] selectionArgs = {"MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE", "MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO"};

        String sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

        final Cursor cursor = getContentResolver()
                .query(MediaStore.Files.getContentUri("external"), projection, selection,
                        null, sortOrder);

        // Put it in the image view
        while (cursor.moveToNext()) {
            //final ImageView imageView = (ImageView) findViewById(R.id.pictureView);
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);
            if (imageFile.exists()) {   // TODO: is there a better way to do this?
                recentfiles.add(imageFile);
                //Log.d("File Url ", imageFile.getPath());
                //Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                //imageView.setImageBitmap(bm);
            }
        }
    }

    public void previewMedia(int type, String path) {

        capture.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        cancel.setVisibility(View.VISIBLE);
        recapture.setVisibility(View.VISIBLE);
        correct.setVisibility(View.VISIBLE);
        flash.setVisibility(View.GONE);
        rotate.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        recordMsg.setVisibility(View.GONE);

        switch (type) {
            case MEDIA_TYPE_GALLERY_IMAGE:
                mPreview.setVisibility(View.INVISIBLE);
                Bitmap bm = BitmapFactory.decodeFile(path);
                recent.setImageBitmap(bm);
                break;
            case MEDIA_TYPE_IMAGE:
                //nothing
                break;
            case MEDIA_TYPE_VIDEO:
                mPreview.setVisibility(View.INVISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(mediaURI);
                videoView.start();
                break;
            default:
                Log.println(Log.INFO, "PreviewMedia", "Not Working");
        }
    }

    public static Camera getCameraInstance(Activity context, int camId) {
        Camera c = null;
        try {
            c = Camera.open();
            //setCameraDisplayOrientation(context,camId,c);
        } catch (Exception e) {
            e.printStackTrace();
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                assert pictureFile != null;
                mediaURI = Uri.parse(pictureFile.getPath());
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    previewMedia(MEDIA_TYPE_IMAGE, mediaPath);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }
        };
        return picture;
    }


    public String prepareMediaRecorder() {

        mCamera = getCameraInstance(this, currentCameraID);
        mCamera.setDisplayOrientation(90);
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setOrientationHint(90);

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));

        // Step 4: Set output file
        mediaURI = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        mMediaRecorder.setOutputFile(mediaURI.getPath());

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return null;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return null;
        }
        return mediaPath;
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MyChatApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyChatApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        videoFileName = timeStamp;
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpeg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
            f = mediaFile;
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_cancel:
                File file = new File(mediaURI.getPath());
                if (file.exists()) {
                    file.delete();
                } else {
                    Log.d("Video File Not found at", mediaURI.getPath());
                }
                finish();
                break;
            case R.id.camera_recapture:
                recreate();
                break;
            case R.id.camera_correct:
                Intent intent = getIntent();
                Log.d("imagepath", mediaURI.getPath());
                intent.putExtra("ImagePath", mediaURI.getPath());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.camera_flash:
                if (flashMode == FLASH_MODE_ON) {
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(parameters);
                    flash.setImageDrawable(getDrawable(R.drawable.flash_off));
                    flashMode = FLASH_MODE_OFF;
                } else if (flashMode == FLASH_MODE_OFF) {
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    mCamera.setParameters(parameters);
                    flash.setImageDrawable(getDrawable(R.drawable.flash_auto));
                    flashMode = FLASH_MODE_AUTO;
                } else if (flashMode == FLASH_MODE_AUTO) {
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    mCamera.setParameters(parameters);
                    flash.setImageDrawable(getDrawable(R.drawable.flash_on));
                    flashMode = FLASH_MODE_ON;
                } else {
                    Log.e("Flash Mode", "Error");
                }
                break;
            case R.id.camera_rotate:

                /*int currentCameraId = -1;
                //mPreview.surfaceDestroyed(mPreview.getHolder());
                //swap the id of the camera to be used
                int noOfCameras = Camera.getNumberOfCameras();
                for (int i = 0; i < noOfCameras; i++) {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);
                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        currentCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
                        break;
                    } else {
                        currentCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
                        break;
                    }
                }
                mCamera = getCameraInstance(currentCameraID);
                mPreview = new CameraPreview(this, mCamera);
//                preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);
                // mPreview.surfaceChanged(mPreview.getHolder(),0,0,0);*/
                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    //release the old camera instance
                    //switch camera, from the front and the back and vice versa

                    releaseCamera();
                    chooseCamera();
                } else {
                    Toast toast = Toast.makeText(this, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                    toast.show();
                }
                //chooseCamera();

                break;
            default:
                //nothing
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check

        for (int i = 0; i < numberOfCameras; i++) {

            Camera.CameraInfo info = new Camera.CameraInfo();

            Camera.getCameraInfo(i, info);

            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }

        }
        return cameraId;
    }

    public void onResume() {
        super.onResume();
        if (!hasCamera(this)) {
            Toast toast = Toast.makeText(this, "Sorry, your phone does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() == -1) {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa
                releaseCamera();
                chooseCamera();
            } else {
                Toast toast = Toast.makeText(this, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    ;
    private boolean cameraFront = false;

    private boolean hasCamera(Context context) {
        //check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public void chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Timer timer;

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            final String hms = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(updatedTime)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(updatedTime)),
                    TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(updatedTime)));
            long lastsec = TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                    .toMinutes(updatedTime));
            System.out.println(lastsec + " hms " + hms);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (recTimer != null)
                            recTimer.setText(hms);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            });
        }
    }
}
