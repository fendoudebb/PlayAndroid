package com.fendoudebb.playandroid.module.feature.adapter;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.feature.data.SystemIntent;
import com.fendoudebb.widget.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.widget.rv.ViewHolder;

/**
 * author : zbj on 2017/9/30 22:24.
 */

public class SystemIntentAdapter extends BaseRecyclerViewAdapter<SystemIntent> {

    @Override
    protected int initItemLayout() {
        return R.layout.item_common_tv;
    }

    @Override
    protected void bindData(ViewHolder holder, SystemIntent gotoIntent) {
        holder.setText(R.id.item_common_name, gotoIntent.name);
    }

}
