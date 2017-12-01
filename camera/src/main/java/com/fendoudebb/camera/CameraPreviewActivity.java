package com.fendoudebb.camera;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * zbj on 2017-11-30 14:40.
 */

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CameraPreviewActivity";
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private CameraView mCameraView;
    private FrameLayout mCamera_preview_container;

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
        mCamera_preview_container.removeAllViews();
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        prepareCamera();
    }

    private void prepareCamera() {
        mCamera = getCameraInstance();

        CameraHelper.configCameraParameters(mCamera);

        mCameraView = new CameraView(this,mCamera);
        mCamera_preview_container.addView(mCameraView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        //注释的两项为隐藏底部导航栏,有实体导航栏的无效
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mCamera_preview_container = findViewById(R.id.camera_preview_container);

        View btn = findViewById(R.id.take_picture);
        btn.setOnClickListener(this);

    }

    public Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    @Override
    public void onClick(View view) {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                File pictureFile = CameraHelper.getOutputMediaFile(CameraHelper.MEDIA_TYPE_IMAGE);
                if (pictureFile == null){
                    Log.d(TAG, "Error creating media file, check storage permissions: ");
                    return;
                }

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    MediaScannerConnection.scanFile(getApplicationContext(),
                            new String[] {pictureFile.getAbsolutePath()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(final String path, final Uri uri) {
                                    Log.d(TAG, "onScanCompleted() called with: path = [" + path +
                                            "], uri = [" + uri + "]");
                                }
                            });

                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Camera camera = Camera.open();
                camera.setDisplayOrientation(90);
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                List<Camera.Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
                Camera.Size optimalSize = getOptimalVideoSize(supportedVideoSizes,
                        supportedPreviewSizes, mPreview.getWidth(),
                        mPreview.getHeight());

                CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
                profile.videoFrameWidth = optimalSize.width;
                profile.videoFrameHeight = optimalSize.height;
                parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
                camera.setParameters(parameters);
                try {
                    camera.setPreviewTexture(mPreview.getSurfaceTexture());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MediaRecorder mediaRecorder = new MediaRecorder();
                camera.unlock();
                mediaRecorder.setCamera(camera);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                mediaRecorder.setProfile(profile);

                File outputMediaFile = getOutputMediaFile(2);
                if (outputMediaFile == null) {
                    return;
                }
                mediaRecorder.setOutputFile(outputMediaFile.getPath());

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();*/

    }

}
