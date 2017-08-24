package com.fendoudebb.playandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fendoudebb.playandroid.config.C;

/**
 * author : zbj on 2017/8/18 22:49.
 */

public class SpUtil {

    private static final String SP_NAME = "SpConfig";

    private static SharedPreferences mSp;

    private static volatile SpUtil singleton = null;

    private SpUtil(Context context) {
        mSp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static SpUtil with(Context context) {
        if (singleton == null) {
            synchronized (SpUtil.class) {
                if (singleton == null) {
                    singleton = new SpUtil(context);
                }
            }
        }
        return singleton;
    }

    /**
     * Set config info
     *
     * @param key   Constant field config in see{@link C}
     * @param value true or false
     */
    public void putBoolean(String key, boolean value) {
        mSp.edit().putBoolean(key, value).apply();
    }

    /**
     * Get config info
     *
     * @param key      Constant field config in see{@link C}
     * @param defValue default value
     * @return the boolean config
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }


}
