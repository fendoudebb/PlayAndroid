package com.fendoudebb.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * zbj on 2017-09-15 17:16.
 * 获取资源文件工具类
 */

public final class ResUtil {

    private ResUtil() {
        throw new IllegalArgumentException("ResUtil can not be initialized");
    }

    private static Context getContext() {
        return ContextDelegate.getContext();
    }

    /**
     * 获取Resources对象
     *
     * @return {@link Resources}
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 根据id获取string.xml中的配置的key值 如: <string name="clock">时钟</string> 获取到的是clock
     *
     * @param resId 资源id
     * @return string.xml中配置的key值
     */
    public static String getResName(@AnyRes int resId) {
        return getResources().getResourceEntryName(resId);
    }

    /**
     * 获取字符串
     *
     * @param id string.xml 中配置的id,R.string.xxx
     * @return 字符串
     */
    public static String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    /**
     * 获取字符串数组
     *
     * @param id array.xml中配置的string-array节点,R.array.xxx
     * @return 字符串数组
     */
    public static String[] getStringArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取int类型数组
     *
     * @param id array.xml中配置的integer-array节点,R.array.xxx
     * @return int类型数组
     */
    public static int[] getIntArray(@ArrayRes int id) {
        return getResources().getIntArray(id);
    }

    /**
     * 获取Drawable
     *
     * @param id drawable文件夹中的图片资源 R.drawable.xxx
     * @return drawable
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    /**
     * 获取颜色值
     *
     * @param id colors.xml中配置的color节点,R.color.xxx
     * @return 颜色的int值
     */
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public static int getId(String resourceName) {
        return getIdentifierByType(resourceName, "id");
    }

    public static int getLayoutId(String resourceName) {
        return getIdentifierByType(resourceName, "layout");
    }

    public static int getStringId(String resourceName) {
        return getIdentifierByType(resourceName, "string");
    }

    public static int getDrawableId(String resourceName) {
        return getIdentifierByType(resourceName, "drawable");
    }

    public static int getMipmapId(String resourceName) {
        return getIdentifierByType(resourceName, "mipmap");
    }

    public static int getColorId(String resourceName) {
        return getIdentifierByType(resourceName, "color");
    }

    public static int getDimenId(String resourceName) {
        return getIdentifierByType(resourceName, "dimen");
    }

    public static int getAttrId(String resourceName) {
        return getIdentifierByType(resourceName, "attr");
    }

    public static int getStyleId(String resourceName) {
        return getIdentifierByType(resourceName, "style");
    }

    public static int getAnimId(String resourceName) {
        return getIdentifierByType(resourceName, "anim");
    }

    public static int getArrayId(String resourceName) {
        return getIdentifierByType(resourceName, "array");
    }

    public static int getIntegerId(String resourceName) {
        return getIdentifierByType(resourceName, "integer");
    }

    public static int getBoolId(String resourceName) {
        return getIdentifierByType(resourceName, "bool");
    }

    private static int getIdentifierByType(String resourceName, String defType) {
        return getResources().getIdentifier(resourceName, defType, getContext().getPackageName());
    }

}
