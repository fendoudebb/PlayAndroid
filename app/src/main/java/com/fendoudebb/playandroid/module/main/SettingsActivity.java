package com.fendoudebb.playandroid.module.main;

import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import com.fendoudebb.activity.BaseActivity;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.music.ui.MusicListActivity;

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
//        new RevealEffectUtil().createEnterRevealEffect(this);

    }

    @Override
    protected void initData() {

    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, MusicListActivity.class);

        TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(intent)
                .startActivities();

    }





}
