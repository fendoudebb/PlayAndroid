package com.fendoudebb.playandroid.module.brightness;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.fendoudebb.base.fragment.CheckPermissionsFragment;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.util.BrightnessUtil;
import com.fendoudebb.util.DrawableUtil;
import com.fendoudebb.util.ToastUtil;


/**
 * zbj on 2017-09-21 17:00.
 */

public class BrightnessFragment extends CheckPermissionsFragment implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "BrightnessFragment";

    private static final int MSG_UPDATE_ICON   = 0;
    private static final int MSG_UPDATE_SLIDER = 1;

    private SeekBar   mSeekBar;
    private ImageView mBtnAutoBrightness;

    private BrightnessObserver mBrightnessObserver;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_ICON:
                    changeAutoBrightnessBtnStatus();
                    break;
                case MSG_UPDATE_SLIDER:
                    int screenBrightness = BrightnessUtil.getScreenBrightness();
                    if (mSeekBar.getProgress() != screenBrightness) {
                        Log.d(TAG, "mBrightnessObserver - screenBrightness: " + screenBrightness);
                        mSeekBar.setProgress(screenBrightness);
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

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
        if (!hasWriteSettingsPermission()) {
            missingWriteSettingsPermissionRationaleDialog();
        }

        mBtnAutoBrightness = view.findViewById(R.id.btn_auto_brightness);
        view.findViewById(R.id.btn_write_settings_permission).setOnClickListener(this);

        changeAutoBrightnessBtnStatus();

        mBtnAutoBrightness.setOnClickListener(this);

        mSeekBar = view.findViewById(R.id.brightness_seek_bar);
        mSeekBar.setMax(255);

    }

    @Override
    protected void initData() {
        int screenBrightness = BrightnessUtil.getScreenBrightness();
        Log.d(TAG, "onViewInit - screenBrightness: " + screenBrightness);
        mSeekBar.setProgress(screenBrightness);

        mSeekBar.setOnSeekBarChangeListener(this);

        mBrightnessObserver = new BrightnessObserver(mHandler);
        mBrightnessObserver.startObserving();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBrightnessObserver != null) {
            mBrightnessObserver.stopObserving();
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(0);
            mHandler = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auto_brightness:
                if (BrightnessUtil.isAutoBrightness()) {
                    BrightnessUtil.closeAutoBrightness();
                } else {
                    BrightnessUtil.openAutoBrightness();
                }
                changeAutoBrightnessBtnStatus();
                break;
            case R.id.btn_write_settings_permission:
                startWriteSettingsActivityForResult();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRequestGranted(String... permission) {
        ToastUtil.showToast("已经获得权限了，可以调整亮度了");
    }

    @Override
    protected void onRequestDenied(String... permission) {
        missingWriteSettingsPermissionRationaleDialog();
    }

    @Override
    protected void onRequestNeverAsk(String... permission) {
        missingWriteSettingsPermissionRationaleDialog();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int screenBrightness = BrightnessUtil.getScreenBrightness();
        Log.d(TAG, "onProgressChanged:screenBrightness: " + screenBrightness);
        if (screenBrightness != progress) {
            BrightnessUtil.setSystemBrightness(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "SeekBar-onStartTrackingTouch() called with: seekBar = [" + seekBar + "]");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "SeekBar-onStopTrackingTouch() called with: seekBar = [" + seekBar + "]");
    }

    private void missingWriteSettingsPermissionRationaleDialog() {
        showNeedPermissionRationale(
                getString(R.string.missing_permission)
                , getString(R.string.write_settings_permission_adjust_brightness_rationale)
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

    private void changeAutoBrightnessBtnStatus() {
        if (BrightnessUtil.isAutoBrightness()) {
            DrawableUtil.tintDrawable(mBtnAutoBrightness.getDrawable(),
                    ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else {
            DrawableUtil.tintDrawable(mBtnAutoBrightness.getDrawable(), Color.BLACK);
        }
    }

    private class BrightnessObserver extends ContentObserver {

        private final Uri BRIGHTNESS_MODE_URI =
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
        private final Uri BRIGHTNESS_URI      =
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);

        BrightnessObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(TAG, "onChange() called with: selfChange = [" + selfChange + "], uri = [" + uri
                    + "]");
            if (selfChange) return;

            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                mHandler.sendEmptyMessage(MSG_UPDATE_ICON);
            } else if (BRIGHTNESS_URI.equals(uri)) {
                mHandler.sendEmptyMessage(MSG_UPDATE_SLIDER);
            }
        }

        void startObserving() {
            final ContentResolver cr = getContext().getContentResolver();
            cr.unregisterContentObserver(this);
            cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, this);
            cr.registerContentObserver(BRIGHTNESS_URI, false, this);
        }

        void stopObserving() {
            final ContentResolver cr = getContext().getContentResolver();
            cr.unregisterContentObserver(this);
        }

    }

}
