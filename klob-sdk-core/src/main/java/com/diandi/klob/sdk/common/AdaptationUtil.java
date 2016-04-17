package com.diandi.klob.sdk.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.debug.DebugTime;
import com.diandi.klob.sdk.util.L;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-04-04  .
 * *********    Time : 21:42 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class AdaptationUtil {
    public final static String NAVIGATION_BAR_HEIGHT = "NavigationBarHeight";
    private static final String TAG = "AdaptationUtil";
    private static AdaptationUtil.ConfigPool mConfigPool = new AdaptationUtil.ConfigPool();

    public static Object getConfig(String key) {
        return mConfigPool.get(key);
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        L.e(TAG, hasNavigationBar);
        return hasNavigationBar;

    }

    static class ConfigPool {
        Map<String, Object> mConfigPools = new HashMap<>();

        public Object get(String key) {
            Object value = null;
            if (mConfigPools.containsKey(key)) {
                value = mConfigPools.get(key);
            } else {
                if (key.equals(NAVIGATION_BAR_HEIGHT)) {
                    value = AdaptationUtil.getNavigationBarHeight(XApplication.getInstance());
                }
                mConfigPools.put(key, value);
            }
            return value;


        }
    }
}
