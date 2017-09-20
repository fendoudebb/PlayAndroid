package com.fendoudebb.playandroid.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.fendoudebb.playandroid.App;

/**
 * zbj on 2017-09-20 17:56.
 */

@SuppressLint("HardwareIds")
public class PhoneInfoUtil {

    private PhoneInfoUtil() {
        throw new IllegalArgumentException("PhoneInfoUtil can not be initialized");
    }

    private static Context getContext() {
        return App.getContext();
    }

    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取电话号码,移动/电信4G卡均不能获取
     *
     * @return 获取手机号
     */
    public static String getPhoneNumber() {
        return getTelephonyManager().getLine1Number();
    }

    /**
     * 获取本机串号IMEI
     *
     */
    public static String getIMEI() {
        return getTelephonyManager().getDeviceId();
    }
}
