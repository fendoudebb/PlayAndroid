package com.fendoudebb.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

/**
 * zbj on 2017-09-28 14:15.
 */

public final class ClipboardUtil {

    private ClipboardUtil() {
        throw new IllegalArgumentException("ClipboardUtil can not be initialized");
    }

    private static Context getContext() {
        return ContextDelegate.getContext();
    }

    private static ClipboardManager getClipboardManager() {
        return ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE));
    }

    /**
     * 复制文本到剪贴板
     *
     * @param text 文本
     */
    public static void copyText(CharSequence text) {
        getClipboardManager().setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        ClipData primaryClip = getClipboardManager().getPrimaryClip();
        if (primaryClip.getItemCount() > 0) {
            return primaryClip.getItemAt(0).coerceToText(getContext());
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(final Intent intent) {
        getClipboardManager().setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        ClipData clip = getClipboardManager().getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }

}
