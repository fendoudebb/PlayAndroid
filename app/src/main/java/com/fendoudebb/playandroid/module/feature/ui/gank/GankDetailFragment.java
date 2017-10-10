package com.fendoudebb.playandroid.module.feature.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.api.ApiFactory;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;
import com.fendoudebb.playandroid.module.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.module.base.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.playandroid.module.feature.adapter.GankNewsAdapter;
import com.fendoudebb.playandroid.module.feature.ui.WebViewActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zbj on 2017/10/7 20:07.
 */

public class GankDetailFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener<Gank> {
    private static final String TAG = "GankDetail-zbj";
    private RecyclerView mRecyclerView;
    private String       mTitle;
    private GankNewsAdapter mGankNewsAdapter;

    public static GankDetailFragment newInstance(String title) {
        Bundle arguments = new Bundle();
        arguments.putString(C.gank.gank_title, title);
        GankDetailFragment fragment = new GankDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_gank_detail;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_detail_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mGankNewsAdapter = new GankNewsAdapter();
        mGankNewsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mGankNewsAdapter);
    }

    @Override
    protected void initData() {

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

                        mGankNewsAdapter.setDataSource(gankData.results);
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

    @Override
    public void onItemClick(View v, Gank gank, int position) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(C.intent.web_view_url, gank.url);
        startActivity(intent);

    }
}
