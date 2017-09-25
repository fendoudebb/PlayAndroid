package com.fendoudebb.playandroid.module.base.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.fendoudebb.playandroid.util.DialogHelper;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * zbj on 2017-09-22 15:57.
 */

public abstract class CheckPermissionsFragment extends BaseFragment {
    private static final String TAG = "CheckPermissions-zbj";

    private static final int REQUEST_CODE_WRITE_SETTINGS = 100;

    protected RxPermissions mRxPermissions;

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRxPermissions = new RxPermissions(getActivity());
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        mRxPermissions = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult() called with: requestCode = [" + requestCode +
                "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], " +
                "resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(getContext())) {
                    onRequestGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    onRequestDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        }
    }

    protected boolean hasWriteSettingsPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.System.canWrite
                (getContext());
    }

    protected void startWriteSettingsActivityForResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
        }
    }

    protected AlertDialog showNeedPermissionRationale(
            @NonNull String title
            , @NonNull String message
            , @NonNull String positiveText
            , @NonNull String negativeText
            , DialogInterface.OnClickListener onPositiveButtonClickListener
            , DialogInterface.OnClickListener onNegativeButtonClickListener
            , boolean cancelable) {

        return new DialogHelper().showAlertDialog(getActivity()
                , title
                , message
                , positiveText
                , negativeText
                , onPositiveButtonClickListener
                , onNegativeButtonClickListener
                , cancelable);
    }

    /**
     * <p>
     * 申请写入设置权限
     * <p>
     * 有权限回调{@link #onRequestGranted(String)}
     * <p>
     * 没有权限回调{@link #onRequestNeverAsk(String)}
     * <p>
     * 去设置界面后开启了权限回调{@link #onRequestGranted(String)}
     * <p>
     * 去设置界面后还是没有开启权限回调{@link #onRequestDenied(String)}
     * <p>
     */
    protected void requestWriteSettingsPermission() {
        if (hasWriteSettingsPermission()) {
            onRequestGranted(Manifest.permission.WRITE_SETTINGS);
        } else {
            onRequestNeverAsk(Manifest.permission.WRITE_SETTINGS);
        }

    }

    protected void requestPermissions(String... permissions) {
        mRxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        String name = permission.name;
                        if (permission.granted) {
                            onRequestGranted(name);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            onRequestDenied(name);
                        } else {
                            onRequestNeverAsk(name);
                        }
                    }
                });
    }

    protected abstract void onRequestGranted(String permission);

    protected abstract void onRequestDenied(String permission);

    protected abstract void onRequestNeverAsk(String permission);
}
