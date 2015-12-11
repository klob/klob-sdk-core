package com.diandi.klob.sdk.bus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-01  .
 * *********    Time : 10:37 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class BusHandler {

    public abstract void onReceive( int what, Object obj);

    public void sendMsg( int what, Object obj) {
        onReceive(what, obj);
    }

    public void sendEmptyMsg( int what) {
        onReceive(what, (Object) null);

    }


}
