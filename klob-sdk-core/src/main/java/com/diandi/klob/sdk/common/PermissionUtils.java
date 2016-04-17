package com.diandi.klob.sdk.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.diandi.klob.sdk.XApplication;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-03-23  .
 * *********    Time : 16:01 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PermissionUtils {
    public static boolean checkPermission(Activity context, String permission) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(XApplication.getInstance(), permission)) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int requestCode = PermissionRequestCode.get(permission);
                context.requestPermissions(new String[]{permission}, requestCode);
            }
            return false;
        }
    }

    public static boolean checkPermission(Fragment context, String permission) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(XApplication.getInstance(), permission)) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int requestCode = PermissionRequestCode.get(permission);
                context.requestPermissions(new String[]{permission}, requestCode);
            }
            return false;
        }
    }
}
