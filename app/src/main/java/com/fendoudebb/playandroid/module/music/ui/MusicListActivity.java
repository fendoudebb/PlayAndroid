package com.fendoudebb.playandroid.module.music.ui;

import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fendoudebb.activity.BaseActivity;
import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.music.adapter.MediaItemAdapter;
import com.fendoudebb.playandroid.module.music.data.LocalMusicLibrary;
import com.fendoudebb.playandroid.module.music.listener.MediaBrowserChangeListener;
import com.fendoudebb.playandroid.module.music.manager.MediaBrowserManager;
import com.fendoudebb.widget.rv.BaseRecyclerViewAdapter;

/**
 * author : zbj on 2017/10/11 16:37.
 */

public class MusicListActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener<MediaBrowserCompat.MediaItem> {

    private static final String TAG = "MusicListActivity-zbj";
    private MediaBrowserManager mMediaBrowserCallback;
    private RecyclerView mMusicListRV;


    @Override
    protected int initContentView() {
        return R.layout.activity_music;
    }

    @Override
    protected void initView() {
        mMusicListRV = findViewById(R.id.music_list_rv);
    }

    @Override
    protected void initData() {
        mMediaBrowserCallback = new MediaBrowserManager(this);
        mMediaBrowserCallback.addListener(new MediaBrowserListener());

        MediaItemAdapter mediaItemAdapter = new MediaItemAdapter();
        mMusicListRV.setAdapter(mediaItemAdapter);
        mMusicListRV.setHasFixedSize(true);
        mMusicListRV.setItemAnimator(new DefaultItemAnimator());
        mMusicListRV.setLayoutManager(new LinearLayoutManager(mMusicListRV.getContext()));

        mediaItemAdapter.setDataSource(LocalMusicLibrary.getMediaItems());

        mediaItemAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaBrowserCallback.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mSeekBarAudio.disconnectController();
        mMediaBrowserCallback.onStop();
    }

    public void onClick(View view) {
        mMediaBrowserCallback.getTransportControls().play();
        Log.d(TAG, "onClick: 点击 了!!!播放啊");
    }

    @Override
    public void onItemClick(View v, MediaBrowserCompat.MediaItem mediaItem, int position) {
        Log.d(TAG, "onItemClick() called with: v = [" + v + "], mediaItem = [" + mediaItem + "], " +
                "position = [" + position + "]");
        mMediaBrowserCallback.getTransportControls().playFromMediaId(mediaItem.getMediaId(), null);
    }

    private class MediaBrowserListener extends MediaBrowserChangeListener {

        @Override
        public void onConnected(@Nullable MediaControllerCompat mediaController) {
            super.onConnected(mediaController);
//            mSeekBarAudio.setMediaController(mediaController);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat playbackState) {
//            mIsPlaying = playbackState != null &&
//                    playbackState.getState() == PlaybackStateCompat.STATE_PLAYING;
//            mMediaControlsImage.setPressed(mIsPlaying);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadata) {
            if (mediaMetadata == null) {
                return;
            }
            /*mTitleTextView.setText(
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            mArtistTextView.setText(
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            mAlbumArt.setImageBitmap(LocalMusicLibrary.getAlbumBitmap(
                    MainActivity.this,
                    mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)));*/
        }
    }


}
