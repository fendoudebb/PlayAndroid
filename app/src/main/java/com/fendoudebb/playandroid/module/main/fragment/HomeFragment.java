package com.fendoudebb.playandroid.module.main.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fendoudebb.base.fragment.BaseFragment;
import com.fendoudebb.extractor.File7zExtractor;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.TestFullScreenBottomSheetDialogFragment;
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
        File7zExtractor.extractFromAssets(getActivity(), "address", "sdcard/add.txt");
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


                //BottomSheetDialog 在27.0.0的v4下默认半屏展开,需改用BottomSheetDialogFragment
                /*BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = getLayoutInflater().inflate(R.layout.test_bottom_sheet_dialog, null);
                mBottomSheetDialog.setContentView(R.layout.test_bottom_sheet_dialog);
                BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
                mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//展开
                mDialogBehavior.setSkipCollapsed(true);
                mBottomSheetDialog.show();*/

                new TestFullScreenBottomSheetDialogFragment().show(getFragmentManager(), "");

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
