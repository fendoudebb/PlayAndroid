package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.fendoudebb.playandroid.App;

/**
 * zbj on 2017-09-22 17:47.
 */

public class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
        throw new IllegalArgumentException("ToastUtil can not be initialized");
    }

    private static Context getContext() {
        return App.getContext();
    }

    public static void showToast(String text) {
        hideToast();
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(@StringRes int stringRes) {
        hideToast();
        String string = getContext().getString(stringRes);
        toast = Toast.makeText(getContext(), string, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(String text) {
        hideToast();
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    private static void hideToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

}