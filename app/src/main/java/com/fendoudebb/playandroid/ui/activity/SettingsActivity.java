package com.fendoudebb.playandroid.ui.activity;

import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.Constant;
import com.fendoudebb.playandroid.util.RevealEffectUtil;
import com.fendoudebb.playandroid.util.SpUtil;

/**
 * author : zbj on 2017/8/19 12:45.
 */

public class SettingsActivity extends BaseActivity {
    private static final String TAG = "zbj0819";

    @Override
    protected int initContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        new RevealEffectUtil().createEnterRevealEffect(this);

    }

    @Override
    protected void initData() {

    }

    public void onClick1(View view) {
        boolean isNightTheme = SpUtil.with(this).getBoolean(Constant.NIGHT_THEME, false);
        if (isNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        SpUtil.with(this).putBoolean(Constant.NIGHT_THEME, !isNightTheme);

        new RevealEffectUtil().createExitRevealEffect(this);
    }





}
