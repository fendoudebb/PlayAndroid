package com.fendoudebb.playandroid.module.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.base.fragment.BaseRecyclerViewFragment;
import com.fendoudebb.playandroid.module.base.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.playandroid.module.feature.adapter.FeatureAdapter;
import com.fendoudebb.playandroid.module.feature.data.Feature;

import java.util.List;

/**
 * zbj on 2017-09-15 14:45.
 */

public class FeaturesFragment extends BaseRecyclerViewFragment implements FeaturesContract.View, BaseRecyclerViewAdapter.OnItemClickListener<Feature> {

    private FeaturesContract.Presenter mPresenter;
    private FeatureAdapter             mFeatureAdapter;

    public static FeaturesFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("", "");
        FeaturesFragment fragment = new FeaturesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void onFragmentCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = new FeaturesPresenter(this);
    }

    @Override
    protected void onViewInit(View view, @Nullable Bundle savedInstanceState) {
        mFeatureAdapter = new FeatureAdapter();
        mRecyclerView.setAdapter(mFeatureAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mFeatureAdapter.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(View v, Feature feature, int position) {
        Intent intent = new Intent(v.getContext(), FeatureDetailActivity.class);
        intent.putExtra(C.intent.feature_name_id, feature.nameId);
        v.getContext().startActivity(intent);
        Toast.makeText(v.getContext().getApplicationContext(), "点击了", Toast.LENGTH_SHORT)
                .show();
    }
}
