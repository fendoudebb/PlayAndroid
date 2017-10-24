package com.fendoudebb.playandroid.debug.blockcanary;

import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * zbj on 2017-10-24 14:43.
 */

public class DebugBlockCanaryHelper implements BlockCanaryHelper {

    @Override
    public void init(Context context) {
        BlockCanary.install(context, new AppBlockCanaryContext()).start();
    }
}
