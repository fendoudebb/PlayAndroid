package com.fendoudebb.playandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

import com.fendoudebb.playandroid.config.Constant;
import com.fendoudebb.playandroid.ui.activity.BaseActivity;
import com.fendoudebb.playandroid.ui.activity.SettingsActivity;
import com.fendoudebb.playandroid.util.RevealEffectUtil;
import com.fendoudebb.playandroid.util.SpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity_zbj";

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        new RevealEffectUtil().createEnterRevealEffect(this);

        TextView view = findView(R.id.text_view);
        view.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        /*Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);*/
        boolean isNightTheme = SpUtil.with(this).getBoolean(Constant.NIGHT_THEME, false);
        if (isNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        SpUtil.with(this).putBoolean(Constant.NIGHT_THEME, !isNightTheme);
        new RevealEffectUtil().createExitRevealEffect(this);

    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
