package com.diandi.klob.sdk.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.diandi.klob.sdk.XApplication;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class FileUtils {


    public final static String DOWNLOAD_SETTING = "download_setting";
    public final static String PACKAGE_NAME = XApplication.getInstance().getPackageName();
    public final static String DOWNLOAD_FOLDER = PACKAGE_NAME;
    public final static String APP_PATH = File.separator+PACKAGE_NAME.substring(PACKAGE_NAME.lastIndexOf(".") + 1)+File.separator;
    public final static String APP_STORAGE_FOLDER = Environment.getExternalStorageDirectory() + APP_PATH + "storage" + File.separator;
    public final static String APP_CACHE_PATH = Environment.getExternalStorageDirectory() + APP_PATH + "cache" + File.separator;
    public final static String PICTURE_PATH = Environment.getExternalStorageDirectory() + APP_PATH + "image" + File.separator;
    public final static String IMAGE_CACHE_PATH = Environment.getExternalStorageDirectory() + APP_PATH + "image/cache" + File.separator;
    public final static String VIDEO_PATH = Environment.getExternalStorageDirectory() + APP_PATH + "video" + File.separator;
    public final static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + APP_PATH + "download" + File.separator;
    private static final String TAG = "FileUtils";

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static File getDestinationInExternalPublicDir(String dirType, String fileName) {
        File file = Environment.getExternalStoragePublicDirectory(dirType);
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IllegalStateException(file.getAbsolutePath() +
                        " already exists and is not a directory");
            }
        } else {
            if (!file.mkdirs()) {
                L.e("createDir", "Unable to create directory: \" +\n" +
                        "                        file.getAbsolutePath()");
        /*        throw new IllegalStateException("Unable to create directory: " +
                        file.getAbsolutePath());*/
            }
        }
        return new File(file.getAbsolutePath() + File.separator + fileName);
    }

    public static File getExternalStoragePath(String fileName) {
        String defaultPath = FileUtils.APP_STORAGE_FOLDER;
        File file = new File(defaultPath);
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IllegalStateException(file.getAbsolutePath() +
                        " already exists and is not a directory");
            }
        } else {
            if (!file.mkdirs()) {
                L.e("createDir", "Unable to create directory: \" +\n" + file.getAbsolutePath());
        /*        throw new IllegalStateException("Unable to create directory: " +
                        file.getAbsolutePath());*/
            }
        }
        return new File(file.getAbsolutePath() + File.separator + fileName);
    }

    public static File getCacheStoragePath(String fileName) {
        String defaultPath = FileUtils.APP_CACHE_PATH;
        File file = new File(defaultPath);
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IllegalStateException(file.getAbsolutePath() +
                        " already exists and is not a directory");
            }
        } else {
            if (!file.mkdirs()) {
                L.e("createDir", "Unable to create directory: \" +\n" + file.getAbsolutePath());
        /*        throw new IllegalStateException("Unable to create directory: " +
                        file.getAbsolutePath());*/
            }
        }
        return new File(file.getAbsolutePath() + File.separator + fileName);
    }

    public static File getDestinationInExternalFilesDir(Context context, String dirType,
                                                        String fileName) {
        final File file = context.getExternalFilesDir(dirType);
        if (file == null) {
            throw new IllegalStateException("Failed to get external storage files directory");
        } else if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IllegalStateException(file.getAbsolutePath() +
                        " already exists and is not a directory");
            }
        } else {
            if (!file.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: " +
                        file.getAbsolutePath());
            }
        }
        //setDestinationFromBase(file, subPath);
        //return this;
        File destFile = new File(file.getAbsolutePath() + File.separator + fileName);
        return destFile;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getFileDownloadPath() {
        String defaultPath = Environment.DIRECTORY_DOWNLOADS + File.separator + FileUtils.DOWNLOAD_FOLDER;
        return defaultPath;
    }


    public static boolean writeFile(File file, byte[] bytes) {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        FileOutputStream out = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            out = new FileOutputStream(file);

            byte[] buffer = new byte[1024 * 8];
            int length = -1;

            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (Exception e2) {
                }
        }

        return true;
    }

    public static File copyFile(File sourceFile, File targetFile) throws Exception {
        FileInputStream in = new FileInputStream(sourceFile);
        byte[] buffer = new byte[128 * 1024];
        int len = -1;
        FileOutputStream out = new FileOutputStream(targetFile);
        while ((len = in.read(buffer)) != -1)
            out.write(buffer, 0, len);
        out.flush();
        out.close();
        in.close();
        return targetFile;
    }


}
