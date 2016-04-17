package com.diandi.klob.sdk.cache;

import android.content.Context;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.ThreadPoolFactory;

import java.util.concurrent.Executor;

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
    private static Executor mCacheTaskExecutor;

    @Deprecated
    public static CacheDispatcher create(Context context) {
        if (mCacheTaskExecutor == null) {
            mCacheTaskExecutor = ThreadPoolFactory.createSmallExecutor();
        }
        return new CacheDispatcher(mCacheTaskExecutor);
    }

    public static CacheDispatcher create() {
        if (mCacheTaskExecutor == null) {
            mCacheTaskExecutor = ThreadPoolFactory.createSmallExecutor();
        }
        return new CacheDispatcher(mCacheTaskExecutor);
    }
}
