package com.diandi.klob.sdk.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;


import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
@Deprecated
public class CacheUtils {
    private static final String TAG = "CacheUtils";
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * 获取/data/data/files目录
     *
     * @param context
     * @return
     */
    public static File getFileDirectory(Context context) {
        File appCacheDir = null;
        if (appCacheDir == null) {
            appCacheDir = context.getFilesDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/files/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    public static File getCacheDirectory(Context context, boolean preferExternal, String dirName) {
        File appCacheDir = null;
        if (preferExternal && MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context, dirName);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            L.e("Can't define system cache directory! '%s' will be used.", cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context, String dirName) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir2 = new File(new File(dataDir, context.getPackageName()), "cache");
        File appCacheDir = new File(appCacheDir2, dirName);
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                L.e(TAG, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                L.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

}
