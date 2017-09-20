package com.fendoudebb.playandroid.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * zbj on 2017-09-20 10:28.
 */

public class DialogHelper {

    /**
     * 显示AlertDialog
     *
     * @param activity                      dialog依附的activity
     * @param title                         标题
     * @param message                       信息
     * @param onPositiveButtonClickListener 确认按钮监听
     * @param positiveText                  确认按钮文字
     * @param onNegativeButtonClickListener 取消按钮监听
     * @param negativeText                  取消按钮文字
     */
    public AlertDialog showAlertDialog(@NonNull Activity activity,
                                       @Nullable String title, @Nullable String message,
                                       @Nullable DialogInterface.OnClickListener
                                               onPositiveButtonClickListener,
                                       @NonNull String positiveText,
                                       @Nullable DialogInterface.OnClickListener
                                               onNegativeButtonClickListener,
                                       @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        return builder.show();
    }

}
