package com.fendoudebb.playandroid.module.feature.ui.music;

import android.content.ComponentName;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.fendoudebb.playandroid.R;
import com.fendoudebb.playandroid.module.base.activity.BaseActivity;
import com.fendoudebb.playandroid.module.media.service.MusicService;

/**
 * author : zbj on 2017/10/11 16:37.
 */

public class MusicActivity extends BaseActivity {

    private static final String TAG = "MusicActivity";

    private MediaBrowserCompat mMediaBrowser;

    // Callback that ensures that we are showing the controls
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
                    Log.d(TAG, "onPlaybackStateChanged() called with: state = [" + state + "]");
                    /*if (shouldShowControls()) {
                        showPlaybackControls();
                    } else {
                        LogHelper.d(TAG, "mediaControllerCallback.onPlaybackStateChanged: " +
                                "hiding controls because state is ", state.getState());
                        hidePlaybackControls();
                    }*/
                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.d(TAG, "onMetadataChanged() called with: metadata = [" + metadata + "]");
                    /*if (shouldShowControls()) {
                        showPlaybackControls();
                    } else {
                        LogHelper.d(TAG, "mediaControllerCallback.onMetadataChanged: " +
                                "hiding controls because metadata is null");
                        hidePlaybackControls();
                    }*/
                }
            };

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.d(TAG, "onConnected");
                    try {
                        connectToSession(mMediaBrowser.getSessionToken());
                    } catch (RemoteException e) {
                        Log.e(TAG,"could not connect media controller");
                    }
                }
            };

    @Override
    protected int initContentView() {
        return R.layout.activity_music;
    }

    @Override
    protected void init() {
        mMediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, MusicService.class), mConnectionCallback, null);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called - mMediaBrowser.connect()");
        mMediaBrowser.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(this);
        if (controllerCompat != null) {
            controllerCompat.unregisterCallback(mMediaControllerCallback);
        }
        mMediaBrowser.disconnect();
    }

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);
        mediaController.registerCallback(mMediaControllerCallback);

        /*if (shouldShowControls()) {
            showPlaybackControls();
        } else {
            LogHelper.d(TAG, "connectionCallback.onConnected: " +
                    "hiding controls because metadata is null");
            hidePlaybackControls();
        }

        if (mControlsFragment != null) {
            mControlsFragment.onConnected();
        }

        onMediaControllerConnected();*/
    }
}
