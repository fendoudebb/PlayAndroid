package com.fendoudebb.playandroid.module.api;

/**
 * author : zbj on 2017/10/3 12:12.
 */

public class ApiFactory {

    private static GankApi gankApi = null;

    public static GankApi getGankApi() {
        if (gankApi == null) {
            synchronized (ApiFactory.class) {
                if (gankApi == null) {
                    gankApi = new ApiRetrofit().getGankApi();
                }
            }
        }
        return gankApi;
    }

}
