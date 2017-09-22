package com.fendoudebb.playandroid.module.base.activity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.main.MainActivity;
import com.fendoudebb.playandroid.util.DialogHelper;
import com.fendoudebb.playandroid.util.UnitConverter;

/**
 * author : zbj on 2017/8/18 23:32.
 * the basic activity for packaging toolbar etc.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        initStatusBar();
        initToolbar();
        init();
        initView();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (this instanceof MainActivity) {
                    break;
                }
                finish();
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void initToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    protected void setToolbarTitle(String title) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(title);
        }
    }

    @LayoutRes
    protected abstract int initContentView();

    protected void init(){}

    protected abstract void initView();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    private int getStatusBarHeight() {
        int result = (int)UnitConverter.dp2px(25);
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result =Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new DialogHelper().showAlertDialog(this,"权限需求", rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, requestCode);
                        }
                    },"确认", null, "取消");
            Log.d("zbj", "requestPermission-shouldShowRequestPermissionRationale: ");
        } else {
            ActivityCompat.requestPermissions(this,new String[]{permission}, requestCode);

            Log.d("zbj", "requestPermission---: ");
        }
    }

}
