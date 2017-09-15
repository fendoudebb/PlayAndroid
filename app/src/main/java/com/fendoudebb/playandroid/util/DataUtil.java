package com.fendoudebb.playandroid.util;

import com.fendoudebb.playandroid.App;
import com.fendoudebb.playandroid.R;

/**
 * zbj on 2017-09-15 17:16.
 */

public class DataUtil {

    public static String[] getFeatureName() {
        return App.getContext().getResources().getStringArray(R.array.feature_name);
    }

    public static int[] getFeatureLogo() {
        return App.getContext().getResources().getIntArray(R.array.feature_logo);
    }



}
