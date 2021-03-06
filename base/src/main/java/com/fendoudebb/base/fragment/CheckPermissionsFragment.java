package com.fendoudebb.base.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fendoudebb.util.DialogHelper;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRxPermissions = new RxPermissions(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
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

    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    /**
     * 打开App设置界面
     */
    protected void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//        intent.setData(Uri.fromParts("package", getPackageName(),null));
        startActivity(intent);
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

    protected void requestPermissions(String permissions) {
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

    protected void requestMultiPermission(final String... permissions) {
        mRxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            onRequestGranted();
                        } else {
                            onRequestNeverAsk(permissions);
                        }
                    }
                });
    }

    protected abstract void onRequestGranted(String... permission);

    protected abstract void onRequestDenied(String... permission);

    protected abstract void onRequestNeverAsk(String... permission);
}
