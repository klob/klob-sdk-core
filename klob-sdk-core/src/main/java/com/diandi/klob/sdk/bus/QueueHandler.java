package com.diandi.klob.sdk.bus;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-08  .
 * *********    Time : 21:25 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class QueueHandler {
    public abstract void onReceive( int what, Object obj);

    public abstract void doNext(int index);

    public abstract void finish();

    public void sendMsg( int what, Object obj) {
        onReceive(what, obj);
    }

    public void sendEmptyMsg( int what) {
        onReceive(what, (Object) null);

    }
}
