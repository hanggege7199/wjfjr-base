package com.tphz.zh_base.common;

import android.content.Context;
import android.os.Environment;

public class Constants {
    private static String AppCacheDir;
    public static Context mContext;

    public static Context getContext() {
        return  mContext;
    }

    public static void init(Context context) {
        Constants.mContext = context;
        Constants.AppCacheDir = context.getFilesDir().getPath();
    }

    public static String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String getAppDir() {
        return AppCacheDir != null
                ? AppCacheDir
                : SD_CARD_PATH + "/" + mContext.getPackageName();
    }

    /**
     * 录音目录
     * @return
     */
    public static String getRecordDir() {
        return getAppDir() + "/record/";
    }

    /**
     * 下载目录
     * @return
     */
    public static String getDownloadDir() {
        return getAppDir() + "/download/";
    }

    /**
     * apk目录
     * @return
     */
    public static String getApkDir() {
        return getAppDir() + "/download/";
    }
}
