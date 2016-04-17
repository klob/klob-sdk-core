package com.diandi.klob.sdk.cache;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-03-29  .
 * *********    Time : 15:22 .
 * *********    Description :
 * *********    Version : 1.0
 * *********    Copyright  © 2014-2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ACacheTask implements ICache {
    public GetCacheListener mCacheListener;
    public String key;
    public Object object;

    @Deprecated
    public ACacheTask(String key, Object object) {
        this.key = key;
        this.object = object;
    }
    @Deprecated
    public ACacheTask(String key, GetCacheListener cacheListener) {
        this.key = key;
        mCacheListener = cacheListener;
    }

    @Deprecated
    public ACacheTask(GetCacheListener cacheListener, String key) {
        mCacheListener = cacheListener;
        this.key = key;

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
            mCacheListener.onSuccess(o);
        }
    }

    public void onFailure(boolean exist) {
        if (mCacheListener != null) {
            mCacheListener.onFailure(exist);
        }
    }

    public interface GetCacheListener {
        void onSuccess(Object object);

        void onFailure(boolean exist);
    }

}
