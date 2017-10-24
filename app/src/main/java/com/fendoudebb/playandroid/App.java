package com.fendoudebb.playandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.fendoudebb.playandroid.util.LifeCycleUtil;

/**
 * author : zbj on 2017/8/19 22:03.
 */

public class App extends Application {

    private static final String TAG = "PlayAndroid_App";

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;
    private LifeCycleUtil mLifeCycleUtil;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext: " + base.toString());
        MultiDex.install(this);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        //onFragmentCreate LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        BuildConfig.BLOCKCANARY.init(this);

        BuildConfig.STETHO.init(this).configureInterceptor();

        //Open StrictMode in debug
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeathOnNetwork()
                    .build());
        }

        mLifeCycleUtil = new LifeCycleUtil(this);

    }

    @Override
    public void onTerminate() {
        mLifeCycleUtil.release(this);
        super.onTerminate();
    }

}
