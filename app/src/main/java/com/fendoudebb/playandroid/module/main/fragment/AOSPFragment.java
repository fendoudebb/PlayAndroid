package com.fendoudebb.playandroid.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.fragment.BaseRecyclerViewFragment;
import com.fendoudebb.playandroid.module.main.adapter.AOSPAdapter;
import com.fendoudebb.playandroid.module.main.data.OSFramework;
import com.fendoudebb.playandroid.util.ResUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zbj on 2017/9/27 21:40.
 */

public class AOSPFragment extends BaseRecyclerViewFragment {
    private static final String TAG = "AOSPFragment";
    private AOSPAdapter mAospAdapter;

    public static AOSPFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        AOSPFragment fragment = new AOSPFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onViewInit(View view, @Nullable Bundle savedInstanceState) {
        mAospAdapter = new AOSPAdapter();
        mRecyclerView.setAdapter(mAospAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        String[] osFrameworks = ResUtil.getStringArray(R.array.open_source_framework);

        List<OSFramework> frameworks = new ArrayList<>();
        for (String osFramework : osFrameworks) {
            String[] framework = osFramework.split("-");
            frameworks.add(new OSFramework(framework[0], framework[1]));
        }

        Log.d(TAG, "onViewInit:frameworks: " + frameworks);


        mAospAdapter.setDataSource(frameworks);


    }


}
