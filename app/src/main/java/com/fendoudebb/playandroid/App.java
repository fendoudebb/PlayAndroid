package com.fendoudebb.playandroid;

import android.app.Application;
import android.os.StrictMode;

/**
 * author : zbj on 2017/8/19 22:03.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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

    }
}
