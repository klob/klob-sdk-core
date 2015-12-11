package com.diandi.klob.sdk;

import android.app.Application;

import com.diandi.klob.sdk.cache.ACache;
import com.diandi.klob.sdk.photo.ImageLoadTool;
import com.diandi.klob.sdk.photo.PhotoOption;
import com.diandi.klob.sdk.util.L;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class XApplication extends Application {
    public final static String TAG = "KApplication";
    public static XApplication sInstance;
    private ACache mACache;

    public static XApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PhotoOption.isWaterMark=false;
        sInstance = this;
        L.setLoggable(com.diandi.klob.sdk.core.BuildConfig.DEBUG);
        ImageLoadTool.initImageLoader(this);
    }

    public ACache getCache() {
        if (mACache == null) {
            mACache = ACache.get(getApplicationContext());
        }
        return mACache;
    }

}