package com.diandi.klob.sdk.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-04-17  .
 * *********    Time : 09:55 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ThreadPoolFactory {

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final String sDefaultThreadNamePrefix = "simple-executor-pool-";
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    public static Executor createLowLevelExecutor() {
        return createExecutor(Thread.NORM_PRIORITY - 2);
    }

    public static Executor createHighLevelExecutor() {
        return createExecutor(Thread.NORM_PRIORITY + 2);
    }

    public static Executor createSmallExecutor() {
        return createExecutor(sDefaultThreadNamePrefix, CORE_POOL_SIZE / 2 + 1, CORE_POOL_SIZE, Thread.NORM_PRIORITY);
    }

    public static Executor createExecutor() {
        return createExecutor(sDefaultThreadNamePrefix, CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, Thread.NORM_PRIORITY);
    }

    public static Executor createExecutor(int priority) {
        return createExecutor(sDefaultThreadNamePrefix, CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, priority);
    }

    public static Executor createExecutor(String threadNamePrefix, int corePoolSize, int maxPoolSize, int priority) {
        BlockingDeque<Runnable> taskWorkQueue = new LinkedBlockingDeque<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskWorkQueue, new DefaultThreadFactory(threadNamePrefix, priority));
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private static final String sPost = "-thread-";
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private int threadPriority = Thread.NORM_PRIORITY;

        private DefaultThreadFactory(String threadNamePrefix) {
            this(threadNamePrefix, Thread.NORM_PRIORITY);
        }

        private DefaultThreadFactory(String threadNamePrefix, int priority) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + sPost;
            threadPriority = priority;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            t.setPriority(threadPriority);
            return t;
        }
    }
}
