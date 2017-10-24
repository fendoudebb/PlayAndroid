package com.fendoudebb.playandroid.module.media.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fendoudebb.playandroid.module.base.fragment.BaseRecyclerViewFragment;
import com.fendoudebb.playandroid.module.feature.adapter.MediaItemAdapter;

/**
 * author : zbj on 2017/10/11 12:24.
 */

public class MusicFragment extends BaseRecyclerViewFragment {
    private static final String TAG = "MusicFragment";
    private MediaBrowserCompat mMediaBrowser;



    public static MusicFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onViewInit(View view, @Nullable Bundle savedInstanceState) {
        MediaItemAdapter mediaItemAdapter = new MediaItemAdapter();
        mRecyclerView.setAdapter(mediaItemAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

    }

}
