package com.fendoudebb.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * zbj on 2017-08-22 13:48.
 */

public final class UnitConverter {

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem()
                .getDisplayMetrics());
    }

    public static float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem()
                .getDisplayMetrics());
    }

}
