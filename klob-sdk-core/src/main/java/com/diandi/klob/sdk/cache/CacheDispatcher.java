package com.diandi.klob.sdk.cache;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.processor.Processor;
import com.diandi.klob.sdk.util.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-28  .
 * *********    Time : 19:45 .
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CacheDispatcher {
    private static final String TAG = " CacheDispatcher";
    private static ACache mCache;
    private Executor mTaskExecutor;


    CacheDispatcher(Executor taskExecutor) {
        mTaskExecutor = taskExecutor;
        mCache = ACache.getInstance();
    }

    public void removeCache(String key) {
        mCache.remove(key);
    }

    public void addPutTask(ICache task) {
        new PutCacheTask(mCache, task);
    }

    public void addGetTask(ICache task) {
        new GetCacheTask(mCache, task, mTaskExecutor);
    }

    public static class GetCacheTask extends SimpleTask {
        private ICache mCacheTask;
        private ACache mDispatcher;
        private Object mObject;
        private Executor mTaskExecutor;

        public GetCacheTask(ACache dispatcher, ICache cacheTask, Executor executor) {
            mDispatcher = dispatcher;
            mCacheTask = cacheTask;
            mTaskExecutor = executor;
            //  run();
            mTaskExecutor.execute(this);
        }

        @Override
        public void doInBackground() {
            mObject = mDispatcher.getAsObject(mCacheTask.getKey());
//            L.e(TAG, "get key     " + mCacheTask.getKey() + "    " + mObject);
//            L.e(TAG + "list", mObject);
            if (mObject != null) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mCacheTask.onSuccess(mObject);
                        } catch (Exception e) {
                            mCacheTask.onFailure(true);
                            e.printStackTrace();
                        }
                    }
                });

            } else {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mCacheTask.onFailure(false);
                    }
                });
            }
        }

        @Override
        public void onFinish(boolean canceled) {

        }


    }

    public static class PutCacheTask extends SimpleTask {
        private ICache mCacheTask;
        private ACache mDispatcher;

        public PutCacheTask(ACache dispatcher, ICache cacheTask) {
            mDispatcher = dispatcher;
            mCacheTask = cacheTask;
            Processor.execute(this);
            //  run();
        }

        @Override
        public void doInBackground() {
            //  L.v(TAG, "put key    " + mCacheTask.getKey() + "    " + mCacheTask.getObject());
            if (mCacheTask.getObject() instanceof List) {
                //  L.e(TAG, "list  ");
                mDispatcher.put(mCacheTask.getKey(), (ArrayList) mCacheTask.getObject());
            } else {
                //   L.e(TAG, "Serializable");
                mDispatcher.put(mCacheTask.getKey(), (Serializable) mCacheTask.getObject()
                );
            }
        }

        @Override
        public void onFinish(boolean canceled) {
        }


    }

}
