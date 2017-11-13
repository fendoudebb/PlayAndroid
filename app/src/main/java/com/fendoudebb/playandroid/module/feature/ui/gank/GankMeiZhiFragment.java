package com.fendoudebb.playandroid.module.feature.ui.gank;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.fendoudebb.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.api.ApiFactory;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;
import com.fendoudebb.playandroid.module.feature.adapter.GankMeiZhiAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zbj on 2017/10/10 12:49.
 */

public class GankMeiZhiFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener {
    private static final String TAG = "GankMeiZhiFragment";
    private RecyclerView mRecyclerView;
    private String       mTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int count = 1;
    private GankMeiZhiAdapter mGankMeiZhiAdapter;

    public static GankMeiZhiFragment newInstance(String title) {
        Bundle arguments = new Bundle();
        arguments.putString(C.gank.gank_title, title);
        GankMeiZhiFragment fragment = new GankMeiZhiFragment();
        fragment.setArguments(arguments);
        return fragment;
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

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mGankMeiZhiAdapter = new GankMeiZhiAdapter();

        mRecyclerView.setAdapter(mGankMeiZhiAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    //0为idle状态
                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                    if (layoutManager instanceof StaggeredGridLayoutManager){
                        ((StaggeredGridLayoutManager) layoutManager).invalidateSpanAssignments();
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int column = staggeredGridLayoutManager.getColumnCountForAccessibility(null,null);
                        int[] positions = new int[column];
                        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                        for (int position : positions) {
                            if (position >= staggeredGridLayoutManager.getItemCount() - column
                                    && staggeredGridLayoutManager.findViewByPosition(position)
                                    .getBottom() <=mRecyclerView.getHeight()) {
                                fetchData(++count);
                                break;
                            }
                        }

                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
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
                            mGankMeiZhiAdapter.setDataSource(gankData.results);
                        } else {
                            List<Gank> dataSource = mGankMeiZhiAdapter.getDataSource();
                            int size = dataSource.size();
                            dataSource.addAll(gankData.results);
                            mGankMeiZhiAdapter.notifyItemInserted(size - 1);
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
    public void onRefresh() {
        fetchData(++count);
    }
}
