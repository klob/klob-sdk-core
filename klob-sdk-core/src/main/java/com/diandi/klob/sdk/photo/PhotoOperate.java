package com.diandi.klob.sdk.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;

import com.diandi.klob.sdk.core.R;
import com.diandi.klob.sdk.util.FormatUtil;
import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Image Compression
 */

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class PhotoOperate {
    private final static String TAG = "PhotoOperate";

    private Context mContext;

    public PhotoOperate(Context context) {
        this.mContext = context;
    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    private File getTempFile2(Context context) {
        File file = null;
        try {
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            file = File.createTempFile(fileName, ".jpg", context.getCacheDir());
        } catch (IOException e) {
        }
        return file;
    }

    private File getTempFile(Context context, String path) {
        File file = null;
        try {
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            file = File.createTempFile(path, ".jpg", context.getCacheDir());
        } catch (IOException e) {
        }
        return file;
    }

    public File scale3(Uri fileUri) throws Exception {
        String path = FileUtils.getPath(mContext, fileUri);
        File outputFile = new File(path);
        CameraPhotoUtil.getExifInfo(path);
        if (FormatUtil.isGif(path)) {
            return outputFile;
        }
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            Matrix matrix = new Matrix();
            matrix.setRotate(CameraPhotoUtil.readPictureDegree(path), bitmap.getWidth(), bitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            outputFile = getTempFile2(mContext);
            FileOutputStream fos = new FileOutputStream(outputFile);
            result.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (!result.isRecycled()) {
                result.recycle();
            }
        } else {
            File tempFile = outputFile;
            outputFile = getTempFile2(mContext);
            copyFileUsingFileChannels(tempFile, outputFile);
        }
        L.i("files", outputFile.getAbsolutePath());
        return outputFile;
    }


    public File scale2(Uri fileUri) throws Exception {
        String path = FileUtils.getPath(mContext, fileUri);
        File outputFile = new File(path);
        CameraPhotoUtil.getExifInfo(path);
        if (FormatUtil.isGif(path)) {
            return outputFile;

        }

        long fileSize = outputFile.length();
        final long fileMaxSize = 400 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            Matrix matrix = new Matrix();
            matrix.setRotate(CameraPhotoUtil.readPictureDegree(path), bitmap.getWidth(), bitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            outputFile = getTempFile2(mContext);
            FileOutputStream fos = new FileOutputStream(outputFile);
            result.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (!result.isRecycled()) {
                result.recycle();
            }

        } else {
            File tempFile = outputFile;
            outputFile = getTempFile2(mContext);
            copyFileUsingFileChannels(tempFile, outputFile);
        }
        L.i("files", outputFile.getAbsolutePath());
        return outputFile;
    }

    public File scale(Uri fileUri) throws Exception {
        L.e(TAG + 0, fileUri);
     /*   String path = null;
   *//*     try {
            path = FileUtils.getPath(mContext, fileUri);
        } catch (Exception e) {
            path=fileUri.getPath();
            e.printStackTrace();
        }*//*
        path=fileUri.toString().substring(7);
        L.e(TAG + 1, path);
        File outputFile = new File(path);*/

        String path = FileUtils.getPath(mContext, fileUri);
        File outputFile = new File(path);
        String name = outputFile.getName();
        L.d(TAG, path);

        if (FormatUtil.isGif(path)) {
            return outputFile;
        }
        long fileSize = outputFile.length();
        final long fileMaxSize = PhotoOption.max_photo_size;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + PhotoOption.scale);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            Matrix matrix = new Matrix();
            matrix.setRotate(CameraPhotoUtil.readPictureDegree(path), bitmap.getWidth(), bitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


            outputFile = getTempFile(mContext, name);
            FileOutputStream fos = new FileOutputStream(outputFile);
            result.compress(Bitmap.CompressFormat.JPEG, PhotoOption.max_photo_quality, fos);
            fos.close();
            Log.d("", "sss ok " + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (!result.isRecycled()) {
                result.recycle();
            }

        } else {
            File tempFile = outputFile;
            outputFile = getTempFile(mContext, name);
            copyFileUsingFileChannels(tempFile, outputFile);
        }
        L.i("files", outputFile.getAbsolutePath());
        return outputFile;
    }

    public File operate(Uri fileUri) throws Exception {
        File finalFile;

        if (PhotoOption.isCompress) {
            File compressFile = scale(fileUri);
            if (PhotoOption.isWaterMark) {
                finalFile = waterMark(compressFile);
            } else {
                finalFile = compressFile;
            }
        } else {
            if (PhotoOption.isWaterMark) {
                finalFile = waterMark(fileUri);
            } else {
                String path = FileUtils.getPath(mContext, fileUri);
                finalFile = new File(path);
            }
        }
        return finalFile;
    }

    public File waterMark(File file) throws Exception {
        return waterMark(Uri.fromFile(file));

    }

    public File waterMark(Uri fileUri) throws Exception {
        L.d(TAG, fileUri);
        String path = FileUtils.getPath(mContext, fileUri);
        File outputFile = new File(path);
        if (FormatUtil.isGifByFile(outputFile)) {
            return outputFile;
        }
        String name = outputFile.getName();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(outputFile.getAbsolutePath(), options);
        int height = options.outHeight;
        int width = options.outWidth;

        options.outHeight = (int) (height);
        options.outWidth = (int) (width);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(outputFile.getAbsolutePath(), options);
        Matrix matrix = new Matrix();
        matrix.setRotate(CameraPhotoUtil.readPictureDegree(path), bitmap.getWidth(), bitmap.getHeight());
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap waterMarkResult = WaterMarkUtil.watermarkBitmap(result, "");
        outputFile = getTempFile(mContext, name);
        FileOutputStream fos = new FileOutputStream(outputFile);
        waterMarkResult.compress(Bitmap.CompressFormat.JPEG, PhotoOption.max_photo_quality, fos);
        fos.close();
        if (!waterMarkResult.isRecycled()) {
            waterMarkResult.recycle();
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if (!result.isRecycled()) {
            result.recycle();
        }

        L.i("files", outputFile.getAbsolutePath());
        return outputFile;

    }

}
