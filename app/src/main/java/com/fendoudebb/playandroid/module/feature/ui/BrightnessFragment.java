package com.fendoudebb.playandroid.module.feature.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.util.BrightnessUtil;

import static com.fendoudebb.playandroid.util.BrightnessUtil.isAutoBrightness;
import static com.fendoudebb.playandroid.util.BrightnessUtil.stopAutoBrightness;

/**
 * zbj on 2017-09-21 17:00.
 */

public class BrightnessFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "BrightnessFragment";

    public static BrightnessFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        BrightnessFragment fragment = new BrightnessFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bright, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_1).setOnClickListener(this);
        view.findViewById(R.id.btn_2).setOnClickListener(this);
    }

    // 设置亮度
    // 程序退出之后亮度失效

    /**
     *
     * @param brightness 调节的亮度 1-255之间
     */
    public void setCurWindowBrightness(int brightness) {
        // 如果开启自动亮度，则关闭。否则，设置了亮度值也是无效的
        if (isAutoBrightness()) {
            stopAutoBrightness();
        }

        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        // 异常处理
        if (brightness < 1) {
            brightness = 1;
        }
        // 异常处理
        if (brightness > 255) {
            brightness = 255;
        }
        lp.screenBrightness = (float) brightness * (1f / 255f);
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                boolean autoBrightness = isAutoBrightness();
                Log.d(TAG, "autoBrightness: " + autoBrightness);

                int screenBrightness = BrightnessUtil.getScreenBrightness();
                Log.d(TAG, "screenBrightness: " + screenBrightness);
                break;
            case R.id.btn_2:
                setCurWindowBrightness(50);
                break;
            default:
                break;
        }
    }
}
