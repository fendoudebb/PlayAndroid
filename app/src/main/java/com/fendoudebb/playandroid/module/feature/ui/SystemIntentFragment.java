package com.fendoudebb.playandroid.module.feature.ui;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fendoudebb.base.fragment.BaseRecyclerViewFragment;
import com.fendoudebb.playandroid.module.feature.adapter.SystemIntentAdapter;
import com.fendoudebb.playandroid.module.feature.data.SystemIntent;
import com.fendoudebb.playandroid.util.SystemIntentUtil;
import com.fendoudebb.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.util.ToastUtil;

import java.util.List;

/**
 * author : zbj on 2017/9/30 22:22.
 */

public class SystemIntentFragment extends BaseRecyclerViewFragment implements BaseRecyclerViewAdapter.OnItemClickListener<SystemIntent> {

    private SystemIntentAdapter mSystemIntentAdapter;

    public static SystemIntentFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        SystemIntentFragment fragment = new SystemIntentFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onViewInit(View view, @Nullable Bundle savedInstanceState) {
        mSystemIntentAdapter = new SystemIntentAdapter();
        mRecyclerView.setAdapter(mSystemIntentAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mSystemIntentAdapter.setOnItemClickListener(this);

        List<SystemIntent> systemIntents = new SystemIntentUtil().getSystemIntents();

        mSystemIntentAdapter.setDataSource(systemIntents);

    }

    @Override
    public void onItemClick(View v, SystemIntent systemIntent, int position) {
        try {
            startActivity(systemIntent.intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showToast("没有对应设置");
        }
    }
}
