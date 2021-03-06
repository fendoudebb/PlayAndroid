package com.fendoudebb.playandroid.module.gank.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.widget.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.widget.rv.ViewHolder;

/**
 * author : zbj on 2017/10/10 15:18.
 */

public class GankNewsAdapter extends BaseRecyclerViewAdapter<Gank> {

    @Override
    protected int initItemLayout() {
        return R.layout.item_gank_news;
    }

    @Override
    protected void bindData(ViewHolder holder, Gank gank) {
        holder.setText(R.id.item_gank_news_title, gank.desc)
                .setText(R.id.item_gank_news_author, gank.who);
        if (gank.images != null) {
            Glide.with(holder.itemView.getContext())
//                    .asBitmap()
                    .load(gank.images.get(0))
                    .into((ImageView) holder.getView(R.id.item_gank_news_pic));
            holder.setVisibleGone(R.id.item_gank_news_pic, true);
        } else {
            holder.setVisibleGone(R.id.item_gank_news_pic, false);
        }
    }
}
