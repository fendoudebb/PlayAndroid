package com.fendoudebb.playandroid;

import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

import com.fendoudebb.playandroid.config.Constant;
import com.fendoudebb.playandroid.ui.activity.BaseActivity;
import com.fendoudebb.playandroid.util.SpUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        TextView view = findView(R.id.text_view);
        view.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        boolean isNightTheme = SpUtil.with(this).getBoolean(Constant.NIGHT_THEME, false);
        if (isNightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        SpUtil.with(this).putBoolean(Constant.NIGHT_THEME, !isNightTheme);
        recreate();
    }
}
