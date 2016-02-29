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
    public TimeUnit unit = TimeUnit.MINUTES;
    public int period = 1;
    public int initialDelay = 0;
    public int delay=0;
}