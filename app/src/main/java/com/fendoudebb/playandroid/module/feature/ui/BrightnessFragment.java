package com.fendoudebb.playandroid.module.feature.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.fragment.CheckPermissionsFragment;
import com.fendoudebb.playandroid.util.BrightnessUtil;
import com.fendoudebb.playandroid.util.ToastUtil;

import static com.fendoudebb.playandroid.util.BrightnessUtil.closeAutoBrightness;
import static com.fendoudebb.playandroid.util.BrightnessUtil.isAutoBrightness;

/**
 * zbj on 2017-09-21 17:00.
 */

public class BrightnessFragment extends CheckPermissionsFragment implements View.OnClickListener {

    private static final String TAG = "BrightnessFragment";


    public static BrightnessFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        BrightnessFragment fragment = new BrightnessFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_bright;
    }

    @Override
    protected void initView(View view) {
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
            closeAutoBrightness();
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
                boolean b = hasWriteSettingsPermission();
                if (b)
                    setCurWindowBrightness(50);
                else
                    show("没有权限", "给我权限", getString(R.string.ok), new DialogInterface
                            .OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startWriteSettingsActivityForResult();
                        }
                    });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRequestGranted(String permission) {
        setCurWindowBrightness(50);
    }

    @Override
    protected void onRequestDenied(String permission) {
        ToastUtil.showToast("还没有给我哦");
    }

    @Override
    protected void onRequestNeverAsk(String permission) {

    }
}
