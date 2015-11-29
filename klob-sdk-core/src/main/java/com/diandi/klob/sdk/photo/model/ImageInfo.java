package com.diandi.klob.sdk.photo.model;

import java.io.File;
import java.io.Serializable;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class ImageInfo implements Serializable {
    public String path;
    public long photoId;
    public int width;
    public int height;
    private static final String prefix = "file://";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageInfo(String path) {

        this.path = pathAddPreFix(path);
    }

    public static String pathAddPreFix(String path) {
        if (!path.startsWith(prefix)) {
            path = prefix + path;
        }
        return path;
    }

    public static boolean isLocalFile(String path) {
        return path.startsWith(prefix);
    }


    public static File getLocalFile(String pathSrc) {
        String pathDesc = pathSrc;
        if (isLocalFile(pathSrc)) {
            pathDesc = pathSrc.substring(prefix.length(), pathSrc.length());
        }

        return new File(pathDesc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        if (photoId != imageInfo.photoId) return false;
        if (width != imageInfo.width) return false;
        if (height != imageInfo.height) return false;
        return !(path != null ? !path.equals(imageInfo.path) : imageInfo.path != null);

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (int) (photoId ^ (photoId >>> 32));
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
