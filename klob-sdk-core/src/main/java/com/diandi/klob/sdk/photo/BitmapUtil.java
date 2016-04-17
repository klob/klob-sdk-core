package com.diandi.klob.sdk.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class BitmapUtil {
    private final static String TAG = "　Camera ";

    public static Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String filepath) {
        int degree = 0;
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            //  L.e(TAG, "orientation");
            //  L.e(TAG, exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1));
            //  L.e(TAG, exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1));
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        L.v(TAG, degree + "");

        return degree;
    }

    public static Bitmap rotateBitmap(String path, Bitmap bitmap) {
        int degree = BitmapUtil.readPictureDegree(path);
        if (degree == 0) {
            return bitmap;
        } else {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, bitmap.getWidth(), bitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return result;

        }

    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static Bitmap rotateBitmap(Bitmap source, int degrees) {
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, source.getWidth() / 2, source.getHeight() / 2);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        try {
            Logger.d(
                    "rotateBitmap",
                    String.format("rotate bitmap, source(%d,%d) result(%d,%d)", source.getWidth(), source.getHeight(), result.getWidth(),
                            result.getHeight()));
        } catch (Exception e) {
        }
        source.recycle();
        return result;
    }

    public static void getExifInfo(String filepath) {
        try {
            ExifInterface exifInterface = new ExifInterface(filepath);
            String FFNumber = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
            String FDateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            String FExposureTime = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
            String FFlash = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            String FFocalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            String FImageLength = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String FImageWidth = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String FISOSpeedRatings = exifInterface.getAttribute(ExifInterface.TAG_ISO);
            String FMake = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String FModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            String FOrientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            String FWhiteBalance = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            Log.i(TAG, "FFNumber:" + FFNumber);
            Log.i(TAG, "FDateTime:" + FDateTime);
            Log.i(TAG, "FExposureTime:" + FExposureTime);
            Log.i(TAG, "FFlash:" + FFlash);
            Log.i(TAG, "FFocalLength:" + FFocalLength);
            Log.i(TAG, "FImageLength:" + FImageLength);
            Log.i(TAG, "FImageWidth:" + FImageWidth);
            Log.i(TAG, "FISOSpeedRatings:" + FISOSpeedRatings);
            Log.i(TAG, "FMake:" + FMake);
            Log.i(TAG, "FModel:" + FModel);
            Log.i(TAG, "FOrientation:" + FOrientation);
            Log.i(TAG, "FWhiteBalance:" + FWhiteBalance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String saveToSdCard(Context context, Bitmap bitmap) {
        return saveToSdCard(context, bitmap, 100);
    }

    public static String saveToSdCard(Context context, Bitmap bitmap, int quality) {
        int finalQuality;
        if (quality < 0) {
            finalQuality = 1;
        } else if (quality > 100) {
            finalQuality = 100;
        } else {
            finalQuality = quality;
        }
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        File file = FileUtils.getExternalStoragePath("icon" + dateTime + ".png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, finalQuality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        L.i("uri ", file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}