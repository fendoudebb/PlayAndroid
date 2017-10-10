package com.fendoudebb.playandroid.module.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LazyLoadFragment extends Fragment {
    private static final String TAG = "LazyLoadFragment";
    protected boolean isViewInitiated;
    protected boolean isDataLoaded;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: start");
        isViewInitiated = true;
        prepareRequestData();
        Log.d(TAG, "onActivityCreated: end");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        initView(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(initLayout(),container,false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData();
        Log.d(TAG, "setUserVisibleHint: ");
    }

    public boolean prepareRequestData() {
        return prepareRequestData(false);
    }

    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            initData();
            isDataLoaded = true;
            return true;
        }
        return false;
    }

    @LayoutRes
    protected abstract int initLayout();

    protected abstract void initView(View view);

    protected abstract void initData();

}