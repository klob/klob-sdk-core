package com.diandi.klob.sdk.cache;

import android.content.Context;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-29  .
 * *********    Time : 15:14 .
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CacheFactory {
    private static CacheTaskExecutor mCacheTaskExecutor;

    public static CacheDispatcher create(Context context) {
        if (mCacheTaskExecutor == null) {
            mCacheTaskExecutor = CacheTaskExecutor.getInstance();
        }
        return new CacheDispatcher(context, mCacheTaskExecutor);
    }
}
