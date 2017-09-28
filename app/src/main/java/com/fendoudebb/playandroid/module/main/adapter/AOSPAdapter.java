package com.fendoudebb.playandroid.module.main.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.feature.ui.WebViewActivity;
import com.fendoudebb.playandroid.module.main.data.OSFramework;

import java.util.List;

/**
 * author : zbj on 2017/9/27 21:47.
 */

public class AOSPAdapter extends Adapter<AOSPAdapter.AOSPViewHolder>{

    private List<OSFramework> mFrameworks;

    public void setDataSource(List<OSFramework> frameworks) {
        mFrameworks = frameworks;
        notifyDataSetChanged();
    }

    @Override
    public AOSPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_aosp, parent, false);
        final AOSPViewHolder viewHolder = new AOSPViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra(C.intent.web_view_url, mFrameworks.get(viewHolder
                        .getAdapterPosition()).website);
                v.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AOSPViewHolder holder, int position) {
        holder.mFrameworkName.setText(mFrameworks.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mFrameworks != null ? mFrameworks.size() : 0;
    }

    static class AOSPViewHolder extends RecyclerView.ViewHolder{

        private TextView mFrameworkName;

        AOSPViewHolder(View itemView) {
            super(itemView);
            mFrameworkName = (TextView) itemView.findViewById(R.id.aosp_name);
        }

    }

}
