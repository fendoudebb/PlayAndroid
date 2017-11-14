package com.fendoudebb.playandroid.module.main.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fendoudebb.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.GlideApp;
import com.fendoudebb.playandroid.module.feature.FeaturesFragment;
import com.fendoudebb.util.ActivityUtil;

/**
 * author : zbj on 2017/9/27 21:17.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mTest;

    public static HomeFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void initView(View view) {
        TextView textView = view.findViewById(R.id.text_view);
        textView.setOnClickListener(this);
        view.findViewById(R.id.text_view_2).setOnClickListener(this);
        mTest = view.findViewById(R.id.test_img);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view:
                FeaturesFragment featuresFragment = FeaturesFragment.newInstance();
                assert getFragmentManager() != null;
                ActivityUtil.addFragmentToActivity(getFragmentManager(), featuresFragment,
                        R.id.contentFrame);
                break;
            case R.id.text_view_2:
//                Glide.with(getActivity()).asBitmap().load(R.drawable.alarm_clock).into(mTest);
                Glide.with(mTest).asBitmap().load(R.drawable.android_splash).thumbnail(0.75f).into(mTest);
                GlideApp.with(mTest).asBitmap().load(R.drawable.android_crash).into(mTest);
                boolean supportStepCountSensor = isSupportStepCountSensor(getActivity());
                Log.d("zbj1114", "supportStepCountSensor: " + supportStepCountSensor);
                break;
            default:
                break;
        }

    }

    public static boolean isSupportStepCountSensor(Context context) {
        // 获取传感器管理器的实例
        SensorManager sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        return countSensor != null || detectorSensor != null;
    }

}
