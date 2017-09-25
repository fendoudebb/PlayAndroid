package com.fendoudebb.playandroid.module.feature.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.fragment.CheckPermissionsFragment;
import com.fendoudebb.playandroid.util.BrightnessUtil;
import com.fendoudebb.playandroid.util.ToastUtil;

import static com.fendoudebb.playandroid.util.BrightnessUtil.closeAutoBrightness;
import static com.fendoudebb.playandroid.util.BrightnessUtil.isAutoBrightness;

/**
 * zbj on 2017-09-21 17:00.
 */

public class BrightnessFragment extends CheckPermissionsFragment implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "BrightnessFragment";
    private SeekBar mSeekBar;


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

        missingWriteSettingsPermissionRationaleDialog();

        view.findViewById(R.id.btn_1).setOnClickListener(this);
        view.findViewById(R.id.btn_2).setOnClickListener(this);
        mSeekBar = (SeekBar) view.findViewById(R.id.brightness_seek_bar);
        mSeekBar.setMax(255);
    }

    private void missingWriteSettingsPermissionRationaleDialog() {
        showNeedPermissionRationale(
                getString(R.string.missing_permission)
                , getString(R.string.write_settings_permission_rationale)
                , getString(R.string.ok)
                , getString(R.string.no)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startWriteSettingsActivityForResult();
                    }
                }
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                }
                , false);
    }

    @Override
    protected void initData() {
        int screenBrightness = BrightnessUtil.getScreenBrightness();
        Log.d(TAG, "screenBrightness: " + screenBrightness);
        mSeekBar.setProgress(screenBrightness);

        mSeekBar.setOnSeekBarChangeListener(this);

    }

    // 设置亮度
    // 程序退出之后亮度失效

    /**
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


                break;
            case R.id.btn_2:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onRequestGranted(String permission) {
        ToastUtil.showToast("已经获得权限了，可以调整亮度了");
    }

    @Override
    protected void onRequestDenied(String permission) {
        missingWriteSettingsPermissionRationaleDialog();
    }

    @Override
    protected void onRequestNeverAsk(String permission) {
        missingWriteSettingsPermissionRationaleDialog();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged() called with: seekBar = [" + seekBar + "], progress = [" +
                progress + "], fromUser = [" + fromUser + "]");
        if (hasWriteSettingsPermission()) {
            setCurWindowBrightness(progress);
        } else {
            requestWriteSettingsPermission();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch() called with: seekBar = [" + seekBar + "]");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch() called with: seekBar = [" + seekBar + "]");

    }


}
