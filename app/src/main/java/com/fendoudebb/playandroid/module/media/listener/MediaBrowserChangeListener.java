package com.fendoudebb.playandroid.module.media.listener;

import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Helper class for easily subscribing to changes in a MediaBrowserService connection.
 */
public abstract class MediaBrowserChangeListener {

        public void onConnected(@Nullable MediaControllerCompat mediaController) {
        }

        public void onMetadataChanged(@Nullable MediaMetadataCompat mediaMetadata) {
        }

        public void onPlaybackStateChanged(@Nullable PlaybackStateCompat playbackState) {
        }
    }