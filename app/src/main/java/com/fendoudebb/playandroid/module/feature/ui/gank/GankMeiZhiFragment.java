package com.fendoudebb.playandroid.module.feature.ui.gank;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.api.ApiFactory;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;
import com.fendoudebb.playandroid.module.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.module.feature.adapter.GankMeiZhiAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zbj on 2017/10/10 12:49.
 */

public class GankMeiZhiFragment extends BaseFragment {
    private static final String TAG = "GankMeiZhiFragment";
    private RecyclerView mRecyclerView;
    private String       mTitle;

    public static GankMeiZhiFragment newInstance(String title) {
        Bundle arguments = new Bundle();
        arguments.putString(C.gank.gank_title, title);
        GankMeiZhiFragment fragment = new GankMeiZhiFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_detail_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_gank_detail;
    }

    @Override
    public void initData() {
        Log.d("LazyLoadFragment", "initData: ");
        Bundle arguments = getArguments();
        mTitle = arguments.getString(C.gank.gank_title);

        ApiFactory.getGankApi()
                .getCategoryData(mTitle, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(@NonNull GankData gankData) {
                        Log.d(TAG, "GankData: " + gankData.toString());
                        if (gankData.error) {
                            return;
                        }
                        GankMeiZhiAdapter gankMeiZhiAdapter = new GankMeiZhiAdapter();

                        mRecyclerView.setAdapter(gankMeiZhiAdapter);
                        gankMeiZhiAdapter.setDataSource(gankData.results);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: e:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }
}
