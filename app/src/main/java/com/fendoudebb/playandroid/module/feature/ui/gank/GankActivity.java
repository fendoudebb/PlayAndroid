package com.fendoudebb.playandroid.module.feature.ui.gank;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.feature.adapter.GankViewPagerAdapter;
import com.fendoudebb.playandroid.util.ResUtil;

/**
 * author : zbj on 2017/10/7 21:32.
 */

public class GankActivity extends BaseActivity {
    private static final String TAG = "GankActivity";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int initContentView() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ResUtil.getColor(R.color.colorPrimaryDark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mTabLayout = findView(R.id.gank_tab_layout);
        mViewPager = findView(R.id.gank_vp);

        mViewPager.setAdapter(new GankViewPagerAdapter(getSupportFragmentManager()));

        mTabLayout.setupWithViewPager(mViewPager);

        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
    }

    @Override
    protected void initData() {
        int nameId = getIntent().getIntExtra(C.intent.feature_name_id, 0);
        setToolbarTitle(ResUtil.getString(nameId));
        Log.d(TAG, "onViewInit: stringId: " + nameId);
    }
}
