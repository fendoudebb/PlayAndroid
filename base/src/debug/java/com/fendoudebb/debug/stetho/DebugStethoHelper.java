package com.fendoudebb.debug.stetho;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * zbj on 2017-10-23 10:16.
 */

public class DebugStethoHelper implements StethoHelper {

    @Override
    public StethoHelper init(Context context) {
        Stetho.initializeWithDefaults(context);
        return this;
    }

    @Override
    public OkHttpClient configureInterceptor() {
        return new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
    }
}
