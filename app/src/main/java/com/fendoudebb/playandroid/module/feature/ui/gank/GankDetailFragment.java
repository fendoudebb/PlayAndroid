package com.fendoudebb.playandroid.module.feature.ui.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fendoudebb.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.api.ApiFactory;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;
import com.fendoudebb.playandroid.module.feature.adapter.GankNewsAdapter;
import com.fendoudebb.playandroid.module.feature.ui.WebViewActivity;
import com.fendoudebb.rv.BaseRecyclerViewAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zbj on 2017/10/7 20:07.
 */

public class GankDetailFragment extends BaseFragment implements BaseRecyclerViewAdapter
        .OnItemClickListener<Gank>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "GankDetail-zbj";
    private RecyclerView       mRecyclerView;
    private String             mTitle;
    private GankNewsAdapter    mGankNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int count = 1;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItem;

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.gank_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R
                .color.holo_green_light, android.R.color.holo_orange_light, android.R.color
                .holo_red_light);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.gank_detail_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);



        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mGankNewsAdapter.getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    //实际项目中这里一般是用网络请求获取数据
                    fetchData(++count);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (mLayoutManager instanceof LinearLayoutManager) {
                    //获取最后一个可见view的位置
                    mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
//            int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                }
            }
        });

        mGankNewsAdapter = new GankNewsAdapter();
        mGankNewsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mGankNewsAdapter);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        mTitle = arguments.getString(C.gank.gank_title);

        fetchData(count);
    }

    private void fetchData(final int count) {
        ApiFactory.getGankApi()
                .getCategoryData(mTitle, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(@NonNull GankData gankData) {
                        Log.d(TAG, "GankData: " + gankData.toString());
                        if (gankData.error) {
                            return;
                        }
                        if (count == 1) {
                            mGankNewsAdapter.setDataSource(gankData.results);
                        } else {
                            mGankNewsAdapter.getDataSource().addAll(gankData.results);
                            mGankNewsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: e:" + e.getMessage());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onItemClick(View v, Gank gank, int position) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(C.intent.web_view_url, gank.url);
        startActivity(intent);

    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: count1111: " + count);
        /*if (mSwipeRefreshLayout.isRefreshing()) {
            ToastUtil.showToast("正在刷新请稍后");
            return;
        }*/

        fetchData(++count);
        Log.d(TAG, "onRefresh: count2222: " + count);


    }
}
