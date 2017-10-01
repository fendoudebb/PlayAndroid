package com.fendoudebb.playandroid.module.feature.adapter;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.playandroid.module.base.rv.ViewHolder;
import com.fendoudebb.playandroid.module.feature.data.Feature;

/**
 * author : zbj on 2017/9/16 09:12.
 */

public class FeatureAdapter extends BaseRecyclerViewAdapter<Feature> {
    private static final String TAG = "FeatureAdapter";

    @Override
    protected int initItemLayout() {
        return R.layout.item_feature;
    }

    @Override
    protected void bindData(ViewHolder holder, Feature feature) {
        holder.setText(R.id.item_feature_name, feature.nameId)
                .setImageResource(R.id.item_feature_logo, feature.logo);
    }

}
