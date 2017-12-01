package com.fendoudebb.playandroid.module.camera;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fendoudebb.base.fragment.CheckPermissionsFragment;
import com.fendoudebb.camera.CameraPreviewActivity;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.util.ToastUtil;

import static android.app.Activity.RESULT_OK;

/**
 * zbj on 2017-11-24 11:22.
 */

public class ChoosePicFragment extends CheckPermissionsFragment implements View.OnClickListener {

    private static final String TAG = "ChoosePicFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mChoosePic;

    public static ChoosePicFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        ChoosePicFragment fragment = new ChoosePicFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_choose_pic;
    }

    @Override
    protected void initView(View view) {
        boolean b = checkCameraHardware(getActivity().getApplicationContext());
        if (!b) {
            getActivity().finish();
            ToastUtil.showToast("没有相机设备!");
            return;
        }
        requestMultiPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.ACCESS_FINE_LOCATION);

        mChoosePic = view.findViewById(R.id.choose_pic);
        Button choosePicFromCameraBtn = view.findViewById(R.id.choose_pic_from_camera);
        Button choosePicFromAlbumBtn = view.findViewById(R.id.choose_pic_from_album);
        choosePicFromCameraBtn.setOnClickListener(this);
        choosePicFromAlbumBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRequestGranted(String... permission) {

    }

    @Override
    protected void onRequestDenied(String... permission) {
        missingSdCardPermissionRationaleDialog();
    }

    @Override
    protected void onRequestNeverAsk(String... permission) {
        missingSdCardPermissionRationaleDialog();
    }

    private void missingSdCardPermissionRationaleDialog() {
        showNeedPermissionRationale(
                getString(R.string.missing_permission)
                , getString(R.string.external_storage_permission_choose_pic_rationale)
                , getString(R.string.ok)
                , getString(R.string.no)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                }
                , false);
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mChoosePic.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_pic_from_camera:
                dispatchTakePictureIntent();
                break;
            case R.id.choose_pic_from_album:
                Intent intent = new Intent(getActivity(), CameraPreviewActivity.class);
                getActivity().startActivity(intent);

                break;
            default:
                break;
        }
    }
}
