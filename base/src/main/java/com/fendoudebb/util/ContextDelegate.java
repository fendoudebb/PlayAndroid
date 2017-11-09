package com.fendoudebb.util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * zbj on 2017-11-09 15:40.
 */

public class ContextDelegate {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext = null;

    public static void initContext(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new NullPointerException("Context == null, make sure ");
        }
        return sContext;
    }

}
