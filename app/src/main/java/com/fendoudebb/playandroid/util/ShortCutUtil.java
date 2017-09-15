package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.content.Intent;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.main.SplashActivity;

/**
 * author : zbj on 2017/8/23 22:05.
 * Add launcher shortcut
 */

public class ShortCutUtil {

    private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    private static final String ACTION_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";


    public static void addShortcut(Context context) {
        boolean isAdd = SpUtil.with(context).getBoolean(C.config.short_cut, false);
        if (isAdd) {
            return;
        }

        Intent shortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        // 名字
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));

        // 图标
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_launcher));

        // 设置关联程序
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(context, SplashActivity.class);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        context.sendBroadcast(shortcutIntent);
        SpUtil.with(context).putBoolean(C.config.short_cut,true);
    }

    private static void removeShortcut(Context context) {
        // remove shortcut的方法在小米系统上不管用，在三星上可以移除
        Intent intent = new Intent(ACTION_REMOVE_SHORTCUT);

        // 名字
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));

        // 设置关联程序
        Intent launcherIntent = new Intent(context, SplashActivity.class);

        launcherIntent.setAction(Intent.ACTION_MAIN);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        context.sendBroadcast(intent);
    }


}
