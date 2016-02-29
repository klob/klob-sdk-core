package com.diandi.klob.sdk;

import android.app.Application;

import com.diandi.klob.sdk.cache.ACache;
import com.diandi.klob.sdk.common.Global;
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
    public static Application sInstance;

    public static Application getInstance() {
        return sInstance;
    }

    public static void init(Application application) {
        init(application, false);
    }

    public static void init(Application application, boolean isDebug) {
        sInstance = application;
        PhotoOption.isWaterMark = false;
        L.setLoggable(isDebug);
        ImageLoadTool.initImageLoader(application);
    }

    public ACache getCache() {
        return Global.getCache();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }




}