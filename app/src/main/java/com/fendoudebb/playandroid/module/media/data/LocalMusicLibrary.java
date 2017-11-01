/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fendoudebb.playandroid.module.media.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;

import com.fendoudebb.playandroid.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class LocalMusicLibrary {


    private static final TreeMap<String, MediaMetadataCompat> music     = new TreeMap<>();
    private static final HashMap<String, String>              musicPath = new HashMap<>();

    private static final List<MediaBrowserCompat.MediaItem> result = new ArrayList<>();


    /**
     * 加载本地的音乐
     */
    public static void initSongList(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//uri
        /**
         * public String name;//歌曲名
         public String author;//歌手
         public String path;//路径

         // 艺术家
         private Uri artUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
         // 流派
         private Uri genresUri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;

         */
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE, //Never Had a Dream Come True
                MediaStore.Audio.Media.DISPLAY_NAME,//Never Had a Dream Come True.mp3
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.GENRE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.IS_MUSIC,
        };
        String selection = String.valueOf(MediaStore.Audio.Media.ALBUM_ID) + " != 1";
        Cursor c = context.getContentResolver().query(uri, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (c == null) {
            return;
        }
        while (c.moveToNext()) {
            String mediaId = c.getString(c.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
//            String musicFileName = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//            String genre = c.getString(c.getColumnIndex(MediaStore.Audio.Media.GENRE));
            long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String albumId = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
            Log.d("zbj1101", "path: " + path);
            int isMusic = c.getInt(c.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));

            if (isMusic != 0) {
                generateMediaMetadataCompat(mediaId, title, artist, album, "genre", duration ,
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI+"/"+albumId,path);
            }
        }
        c.close();
        Log.d("zbj1103", "initSongList: " + musicPath.toString());
    }

    static {
        initSongList(App.getContext());
    }

    public static String getRoot() {
        return "root";
    }

    public static String getMusicPath(String mediaId) {
        return  musicPath.containsKey(mediaId) ? musicPath.get(mediaId) : null;
    }

    public static Bitmap getAlbumBitmap(String mediaId) {
        String path = musicPath.get(mediaId);
        Log.d("zbj1101", "getAlbumBitmap: path:" + path);
        return createAlbumArt(path);
    }

    public static List<MediaBrowserCompat.MediaItem> getMediaItems() {
        if (result.size() > 0) {
            return result;
        }
        for (MediaMetadataCompat metadata : music.values()) {
            result.add(
                    new MediaBrowserCompat.MediaItem(
                            metadata.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }
        return result;
    }

    public static MediaMetadataCompat getMetadata(String mediaId) {
        MediaMetadataCompat metadataWithoutBitmap = music.get(mediaId);
        Bitmap albumArt = getAlbumBitmap(mediaId);

        // Since MediaMetadataCompat is immutable, we need to create a copy to set the album art.
        // We don't set it initially on all items so that they don't take unnecessary memory.
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder();
        for (String key :
                new String[]{
                        MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                        MediaMetadataCompat.METADATA_KEY_ALBUM,
                        MediaMetadataCompat.METADATA_KEY_ARTIST,
                        MediaMetadataCompat.METADATA_KEY_GENRE,
                        MediaMetadataCompat.METADATA_KEY_TITLE
                }) {
            builder.putString(key, metadataWithoutBitmap.getString(key));
        }
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                metadataWithoutBitmap.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt);
        return builder.build();
    }

    private static MediaMetadataCompat generateMediaMetadataCompat(
            String mediaId,
            String title,
            String artist,
            String album,
            String genre,
            long duration,
            String resUri,
            String path) {
        MediaMetadataCompat mediaMetadataCompat = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                        TimeUnit.MILLISECONDS.convert(duration, TimeUnit.SECONDS))
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, resUri)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, resUri)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON,getAlbumBitmap(mediaId))
                .build();
        Log.d("zbj1102", "generateMediaMetadataCompat() called with: mediaId = [" + mediaId + "], title" +
                " = [" + title + "], artist = [" + artist + "], album = [" + album + "], genre = " +
                "[" + genre + "], duration = [" + duration + "], resUri = [" + resUri + "], path " +
                "= [" + path + "]");
        music.put(mediaId, mediaMetadataCompat);
        musicPath.put(mediaId, path);
        return mediaMetadataCompat;
    }

    /**
     * @Description 获取专辑封面
     * @return 专辑封面bitmap
     */
    public static Bitmap createAlbumArt(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Bitmap bitmap = null;
        //能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path); //设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            if (embedPic != null) {
                bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        Log.d("zbj1101", "createAlbumArt:bitmap: " + bitmap + ",music: "+path);
        return bitmap;
    }
}