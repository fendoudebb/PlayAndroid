package com.fendoudebb.playandroid.module;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * zbj on 2017-09-21 17:55.
 */

public abstract class CheckPermissionsActivity extends AppCompatActivity {

    private static final String TAG = "CheckPermissions";

    protected void checkPermission(String permission) {

        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            Log.i("info", "1,需要申请权限。");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //TODO 用户未拒绝过 该权限 shouldShowRequestPermissionRationale返回false  用户拒绝过一次则一直返回true
                //TODO   注意小米手机  则一直返回时 false
                Log.i(TAG, "3,用户已经拒绝过一次该权限，需要提示用户为什么需要该权限。\n" +
                        "此时shouldShowRequestPermissionRationale返回：" + ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission));
                //TODO  解释为什么  需要该权限的  对话框
//                showMissingPermissionDialog();
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    permissionHasGranted();
                                    Log.d(TAG, "accept: 权限被授予l");
                                } else {
                                    Log.d(TAG, "accept: 权限没有被授予");
                                }
                            }
                        });
            } else {
               /* // 申请授权。
                ActivityCompat.requestPermissions(this, new String[]{permission}, resultCode);
                Log.i("info", "2,用户拒绝过该权限，或者用户从未操作过该权限，开始申请权限。-\n" +
                        "此时shouldShowRequestPermissionRationale返回：" +
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission));*/

                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    permissionHasGranted();
                                    Log.d(TAG, "accept: 权限被授予l");
                                } else {
                                    Log.d(TAG, "accept: 权限没有被授予");
                                }
                            }
                        });
            }
        } else {
            //TODO 权限 已经被准许  you can do something
            permissionHasGranted();
            Log.i(TAG, "7,已经被用户授权过了=可以做想做的事情了==打开联系人界面");
        }
    }

    protected abstract void permissionHasGranted();

    private void requestPermission(String... permissions) {

    }

    /**
     * 提示用户的 dialog
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少联系人权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "8--权限被拒绝,此时不会再回调onRequestPermissionsResult方法");
                    }
                });
        builder.setPositiveButton("去设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "4,需要用户手动设置，开启当前app设置界面");
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 打开App设置界面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
//        intent.setData(Uri.fromParts("package", getPackageName(),null));
        startActivity(intent);
    }

}
