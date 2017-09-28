package com.fendoudebb.playandroid.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * author : zbj on 2017/8/19 21:14.
 * An animator designed to add an effect for activity recreate
 */

public final class RevealEffectUtil {
    private static final String TAG = "RevealEffectUtil";
    private enum Status {
        ENTER, EXIT
    }

    public void createEnterRevealEffect(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View rootView = ((ViewGroup)activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
            rootView.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    Animator revealEffect = createRevealEffect(rootView, Status.ENTER);
                    Log.d(TAG, "run: 111111111");
                    revealEffect.start();
                }
            });
        }
    }

    public void createExitRevealEffect(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View rootView = ((ViewGroup)activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
            Animator revealEffect = createRevealEffect(rootView, Status.EXIT);
            revealEffect.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.setVisibility(View.INVISIBLE);
                    activity.recreate();
                }
            });
            revealEffect.start();
        } else {
            activity.recreate();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator createRevealEffect(View rootView, Status status) {
        float radius = (float) Math.hypot(rootView.getWidth(), rootView.getHeight());
        float startRadius = status == Status.ENTER ? 0 : radius;
        float endRadius = status == Status.ENTER ? radius : 0;
        Animator animator = ViewAnimationUtils.createCircularReveal(rootView
                , rootView.getWidth(), rootView.getHeight(),
                startRadius, endRadius);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

}
