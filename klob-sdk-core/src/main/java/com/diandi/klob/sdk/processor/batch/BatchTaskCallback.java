package com.diandi.klob.sdk.processor.batch;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-23  .
 * *********    Time : 09:42 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public interface BatchTaskCallback {
    void onSuccess(int index);

    void onFailure();

    void onFinish(int progress);

    void onCancel();
}
