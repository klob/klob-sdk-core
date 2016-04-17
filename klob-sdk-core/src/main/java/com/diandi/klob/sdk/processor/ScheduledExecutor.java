package com.diandi.klob.sdk.processor;

import com.diandi.klob.sdk.concurrent.SimpleTask;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-03-09  .
 * *********    Time : 21:58 .
 * *********    Version : 1.0
 * *********    Copyright © 2016, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ScheduledExecutor {
    private static ScheduledExecutor sScheduledExecutor;
    private static ScheduledExecutorService sInternalExecutor;
    private static int processorsCount = Runtime.getRuntime().availableProcessors();

    static {
        sInternalExecutor = Executors.newScheduledThreadPool(processorsCount);
        sScheduledExecutor = new ScheduledExecutor();
    }

    public static ScheduledExecutor getInstance() {
        return sScheduledExecutor;
    }

    public void execute(Runnable runnable, long delay) {
        sInternalExecutor.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    /*public void execute(final Runnable runOnUiThread, final int delay) {
        new MainThreadDelayedTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread();
            }

            @Override
            public void runOnUiThread() {
                SimpleTask.post(runOnUiThread);
            }
        };
        sInternalExecutor.schedule(task, 0, TimeUnit.SECONDS);
    }*/

}
