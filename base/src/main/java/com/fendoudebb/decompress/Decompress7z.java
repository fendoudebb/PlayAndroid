package com.fendoudebb.decompress;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * zbj on 2016-12-22 09:47.
 * 7zip 解压
 */

public class Decompress7z {

    public static boolean decompress7zip(String filePath, String outPath) {
        File outDir = new File(outPath);
        if (!outDir.exists() || !outDir.isDirectory()) {
            outDir.mkdirs();
        }
        return (decompress7z(filePath, outPath) == 0);
    }

    /**
     * Decompress from assets
     *
     * @param context 上下文
     * @param assetPath assets下的目录
     * @param outPath 解压至路径
     * @return 解压是否成功
     */
    public static boolean decompressFromAssets(Context context, String assetPath, String outPath) {
        File outDir = new File(outPath);
        if (!outDir.exists() || !outDir.isDirectory()) {
            outDir.mkdirs();
        }

        String tempPath = outPath + File.separator + ".temp";
        try {
            copyFromAssets(context, assetPath, tempPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        boolean ret = (decompress7z(tempPath, outPath) == 0);
        new File(tempPath).delete();

        return ret;
    }

    /**
     * Copy asset to temp
     *
     * @param context 上下文
     * @param assetPath assets下的目录
     * @param tempPath 临时存放的位置
     * @throws Exception io异常
     */
    private static void copyFromAssets(Context context, String assetPath, String tempPath)
            throws Exception {
        InputStream inputStream = context.getAssets().open(assetPath);
        FileOutputStream fileOutputStream = new FileOutputStream(tempPath);
        int length = -1;
        byte[] buffer = new byte[0x400000];
        while ((length = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, length);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
    }

    //JNI interface
    private static native int decompress7z(String filePath, String outPath);

    static {
        System.loadLibrary("fendoudebb_decompress7z");
    }
}
