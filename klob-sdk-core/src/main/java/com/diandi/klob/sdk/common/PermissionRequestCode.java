package com.diandi.klob.sdk.common;

import java.util.HashMap;
import java.util.Map;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-03-23  .
 * *********    Time : 16:15 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class PermissionRequestCode {
    static int sRequestValue = -1;
    static Map<String, Integer> sPermissionHash = new HashMap<>();

    public synchronized static int get(String permission) {
        if (sPermissionHash.containsKey(permission)) {
            return sPermissionHash.get(permission);
        } else {
            sRequestValue++;
            sPermissionHash.put(permission, sRequestValue);
            return sRequestValue;
        }
    }
}
