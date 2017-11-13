package com.fendoudebb.playandroid.module.main.adapter;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.main.data.OSFramework;
import com.fendoudebb.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.rv.ViewHolder;

/**
 * author : zbj on 2017/9/27 21:47.
 */

public class AOSPAdapter extends BaseRecyclerViewAdapter<OSFramework> {

    @Override
    protected int initItemLayout() {
        return R.layout.item_common_tv;
    }

    @Override
    protected void bindData(ViewHolder holder, OSFramework osFramework) {
        holder.setText(R.id.item_common_name, osFramework.name);
    }

}
