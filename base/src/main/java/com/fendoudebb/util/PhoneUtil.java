package com.fendoudebb.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * zbj on 2017-09-20 17:56.
 */

@SuppressLint("HardwareIds")
public final class PhoneUtil {

    private PhoneUtil() {
        throw new IllegalArgumentException("PhoneUtil can not be initialized");
    }

    private static Context getContext() {
        return ContextDelegate.getContext();
    }

    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取电话号码,移动/电信4G卡均不能获取
     * {@link TelephonyManager#getLine1Number()}
     * 需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return 获取手机号
     */
    public static String getPhoneNumber() {
        return getTelephonyManager().getLine1Number();
    }

    /**
     * 获取IMEI
     * {@link TelephonyManager#getDeviceId()}
     * 需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return IMEI码
     */
    public static String getIMEI() {
        return getTelephonyManager().getDeviceId();
    }

    /**
     * 获取IMSI
     * {@link TelephonyManager#getSubscriberId()}
     * 需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return IMSI码
     */
    public static String getIMSI() {
        return getTelephonyManager().getSubscriberId();
    }

    /**
     * 获取移动终端类型
     *
     * @return 手机制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为GSM，移动和联通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为CDMA，电信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public static int getPhoneType() {
        return getTelephonyManager().getPhoneType();
    }


    /**
     * 获取Sim卡运营商名称 如中国移动,中国电信,中国联通(CMCC...)
     * {@link TelephonyManager#getSimOperatorName()}
     *
     * @return sim卡运营商名称 如CMCC (可能返回空串)
     */
    public static String getSimOperatorName() {
        return getTelephonyManager().getSimOperatorName();
    }

    /**
     * 根据ismi前5位获取运营商名称
     * getIMSI方法需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return 中国三大运营商 中国移动 中国电信 中国联通
     */
    public static String getChineseOperatorName() {
        String imsiPrefix = getIMSI().substring(0, 5);
        Log.d("zbj", "getChineseOperatorName: " + imsiPrefix);
        switch (imsiPrefix) {
            case "46000":
            case "46002":
            case "46004":
            case "46007":
                return "中国移动";
            case "46003":
            case "46005":
            case "46011":
                return "中国电信";
            case "46001":
            case "46006":
            case "46009":
                return "中国联通";
            default:
                return "未知";
        }

    }

    /**
     * 获取Sim卡运营商数字编号(与imsi前5位相同) 如46002
     * {@link TelephonyManager#getSimOperator()}
     *
     * @return 运营商数字编号
     */
    public static String getSimOperator() {
        return getTelephonyManager().getSimOperator();
    }

    /**
     * 获取sim卡序列号 前六位运营商代码:中国移动的为：898600,中国联通的为:898601。
     * 注意：对于CDMA设备,返回的是一个空值! 中国电信为CDMA
     * {@link TelephonyManager#getSimSerialNumber()}
     * <p>
     * <p>
     * SIM卡上数字的意义,ICCID(SIM卡号码)的定义应该是：
     * <li>1-6位：国际移动运营商识别码(IMSI)：898600（中国移动），898601（中国联通）</li>
     * 在此之后的7-20位，移动和联通的定义是不同的!<br>
     * <p>
     * <P>
     * 中国移动：<br>
     * <li>第7、8位对应着网络号第3、4位</li>
     * <li>第9、10位各省编号：01北京、02天津、03河北、04山西、05内蒙、06辽宁、07吉林、08黑龙江、09上海、10江苏、11浙江、12安徽、13福建、14江西、15
     * 山东、16河南、17湖北、18湖南、19广东、20广西、21海南、22四川、23重庆、24贵州、25云南、26陕西、27甘肃、28青海、29宁夏、30新疆、31西藏。</li>
     * <li>第11、12位：编制ICCID时的年份。</li>
     * <li>第13位：SIM卡生产商的编号。</li>
     * <li>第14-19位：用户识别号。</li>
     * <p>
     * 如：89860 07031 01120 94320 是2001年生产的西藏移动的1370开头的全球通号码。<br>
     * <li>第20位：校验号。</li>
     * <p>
     * <p>
     * 中国联通：<br>
     * <li>第7、8位：编制ICCID时的年份。</li>
     * <li>第9-19位由于几个版本联通卡的定义不相同（有的第10-12位是当地区号，不足3位前面补0，如重庆为“023”，有的是第11-13位是当地区号），区号前面是SIM
     * 卡生产商的编号，区号后面是用户识别号。</li>
     * <li>第20位：校验号。</li>
     * <p>
     * 如：89860 10110 23117 0983S 是2001年生产的重庆联通的号码，还是32K的<br>
     * </p>
     * <p>
     * 需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     *
     * @return 序列号 如898600xxxxxxxxxxxxxx
     */
    public static String getSimSerialNumber() {
        return getTelephonyManager().getSimSerialNumber();
    }

    /**
     * 获取sim卡所属国家码
     * {@link TelephonyManager#getSimCountryIso()}
     *
     * @return 国家码 如 cn
     */
    public static String getSimCountryIso() {
        return getTelephonyManager().getSimCountryIso();
    }

    /**
     * 获取sim卡个数 {@link TelephonyManager#getPhoneCount()},需api>=23
     *
     * @return 0-没有数据,1-1张sim卡,2-2张sim卡
     */
    public static int getPhoneCount() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getTelephonyManager().getPhoneCount();
        }
        return -1;
    }

}
