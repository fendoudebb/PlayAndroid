package com.fendoudebb.camera;

import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * zbj on 2017-12-01 15:37.
 */

public class CameraHelper {
    private static final String TAG = "CameraHelper";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static void configCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        List<Camera.Size> pictureSizes = camera.getParameters().getSupportedPictureSizes();
        List<Camera.Size> previewSizes = camera.getParameters().getSupportedPreviewSizes();
        Camera.Size psize;
        for (int i = 0; i < pictureSizes.size(); i++) {
            psize = pictureSizes.get(i);
            Log.i("pictureSize",psize.width+" x "+psize.height);
        }
        for (int i = 0; i < previewSizes.size(); i++) {
            psize = previewSizes.get(i);
            Log.i("previewSize",psize.width+" x "+psize.height);
        }

        List<String> supportedFocusModes = camera.getParameters().getSupportedFocusModes();
        Log.d(TAG, "supportedFocusModes: " + supportedFocusModes.toString());


        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        Camera.Size optimalSize = CameraHelper.getOptimalSize(supportedPictureSizes,
                supportedPreviewSizes, 1920, 1080);

        // Use the same size for recording profile.

        Log.d(TAG, "prepareCamera: CameraSize: " + optimalSize.width + " x " + optimalSize.height);


        // likewise for the camera object itself.
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        parameters.setPictureSize(optimalSize.width, optimalSize.height);
        parameters.setRotation(90);

        if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            Log.d(TAG, "setFocusMode");
        }

     /*   if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> areas = new ArrayList<>();
            Rect rect = new Rect(-100, -100, 100, 100);
            areas.add(new Camera.Area(rect, 600));
            Rect rect2 = new Rect(800, -1000, 1000, -800);
            areas.add(new Camera.Area(rect2, 400));
            parameters.setMeteringAreas(areas);
        }*/

        if (parameters.getMaxNumMeteringAreas() > 0){ // check that metering areas are supported
            Log.d(TAG, "getMaxNumMeteringAreas() called : " + parameters.getMaxNumMeteringAreas());
            List<Camera.Area> meteringAreas = parameters.getMeteringAreas();
//            Log.d(TAG, "getMeteringAreas: " + meteringAreas.toString());
           /* List<Camera.Area> meteringAreas = new ArrayList<>();

            Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
            meteringAreas.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
            Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
            meteringAreas.add(new Camera.Area(areaRect2, 400)); // set weight to 40%
            parameters.setMeteringAreas(meteringAreas);*/
        }

        if (parameters.getMaxNumDetectedFaces() > 0) {
            Log.d(TAG, "getMaxNumDetectedFaces: " + parameters.getMaxNumDetectedFaces());
            camera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                @Override
                public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                    Log.d(TAG, "onFaceDetection() called with: faces = [" + faces + "], camera = " +
                            "[" + camera + "]");
                    if (faces.length > 0) {
                        Log.d(TAG, "face detected: "+ faces.length +
                                " Face 1 Location X: " + faces[0].rect.centerX() +
                                "Y: " + faces[0].rect.centerY() );
                    }
                }
            });
        }

        camera.setParameters(parameters);

    }

    /**
     * Iterate over supported camera video/pictures sizes to see which one best fits the
     * dimensions of the given view while maintaining the aspect ratio. If none can,
     * be lenient with the aspect ratio.
     *
     * @param supportedSizes Supported camera video/pictures sizes.
     * @param previewSizes Supported camera preview sizes.
     * @param w     The width of the view.
     * @param h     The height of the view.
     * @return Best match camera video/pictures size to fit in the view.
     */
    public static Camera.Size getOptimalSize(List<Camera.Size> supportedSizes,
                                                  List<Camera.Size> previewSizes, int w, int h) {
        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;

        // Supported video sizes list might be null, it means that we are allowed to use the preview
        // sizes
        List<Camera.Size> sizes;
        if (supportedSizes != null) {
            sizes = supportedSizes;
        } else {
            sizes = previewSizes;
        }
        Camera.Size optimalSize = null;

        // Start with max value and refine as we iterate over available video sizes. This is the
        // minimum difference between view and camera height.
        double minDiff = Double.MAX_VALUE;

        // Target view height
        int targetHeight = h;

        // Try to find a video/pictures size that matches aspect ratio and the target view size.
        // Iterate over all available sizes and pick the largest size that can fit in the view and
        // still maintain the aspect ratio.
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff && previewSizes.contains(size)) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find video/pictures size that matches the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff && previewSizes.contains(size)) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public  static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "getOutputMediaFile: 没挂载sd卡");
            return  null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraSample");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            Log.d(TAG, "mediaStorageDir: 不存在1111111");
            if (! mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
            Log.d(TAG, "mediaStorageDir: 不存在2222222");
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
            Log.d(TAG, "MEDIA_TYPE_IMAGE: mediaFile: " + mediaFile.toString());
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
            Log.d(TAG, "MEDIA_TYPE_VIDEO: mediaFile: " + mediaFile.toString());
        } else {
            Log.d(TAG, "null: mediaFile: 类型为空");
            return null;
        }

        return mediaFile;
    }


}
