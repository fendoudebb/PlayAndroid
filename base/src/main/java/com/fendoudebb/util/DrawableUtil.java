package com.fendoudebb.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * zbj on 2017-09-27 10:02.
 */

public final class DrawableUtil {

    private DrawableUtil() {
        throw new IllegalArgumentException("DrawableUtil can not be initialized");
    }

    private static Context getContext() {
        return ContextDelegate.getContext();
    }

    /**
     * 图片着色
     *
     * @param drawableRes 图片资源 R.drawable.xxx
     * @param color       颜色值 Color.RED 或 {@link ContextCompat#getColor(Context, int)}
     * @return 着色后的Drawable
     */
    public static Drawable tintDrawableRes(@DrawableRes int drawableRes, @ColorInt int color) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRes);
        return tintDrawable(drawable, color);
    }

    /**
     * @param drawable 比如{@link ImageView#getDrawable()}
     * @param color    颜色值Color.RED 或 {@link ContextCompat#getColor(Context, int)}
     * @return 着色后的Drawable
     */
    public static Drawable tintDrawable(Drawable drawable, @ColorInt int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

}
