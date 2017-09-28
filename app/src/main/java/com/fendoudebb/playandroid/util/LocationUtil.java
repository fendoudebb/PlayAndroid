package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.location.LocationManager;

import com.fendoudebb.playandroid.App;

/**
 * zbj on 2017-09-20 17:56.
 */

public final class LocationUtil {

    private LocationUtil() {
        throw new IllegalArgumentException("LocationUtil can not be initialized");
    }

    private static Context getContext() {
        return App.getContext();
    }

    /**
     * 获取手机所有连接LOCATION_SERVICE对象
     *
     * @return 位置信息管理者
     */
    private static LocationManager getLocationManager() {
        return ((LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE));
    }

    /**
     * 判断GPS是否打开
     * ACCESS_FINE_LOCATION权限
     *
     * @return GPS是否可用
     */
    public static boolean isGPSEnabled() {
        return getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
