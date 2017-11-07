package com.fendoudebb.playandroid.module.base.activity;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.main.MainActivity;
import com.fendoudebb.playandroid.util.KeyBoardUtil;
import com.fendoudebb.playandroid.util.UnitConverter;

/**
 * author : zbj on 2017/8/18 23:32.
 * the basic activity for packaging toolbar etc.
 */

public abstract class BaseActivity extends SwipeBackActivity implements SlidingPaneLayout
        .PanelSlideListener {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //最近列表显示的缩略图的label,icon,top bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(
                    getTitle().toString(),
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
            setTaskDescription(taskDesc);
        }
        setContentView(initContentView());
        initStatusBar();
        initToolbar();
        init();
        initView();
        initData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (this instanceof MainActivity) {
                    break;
                }
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
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

    protected void init() {
    }

    protected abstract void initView();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    private int getStatusBarHeight() {
        int result = (int) UnitConverter.dp2px(25);
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resId);
        }
        Log.d("zbj0927", "getStatusBarHeight: result: " + result);
        return result;
    }

    /* 点击非EditText处隐藏键盘 */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev) && v != null) {
                KeyBoardUtil.hideSoftInput(v);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
