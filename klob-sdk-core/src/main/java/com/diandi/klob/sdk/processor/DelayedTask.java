package com.diandi.klob.sdk.processor;

import com.diandi.klob.sdk.concurrent.SimpleTask;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-03-09  .
 * *********    Time : 22:13 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class DelayedTask  implements Runnable{

    int mDelay;

    public DelayedTask(int delay) {
        mDelay = delay;
    }

}