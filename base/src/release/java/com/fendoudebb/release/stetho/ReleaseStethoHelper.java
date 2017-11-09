package com.fendoudebb.release.stetho;

import android.content.Context;

import com.fendoudebb.debug.stetho.StethoHelper;

import okhttp3.OkHttpClient;

/**
 * zbj on 2017-10-23 10:16.
 */

public class ReleaseStethoHelper implements StethoHelper {

    @Override
    public StethoHelper init(Context context) {
        return this;
    }

    @Override
    public OkHttpClient configureInterceptor() {
        return null;
    }
}
