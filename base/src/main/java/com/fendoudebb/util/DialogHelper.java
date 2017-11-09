package com.fendoudebb.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * zbj on 2017-09-20 10:28.
 */

public final class DialogHelper {

    /**
     * 显示AlertDialog
     *
     * @param activity                      dialog依附的activity
     * @param title                         标题
     * @param message                       信息
     * @param positiveText                  确认按钮文字
     * @param negativeText                  取消按钮文字
     * @param onPositiveButtonClickListener 确认按钮监听
     * @param onNegativeButtonClickListener 取消按钮监听
     * @param cancelable 是否能取消
     */
    public AlertDialog showAlertDialog(@NonNull Activity activity
            , @Nullable String title
            , @Nullable String message
            , @NonNull String positiveText
            , @NonNull String negativeText
            , @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener
            , @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener
            , boolean cancelable) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, onPositiveButtonClickListener)
                .setNegativeButton(negativeText, onNegativeButtonClickListener)
                .setCancelable(cancelable).show();
    }

}
