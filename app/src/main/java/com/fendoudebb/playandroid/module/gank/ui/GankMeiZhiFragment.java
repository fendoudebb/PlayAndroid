package com.fendoudebb.playandroid.module.gank.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.fendoudebb.base.fragment.BaseFragment;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.playandroid.module.gank.adapter.GankMeiZhiAdapter;
import com.fendoudebb.playandroid.module.gank.vm.GankViewModel;
import com.fendoudebb.util.ToastUtil;

/**
 * author : zbj on 2017/10/10 12:49.
 */

public class GankMeiZhiFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener {
    private static final String TAG = "GankMeiZhiFragment";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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

        mSwipeRefreshLayout = view.findViewById(R.id.gank_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R
                .color.holo_green_light, android.R.color.holo_orange_light, android.R.color
                .holo_red_light);

        mRecyclerView = view.findViewById(R.id.gank_detail_rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

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
                                Log.d(TAG, "onScrollStateChanged: 滑动到底部了");
                                ToastUtil.showToast("滑动到底部了!");
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
        String title = arguments.getString(C.gank.gank_title);

        GankViewModel gankViewModel = ViewModelProviders.of(this).get(GankViewModel.class);
        gankViewModel.getGankLiveData(title).observe(this, new Observer<PagedList<Gank>>() {
            @Override
            public void onChanged(@Nullable PagedList<Gank> ganks) {
                mGankMeiZhiAdapter.setList(ganks);
            }
        });

    }



    @Override
    public void onRefresh() {

    }
}
