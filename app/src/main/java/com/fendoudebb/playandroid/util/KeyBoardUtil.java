package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fendoudebb.playandroid.App;

/**
 * zbj on 2017-09-27 17:32.
 */

public class KeyBoardUtil {

    /**
     * 避免输入法面板遮挡
     * <p>在manifest.xml中activity中设置</p>
     * <p>android:windowSoftInputMode="adjustPan"</p>
     */
    private KeyBoardUtil() {
        throw new IllegalArgumentException("KeyBoardUtil can not be initialized");
    }

    private static Context getContext() {
        return App.getContext();
    }

    private static InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 动态显示软键盘
     * {@link InputMethodManager#showSoftInput(View, int)}
     *
     * @param view 视图
     */
    public static void showSoftInput(@NonNull View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        getInputMethodManager().showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 动态隐藏软键盘
     *{@link InputMethodManager#hideSoftInputFromWindow(IBinder, int)}
     * @param view 视图
     */
    public static void hideSoftInput(@NonNull View view) {
        getInputMethodManager().hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
