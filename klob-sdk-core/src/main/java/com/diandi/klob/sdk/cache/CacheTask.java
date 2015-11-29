package com.diandi.klob.sdk.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-11  .
 * *********    Time : 08:23 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class CacheTask<T> implements ICache {

    private GetCacheListener mCacheListener;
    private String key;
    private Object object;

    public CacheTask(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public CacheTask(String key, GetCacheListener cacheListener) {
        this.key = key;
        mCacheListener = cacheListener;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void onSuccess(Object o) {
        if (mCacheListener != null && o != null) {
            if (o instanceof List) {
                mCacheListener.onSuccess((ArrayList<T>) o);
            }else {
                List<T> list=new ArrayList<>();
                list.add((T)o);
                mCacheListener.onSuccess(list);
            }
        }
    }

    public void onFailure(boolean exist) {
        if (mCacheListener != null) {
            mCacheListener.onFailure(exist);
        }
    }

    public interface GetCacheListener {
        void onSuccess(List list);

        void onFailure(boolean exist);
    }

}


