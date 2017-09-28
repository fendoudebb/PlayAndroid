package com.fendoudebb.playandroid.module.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author : zbj on 2017/9/27 22:16.
 */

public abstract class BaseRecyclerViewFragment extends Fragment {

    protected RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onFragmentCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return new RecyclerView(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view;
        onViewInit(view, savedInstanceState);
    }

    protected abstract void onFragmentCreate(@Nullable Bundle savedInstanceState);

    protected abstract void onViewInit(View view, @Nullable Bundle savedInstanceState);


}
