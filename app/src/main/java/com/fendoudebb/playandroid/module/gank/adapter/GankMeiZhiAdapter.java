package com.fendoudebb.playandroid.module.gank.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.GlideApp;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.rv.ViewHolder;

/**
 * author : zbj on 2017/10/10 15:23.
 */

public class GankMeiZhiAdapter extends PagedListAdapter<Gank,ViewHolder> {

    public GankMeiZhiAdapter() {
        super(new DiffCallback<Gank>() {
            @Override
            public boolean areItemsTheSame(@NonNull Gank oldItem, @NonNull Gank newItem) {
                return TextUtils.equals(oldItem._id, newItem._id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Gank oldItem, @NonNull Gank newItem) {
                return TextUtils.equals(oldItem.url, newItem.url);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_common_img, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gank gank = getItem(position);
        if (gank != null) {
            GlideApp.with(holder.itemView.getContext())
                    .load(gank.url)
                    .centerCrop()
                    .into((ImageView) holder.getView(R.id.item_common_pic));
        }
    }
}
