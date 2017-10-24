package com.fendoudebb.playandroid.debug.stetho;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * zbj on 2017-10-23 10:15.
 */

public interface StethoHelper {

    StethoHelper init(Context context);

    OkHttpClient configureInterceptor();

}
