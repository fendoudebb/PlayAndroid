package com.fendoudebb.playandroid.module.music.adapter;

import android.graphics.Bitmap;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.widget.rv.BaseRecyclerViewAdapter;
import com.fendoudebb.widget.rv.ViewHolder;

/**
 * author : zbj on 2017/10/11 16:08.
 */

public class MediaItemAdapter extends BaseRecyclerViewAdapter<MediaBrowserCompat.MediaItem> {
    private static final String TAG = "MediaItemAdapter";
    @Override
    protected int initItemLayout() {
        return R.layout.item_media_item;
    }

    @Override
    protected void bindData(ViewHolder holder, MediaBrowserCompat.MediaItem mediaItem) {
        Log.d(TAG, "bindData() called with: holder = [" + holder + "], mediaItem = [" + mediaItem
                + "]");
        MediaDescriptionCompat description = mediaItem.getDescription();
        Log.d(TAG, "bindData: description: " + description.toString());
        holder.setText(R.id.media_item_name, description.getTitle());
        Bitmap iconBitmap = description.getIconBitmap();
        Log.d(TAG, "iconBitmap: " + iconBitmap);
        holder.setImageBitmap(R.id.media_item_pic, iconBitmap);
    }
}
