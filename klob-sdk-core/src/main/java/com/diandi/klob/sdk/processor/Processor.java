package com.diandi.klob.sdk.processor;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-15  .
 * *********    Time : 07:59 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class Processor {

    private Processor() {

    }

    public static void execute(WorkHandler handler) {
        SimpleExecutor.getInstance().execute(new WorkTask(handler));
    }

    public static void post(Runnable r) {
        SimpleTask.post(r);
    }

    public static void postDelay(Runnable r, long delayMillis) {
        SimpleTask.postDelay(r, delayMillis);
    }


    public static class WorkTask extends SimpleTask {

        WorkHandler mHandler;

        public WorkTask(WorkHandler dataHandler) {
            mHandler = dataHandler;
        }

        @Override
        public void doInBackground() {
            mHandler.start();
        }

        @Override
        public void onFinish(boolean canceled) {
            post(new Runnable() {
                @Override
                public void run() {
                    mHandler.over();
                }
            });
        }
    }
}
