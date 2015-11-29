package com.diandi.klob.sdk.photo.model;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ImageInfoExtra {
    private ImageInfo mImageInfo;
    private int mCount = 0;
    private String mName = "";

    public ImageInfoExtra(String name, ImageInfo mImageInfo, int count) {
        mName = name;
        this.mImageInfo = mImageInfo;
        mCount = count;
    }

    public String getPath() {
        return mImageInfo.path;
    }

    public int getCount() {
        return mCount;
    }

    public String getmName() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfoExtra that = (ImageInfoExtra) o;

        if (mCount != that.mCount) return false;
        return mImageInfo.equals(that.mImageInfo);

    }

    @Override
    public int hashCode() {
        int result = mImageInfo.hashCode();
        result = 31 * result + mCount;
        return result;
    }
}
