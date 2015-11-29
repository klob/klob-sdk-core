package com.diandi.klob.sdk.cache;

import android.annotation.TargetApi;
import android.os.Build;

import com.diandi.klob.sdk.concurrent.LinkedBlockingDeque;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use a Thread pool to manager the thread.
 *
 * @author http://www.liaohuqiu.net
 */
public class CacheTaskExecutor implements Executor {

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;
    private static final int KEEP_ALIVE_TIME = 1;

    private static int sNUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static CacheTaskExecutor sInstance = null;

    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        sInstance = new CacheTaskExecutor();
    }

    private final ThreadPoolExecutor mThreadPool;
    private final LinkedBlockingStack<Runnable> mTaskWorkQueue;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private CacheTaskExecutor() {

        mTaskWorkQueue = new LinkedBlockingStack<Runnable>();
        mThreadPool = new ThreadPoolExecutor(sNUMBER_OF_CORES, sNUMBER_OF_CORES, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mTaskWorkQueue, new DefaultThreadFactory());
        if (Build.VERSION.SDK_INT >= 9) {
            mThreadPool.allowCoreThreadTimeOut(true);
        } else {
            // Does nothing
        }
    }

    public static CacheTaskExecutor getInstance() {
        return sInstance;
    }

    @Override
    public void execute(Runnable command) {
        mThreadPool.execute(command);
    }

    public void setTaskOrder(int order) {
        mTaskWorkQueue.setTaskOrder(order);
    }

    public static class LinkedBlockingStack<T> extends LinkedBlockingDeque<T> {

        private static final long serialVersionUID = -4114786347960826192L;
        private int mCacheTaskOrder = 1;

        public void setTaskOrder(int order) {
            mCacheTaskOrder = order;
        }

        @Override
        public boolean offer(T e) {
            if (mCacheTaskOrder == 1) {
                return super.offerFirst(e);
            } else {
                return super.offer(e);
            }
        }

        @Override
        public T remove() {
            if (mCacheTaskOrder == 2) {
                return super.removeFirst();
            } else {
                return super.remove();
            }
        }
    }

    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private static final String sPre = "image-executor-pool-";
        private static final String sPost = "-thread-";
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = sPre + poolNumber.getAndIncrement() + sPost;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}