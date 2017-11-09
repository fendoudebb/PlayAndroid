package com.fendoudebb.playandroid.module.main.activity;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.main.fragment.AOSPFragment;
import com.fendoudebb.util.ActivityUtil;
import com.fendoudebb.util.ResUtil;

/**
 * zbj on 2017-09-29 14:31.
 */

public class NavDetailActivity extends BaseActivity {
    private static final String TAG = "NavDetailActivity";

    @Override
    protected int initContentView() {
        return R.layout.activity_nav_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        int navNameId = getIntent().getIntExtra(C.intent.nav_name_id,0);
        setToolbarTitle(ResUtil.getString(navNameId));
        Log.d(TAG, "initData: navName: " + ResUtil.getString(navNameId));
        Fragment fragment = null;
        switch (navNameId) {
            case R.string.nav_about_author:

                break;
            case R.string.nav_open_source_framework:
                fragment = AOSPFragment.newInstance();
                break;
        }
        if (fragment != null) {
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), fragment
                    , R.id.nav_detail_container);
        }
    }
}
