package com.diandi.klob.sdk.cache;

import java.util.ArrayList;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-11  .
 * *********    Time : 08:35 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public interface ICache {

    String getKey();

    void setKey(String key);

    Object getObject();

    void setObject(Object object);

    void onSuccess(Object o);

    void onFailure(boolean exist);


}
