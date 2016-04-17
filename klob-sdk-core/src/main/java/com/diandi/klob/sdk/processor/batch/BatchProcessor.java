package com.diandi.klob.sdk.processor.batch;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.processor.WorkHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-23  .
 * *********    Time : 09:44 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BatchProcessor extends QueueHandler {
    private final static String TAG = "BatchProcessor";
    private BatchTaskCallback mBatchTaskCallback;
    private List<WorkHandler> mHandlers;
    private List<WorkTask> mTasks;
    private int mIndex;
    private int mTaskSize;
    private int mProgress;
    private boolean mIsCancel;
    private BatchMode mMode = BatchMode.SEQUENCE;
    private BatchOption mTaskOption;
    private ScheduledExecutorService mExecutorService;

    public BatchProcessor(List<WorkHandler> handlers, BatchOption option, BatchTaskCallback batchTaskCallback) {
        mBatchTaskCallback = batchTaskCallback;
        mHandlers = handlers;
        mTaskOption = option;
        mMode = option.mode;
        initTask();

    }

   /* public BatchProcessor(List<WorkHandler> handlers, BatchTaskCallback batchTaskCallback, boolean executeInSequence) {
        mBatchTaskCallback = batchTaskCallback;
        mHandlers = handlers;
        if (executeInSequence) {
            mMode = Mode.SEQUENCE;
        } else {
            mMode = Mode.ASYNC;
        }
        initTask();

    }

    public BatchProcessor(List<WorkHandler> handlers, BatchTaskCallback batchTaskCallback) {
        this(handlers, batchTaskCallback, true);
    }*/

    private void initTask() {
        mTasks = new ArrayList<>();
        for (WorkHandler handler : mHandlers) {
            mTasks.add(new WorkTask(handler));
        }
        mTaskSize = mTasks.size();
        isBackground = mTaskOption.isBackGround;
    }

    @Override
    protected void sendEmptyMsg(int what) {
        super.sendEmptyMsg(what);

    }

    public void start() {
        if (mMode == BatchMode.SEQUENCE) {
            sendEmptyMsg(0);
        } else if (mMode == BatchMode.ASYNC) {
            for (int i = 0; i < mTaskSize; i++) {
                sendEmptyMsg(i);
            }
        } else if (mMode == BatchMode.SCHEDULE) {
            mExecutorService = Executors.newScheduledThreadPool(1000);
            mExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (!mIsCancel) {
                        {
                            int delay = mTaskOption.delay;
                            if (delay == 0) {

                            } else {
                                try {
                                    delay = new Random(System.currentTimeMillis()).nextInt(mTaskOption.delay);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    delay = 0;
                                }
                            }

                            sendMsgDelay(mIndex, delay * 1000);
                        }
                    }
                }
            }, mTaskOption.initialDelay, mTaskOption.period, mTaskOption.unit);

        }
    }

    @Override
    protected void onReceive(int what, Object obj) {
        doNext(what);
    }

    @Override
    protected void doNext(int index) {
        mIndex = index;
        if (!mIsCancel) {
            if (index < mTaskSize) {
                executeTask(index);
                mIndex++;
            }
        }
    }

    protected void executeTask(int index) {
        SimpleExecutor.getInstance().execute(mTasks.get(index));
    }

    @Override
    protected void finish() {
        SimpleTask.post(new Runnable() {
            @Override
            public void run() {
                if (mBatchTaskCallback != null) {
                    mBatchTaskCallback.onFinish(mProgress);
                }
                if (mExecutorService != null) {
                    mExecutorService.shutdown();
                }
            }
        });

    }

    public void cancel() {
        mIsCancel = true;
        if (mBatchTaskCallback != null) {
            for (WorkTask task : mTasks) {
                task.cancel();
            }
            mBatchTaskCallback.onCancel();

        }
        finish();
    }


    class WorkTask extends SimpleTask {

        WorkHandler mHandler;

        public WorkTask(WorkHandler dataHandler) {
            mHandler = dataHandler;
        }

        @Override
        public void doInBackground() {
            if (!mIsCancel) {
                mHandler.start();
            }
        }

        @Override
        public void onFinish(boolean canceled) {
            if (!mIsCancel) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mProgress++;
                        mHandler.over();
                        if (mBatchTaskCallback != null) {
                            mBatchTaskCallback.onSuccess(mIndex);
                        }
                        if (mMode == BatchMode.SEQUENCE) {
                            sendEmptyMsg(mIndex);
                        }
                        if (mProgress == mTaskSize) {
                            finish();
                        }

                    }
                });
            }
        }
    }
}
