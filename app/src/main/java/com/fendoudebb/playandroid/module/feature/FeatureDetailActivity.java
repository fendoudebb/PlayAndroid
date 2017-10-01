package com.fendoudebb.playandroid.module.feature;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.feature.ui.BrightnessFragment;
import com.fendoudebb.playandroid.module.feature.ui.CalendarFragment;
import com.fendoudebb.playandroid.module.feature.ui.ClockFragment;
import com.fendoudebb.playandroid.module.feature.ui.SystemIntentFragment;
import com.fendoudebb.playandroid.util.ActivityUtil;
import com.fendoudebb.playandroid.util.ResUtil;

/**
 * author : zbj on 2017/9/20 22:06.
 * 详情
 */

public class FeatureDetailActivity extends BaseActivity {
    private static final String TAG = "FeatureDetailActivity";

    @Override
    protected int initContentView() {
        return R.layout.activity_feature_detail;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        int nameId = getIntent().getIntExtra(C.intent.feature_name_id, 0);
        setToolbarTitle(ResUtil.getString(nameId));
        Log.d(TAG, "onViewInit: stringId: " + nameId);
        Fragment fragment = null;
        switch (nameId) {
            case R.string.mini_browser:

                break;
            case R.string.calendar:
                fragment = CalendarFragment.newInstance();
                break;
            case R.string.clock:
                fragment = ClockFragment.newInstance();
                break;
            case R.string.alarm_clock:

                break;
            case R.string.brightness:
                fragment = BrightnessFragment.newInstance();
                break;
            case R.string.volume:

                break;
            case R.string.flashlight:

                break;
            case R.string.screenshot:

                break;
            case R.string.qr_code:

                break;
            case R.string.music:

                break;
            case R.string.video:

                break;
            case R.string.number_attribution:

                break;
            case R.string.timer:

                break;
            case R.string.camera:

                break;
            case R.string.contact:

                break;
            case R.string.app_list:

                break;
            case R.string.system_setting:
                fragment = SystemIntentFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragment != null) {
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id
                    .feature_detail_container);
        }
    }
}
