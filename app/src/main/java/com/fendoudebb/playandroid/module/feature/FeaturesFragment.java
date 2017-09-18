package com.fendoudebb.playandroid.module.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fendoudebb.playandroid.module.feature.adapter.FeatureAdapter;
import com.fendoudebb.playandroid.module.feature.data.Feature;

import java.util.List;

/**
 * zbj on 2017-09-15 14:45.
 */

public class FeaturesFragment extends Fragment implements FeaturesContract.View {

    private FeaturesContract.Presenter mPresenter;
    private RecyclerView               mRecyclerView;
    private FeatureAdapter             mFeatureAdapter;

    public static FeaturesFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        FeaturesFragment fragment = new FeaturesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FeaturesPresenter(this);
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
        mFeatureAdapter = new FeatureAdapter();
        mRecyclerView.setAdapter(mFeatureAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        /*mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int mSpace = (int) UnitConverter.dp2px(10);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                outRect.top = outRect.left = outRect.right = outRect.bottom = mSpace;
            }
        });*/

    }

    @Override
    public void setPresenter(FeaturesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFeatures(List<Feature> features) {
        mFeatureAdapter.setDataSource(features);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }
}
