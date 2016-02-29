package com.diandi.klob.sdk.processor.batch;

import com.diandi.klob.sdk.concurrent.SimpleTask;

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
    protected abstract void onReceive(int what, Object obj);

    protected abstract void doNext(int index);

    protected abstract void finish();

    protected void sendMsg(int what, Object obj) {
        onReceive(what, obj);
    }

    protected void sendMsgDelay(final int what, int delay) {
        SimpleTask.postDelay(new Runnable() {
            @Override
            public void run() {
                onReceive(what, null);
            }
        }, delay);

    }

    protected void sendEmptyMsg(int what) {
        onReceive(what, (Object) null);
    }
}
