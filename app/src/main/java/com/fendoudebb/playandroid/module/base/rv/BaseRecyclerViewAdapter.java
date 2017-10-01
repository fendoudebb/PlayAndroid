package com.fendoudebb.playandroid.module.base.rv;

import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author : zbj on 2017/9/30 22:26.
 * RecyclerView BaseAdapter
 */

public abstract class BaseRecyclerViewAdapter<T extends Parcelable> extends Adapter<ViewHolder> {

    private List<T> mT;

    private OnItemClickListener<T>  onItemClickListener;//单击事件
    private OnItemLongClickListener<T> onItemLongClickListener;//长按单击事件

    public void setDataSource(List<T> t) {
        mT = t;
        notifyDataSetChanged();
    }

    public List<T> getDataSource() {
        return mT;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(initItemLayout(), parent, false);
        final ViewHolder holder = new ViewHolder(v);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(v, mT.get(position), position);
                }
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    int position = holder.getAdapterPosition();
                    onItemLongClickListener.onItemLongClick(v, mT.get(position), position);
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindData(holder, mT.get(position));
    }

    @Override
    public int getItemCount() {
        return mT != null ? mT.size() : 0;
    }

    @LayoutRes
    protected abstract int initItemLayout();

    protected abstract void bindData(ViewHolder holder, T t);

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListner(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View v, T t, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View v, T t, int position);
    }
}
