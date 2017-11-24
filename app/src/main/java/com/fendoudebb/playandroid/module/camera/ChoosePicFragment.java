package com.fendoudebb.playandroid.module.camera;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fendoudebb.base.fragment.CheckPermissionsFragment;
import com.fendoudebb.playandroid.R;

/**
 * zbj on 2017-11-24 11:22.
 */

public class ChoosePicFragment extends CheckPermissionsFragment {
    private static final String TAG = "ChoosePicFragment";
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
        requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE);
        ImageView choosePic = view.findViewById(R.id.choose_pic);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onRequestGranted(String permission) {

    }

    @Override
    protected void onRequestDenied(String permission) {
        missingSdCardPermissionRationaleDialog();
    }

    @Override
    protected void onRequestNeverAsk(String permission) {
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
}
