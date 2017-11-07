package com.fendoudebb.playandroid.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * zbj on 2017-09-29 17:45.
 * 生命周期回调
 */

public class LifeCycleUtil implements Application.ActivityLifecycleCallbacks, ComponentCallbacks,ComponentCallbacks2 {

    private static final String TAG = "LifeCycleUtil";

    private boolean isAppBackground = false;

    public LifeCycleUtil(Application application) {
        application.registerActivityLifecycleCallbacks(this);
        application.registerComponentCallbacks(this);
    }

    public void release(Application application) {
        application.unregisterActivityLifecycleCallbacks(this);
        application.unregisterComponentCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated() called with: activity = [" + activity + "], " +
                "savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
        if (isAppBackground) {
            isAppBackground = false;
            //来广告了
            Toast.makeText(activity.getApplicationContext(), "弹出一个广告", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "APP 进入前台");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], " +
                "outState = [" + outState + "]");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged() called with: newConfig = [" + newConfig + "]");
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory() called");
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d(TAG, "onTrimMemory() called with: level = [" + level + "]");
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isAppBackground = true;
            Log.d(TAG, "APP 退出到后台");
        }
    }
}
