package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import com.fendoudebb.playandroid.App;

/**
 * zbj on 2017-09-21 16:41.
 * 调节屏幕亮度工具类
 * 注意：需要添加setting权限
 * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 */

public class BrightnessUtil {

    private BrightnessUtil() {
        throw new IllegalArgumentException("BrightnessUtil can not be initialized");
    }

    private static Context getContext() {
        return App.getContext();
    }

    // 判断是否开启了自动亮度调节
    public static boolean isAutoBrightness() {
        boolean isAutoBrightness = false;
        try {
            isAutoBrightness = Settings.System.getInt(getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System
                    .SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return isAutoBrightness;
    }

    /**
     * 获取当前屏幕的亮度,屏幕亮度值0-255
     * {@link Settings.System#SCREEN_BRIGHTNESS}
     *
     * @return int 0-255
     */
    public static int getScreenBrightness() {
        int nowBrightnessValue = 0;
        try {
            nowBrightnessValue = Settings.System.getInt(getContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

   /* // 设置亮度,存在static方法中activity会泄露
    // 程序退出之后亮度失效
    public static void setCurWindowBrightness(int brightness) {
        // 如果开启自动亮度，则关闭。否则，设置了亮度值也是无效的
        if (isAutoBrightness()) {
            closeAutoBrightness();
        }
        // context转换为Activity
        Activity activity = (Activity) context;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        // 异常处理
        if (brightness < 1) {
            brightness = 1;
        }
        // 异常处理
        if (brightness > 255) {
            brightness = 255;
        }
        lp.screenBrightness = (float) brightness * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }*/

    // 设置系统亮度
    // 程序退出之后亮度依旧有效
    public static void setSystemBrightness(int brightness) {
        // 异常处理
        if (brightness < 1) {
            brightness = 1;
        }
        // 异常处理
        if (brightness > 255) {
            brightness = 255;
        }
        saveBrightness(brightness);
    }

    // 停止自动亮度调节

    /**
     * 需要<uses-permission android:name="android.permission.WRITE_SETTINGS" />
     */
    public static void closeAutoBrightness() {
        Settings.System.putInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    // 开启亮度自动调节
    public static void openAutoBrightness() {
        Settings.System.putInt(getContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    // 保存亮度设置状态
    public static void saveBrightness(int brightness) {
        Uri uri = Settings.System.getUriFor("screen_brightness");
        Settings.System.putInt(getContext().getContentResolver(), "screen_brightness", brightness);
        getContext().getContentResolver().notifyChange(uri, null);
    }

}
