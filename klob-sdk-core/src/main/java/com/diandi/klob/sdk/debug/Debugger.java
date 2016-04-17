package com.diandi.klob.sdk.debug;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.diandi.klob.sdk.util.L;

import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-03-05  .
 * *********    Time : 10:31 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class Debugger {
    private final static String TAG="Debugger";
    public  static void printActivityInfo(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);
        if(appTask!=null)
        {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : appTask) {
                Log.d(TAG,recentTaskInfo.baseIntent.toString());
            }
        }

    }
}
