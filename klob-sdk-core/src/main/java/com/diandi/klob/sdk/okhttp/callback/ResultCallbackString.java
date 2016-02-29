package com.diandi.klob.sdk.okhttp.callback;

import com.squareup.okhttp.Request;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-19  .
 * *********    Time : 11:16 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public abstract class ResultCallbackString extends ResultCallback<String> {
    @Override
    public abstract void onError(Request request, Exception e);

    @Override
    public abstract void onResponse(String response);
}
