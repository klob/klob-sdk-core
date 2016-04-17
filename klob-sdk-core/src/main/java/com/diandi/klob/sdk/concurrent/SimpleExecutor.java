package com.diandi.klob.sdk.concurrent;

import java.util.concurrent.Executor;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-04-17  .
 * *********    Time : 09:55 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class SimpleExecutor implements Executor {

    private static Executor sInstance = null;

    public static Executor getInstance() {
        return sInstance;
    }

    static {
        if (sInstance == null) {
            sInstance = ThreadPoolFactory.createExecutor();
        }
    }

    public void execute(Runnable runnable) {
        sInstance.execute(runnable);
    }


}