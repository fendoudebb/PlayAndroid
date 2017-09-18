package com.fendoudebb.playandroid.module.feature.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.feature.data.Feature;

import java.util.List;

/**
 * author : zbj on 2017/9/16 09:12.
 */

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {
    private static final String TAG = "FeatureAdapter";
    private List<Feature> mFeatures;

    public void setDataSource(List<Feature> features) {
        mFeatures = features;
        notifyDataSetChanged();
    }

    @Override
    public FeatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_feature, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext().getApplicationContext(), "点击了", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        return new FeatureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeatureViewHolder holder, int position) {
        holder.mFeatureName.setText(mFeatures.get(position).name);
        holder.mFeatureLogo.setImageResource(mFeatures.get(position).logo);
        Log.d(TAG, "onBindViewHolder: mFeatures.get(position).logo: " + mFeatures.get(position).logo);
    }

    @Override
    public int getItemCount() {
        return mFeatures != null ? mFeatures.size() : 0;
    }

    static class FeatureViewHolder extends RecyclerView.ViewHolder {

        private TextView mFeatureName;
        private ImageView mFeatureLogo;

        FeatureViewHolder(View itemView) {
            super(itemView);
            mFeatureName = (TextView) itemView.findViewById(R.id.item_feature_name);
            mFeatureLogo = (ImageView) itemView.findViewById(R.id.item_feature_logo);
        }
    }
}
