package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.location.LocationManager;

/**
 * zbj on 2017-09-20 17:56.
 */

public class GPSUtil {

    /**
     * 判断GPS是否打开
     * ACCESS_FINE_LOCATION权限
     *
     */
    public static boolean isGPSEnabled(Context context) {
        //获取手机所有连接LOCATION_SERVICE对象
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
