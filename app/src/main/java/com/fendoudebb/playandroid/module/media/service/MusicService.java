package com.fendoudebb.playandroid.module.media.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.media.MediaRouter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.fendoudebb.playandroid.util.MediaIDHelper.MEDIA_ID_EMPTY_ROOT;
import static com.fendoudebb.playandroid.util.MediaIDHelper.MEDIA_ID_ROOT;

/**
 * author : zbj on 2017/10/11 16:44.
 */

public class MusicService extends MediaBrowserServiceCompat {
    private static final String TAG = "MusicService";

    private MediaSessionCompat mSession;
    private MediaRouter mMediaRouter;

    @Override
    public void onCreate() {
        super.onCreate();
        mSession = new MediaSessionCompat(this, TAG);
        setSessionToken(mSession.getSessionToken());

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSession.release();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable
            Bundle rootHints) {
        Log.d(TAG, "onGetRoot() called with: clientPackageName = [" + clientPackageName + "], " +
                "clientUid = [" + clientUid + "], rootHints = [" + rootHints + "]");
        return new BrowserRoot(MEDIA_ID_ROOT, null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.d(TAG, "onLoadChildren() called with: parentId = [" + parentId + "], result = [" +
                result + "]");
        if (MEDIA_ID_EMPTY_ROOT.equals(parentId)) {
            result.sendResult(new ArrayList<MediaBrowserCompat.MediaItem>());
        } /*else if (mMusicProvider.isInitialized()) {
            // if music library is ready, return immediately
            result.sendResult(mMusicProvider.getChildren(parentMediaId, getResources()));
        }*/ else {
            // otherwise, only return results when the music library is retrieved
            result.detach();
            /*mMusicProvider.retrieveMediaAsync(new MusicProvider.Callback() {
                @Override
                public void onMusicCatalogReady(boolean success) {
                    result.sendResult(mMusicProvider.getChildren(parentMediaId, getResources()));
                }
            });*/
        }
    }
}
