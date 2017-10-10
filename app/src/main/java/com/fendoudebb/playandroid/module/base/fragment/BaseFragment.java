package com.fendoudebb.playandroid.module.base.fragment;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * zbj on 2017-09-22 15:44.
 */

public abstract class BaseFragment extends LazyLoadFragment {

    @LayoutRes
    protected abstract int initLayout();

    protected abstract void initView(View view);

    protected abstract void initData();

}
