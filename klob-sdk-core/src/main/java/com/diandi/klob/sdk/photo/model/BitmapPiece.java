package com.diandi.klob.sdk.photo.model;

import android.graphics.Bitmap;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-12-08  .
 * *********    Time : 15:02 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BitmapPiece {
    public Bitmap bitmap;
    public int index;

    @Override
    public String toString() {
        return "BitmapPiece{" +
                "getWidth=" + bitmap.getWidth() +
                "getHeight=" + bitmap.getHeight() +
                ", index=" + index +
                '}';
    }
}
