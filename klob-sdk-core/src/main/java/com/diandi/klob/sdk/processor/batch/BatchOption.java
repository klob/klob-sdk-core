package com.diandi.klob.sdk.processor.batch;

import java.util.concurrent.TimeUnit;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-26  .
 * *********    Time : 21:49 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BatchOption {

    public BatchMode mode = BatchMode.SEQUENCE;

    /**
     * unit the time unit of the initialDelay and period parameters
     */
    public TimeUnit unit = TimeUnit.MINUTES;
    /**
     * period the period between successive executions
     */
    public int period = 1;
    /**
     * initialDelay the time to delay first execution
     */
    public int initialDelay = 0;
    /**
     * 延迟
     */
    public int delay = 0;
    public boolean isBackGround = false;
}