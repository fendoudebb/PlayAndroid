package com.fendoudebb.playandroid.module.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.widget.PagerEnabledSlidingPaneLayout;

import java.lang.reflect.Field;

/**
 * author : zbj on 2017/10/10 19:38.
 * 侧滑退出Activity,使用SlidingPaneLayout
 */

public abstract class SwipeBackActivity extends AppCompatActivity implements SlidingPaneLayout
        .PanelSlideListener {
    private static final String TAG = "SlidingPaneLayout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    private void initSwipeBackFinish(){
        if (isSupportSwipeBack()) {
            PagerEnabledSlidingPaneLayout slidingPaneLayout = new PagerEnabledSlidingPaneLayout(this);
            //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
            //是32dp，现在给它改成0
            try {
                //属性
                Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
                f_overHang.setAccessible(true);
                f_overHang.set(slidingPaneLayout, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            slidingPaneLayout.setPanelSlideListener(this);
            slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);

            View leftView = new View(this);
            leftView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            slidingPaneLayout.addView(leftView, 0);

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
            decorChild.setBackgroundColor(Color.WHITE);
            decor.removeView(decorChild);
            decor.addView(slidingPaneLayout);
            slidingPaneLayout.addView(decorChild, 1);
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        Log.d(TAG, "onPanelSlide() called with: panel = [" + panel + "], slideOffset = [" +
                slideOffset + "]");
    }

    @Override
    public void onPanelOpened(View panel) {
        Log.d(TAG, "onPanelOpened() called with: view = [" + panel + "]");
        finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    @Override
    public void onPanelClosed(View panel) {
        Log.d(TAG, "onPanelClosed() called with: panel = [" + panel + "]");
    }

    /**
     * 是否支持滑动返回
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }
}
