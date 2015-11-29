package com.diandi.klob.sdk.util;

import java.util.Collection;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CollectionUtils {

    public static boolean isNotNull(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Collection<?> collection) {
        if (collection == null) {
            return true;
        } else if (collection.size() == 0) {
            return true;
        }
        return false;
    }

}
