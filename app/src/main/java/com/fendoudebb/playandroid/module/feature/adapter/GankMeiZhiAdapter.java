package com.fendoudebb.playandroid.module.feature.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.rv.ViewHolder;

/**
 * author : zbj on 2017/10/10 15:23.
 */

public class GankMeiZhiAdapter extends BaseRecyclerViewAdapter<Gank> {

    @Override
    protected int initItemLayout() {
        return R.layout.item_common_img;
    }

    @Override
    protected void bindData(ViewHolder holder, Gank gank) {
        Glide.with(holder.itemView.getContext())
                .load(gank.url)
                .into((ImageView) holder.getView(R.id.item_common_pic));
    }
}
