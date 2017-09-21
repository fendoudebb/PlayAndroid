package com.fendoudebb.playandroid.module.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.fendoudebb.playandroid.module.CheckPermissionsActivity;

/**
 * zbj on 2017-08-23 15:45.
 */

public class SplashActivity extends CheckPermissionsActivity {
    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

//        getWindow().setBackgroundDrawableResource(R.drawable.splash_bg);

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void permissionHasGranted() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AdvertiseActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        }, 500);
    }
}
