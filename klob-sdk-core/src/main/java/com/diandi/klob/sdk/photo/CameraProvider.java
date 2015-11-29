package com.diandi.klob.sdk.photo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.util.CacheUtils;
import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;
import com.diandi.klob.sdk.util.photo.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class CameraProvider {
    private String TAG = getClass().getSimpleName();
    public static String FILE_PREFIX = "icon";
    public static int REQUESTCODE_CAMERA = 1;
    public static int REQUESTCODE_LOCATION = 2;
    public static int REQUESTCODE_CROP = 3;


    private CameraCallBack mCallBack;
    private Activity mContext;
    private String mCacheDirectory = "";

    private String dateTime;
    private int degree = 0;

    public CameraProvider(Activity context, CameraCallBack callBack) {
        mContext = context;
        mCallBack = callBack;
        mCacheDirectory = CacheUtils.getCacheDirectory(mContext, true, FILE_PREFIX) + "";
    }

    public void startCamera() {
        Date date = new Date(System.currentTimeMillis());
        dateTime = date.getTime() + "";
        File f = new File(CacheUtils.getCacheDirectory(mContext, true, FILE_PREFIX) + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        L.v("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mContext.startActivityForResult(camera, 1);
    }

    public void startZoom() {
        String path = getCameraResultPath();
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            Uri uri = Uri.fromFile(file);
            startZoom(uri);
        }
    }

    public void startZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        mContext.startActivityForResult(intent, 3);
    }

    public String getCameraResultPath() {
        String path = mCacheDirectory + dateTime;
        return path;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTCODE_CAMERA) {
                if (mCallBack != null) {
                    degree = CameraPhotoUtil.readPictureDegree(getCameraResultPath());
                    if (degree != 0) {
                        SimpleExecutor.getInstance().execute(new RotateImageTask());
                    } else {
                        mCallBack.cameraFinish(getCameraResultPath());
                    }

                }


            } else if (requestCode == REQUESTCODE_CROP) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        String iconUrl = CameraProvider.saveToSdCard(mContext, bitmap);
                        if (mCallBack != null) {
                            mCallBack.cropFinish(bitmap, iconUrl);
                        }

                    }
                }
            }
        }
    }

    public static String saveToSdCard(Context context, Bitmap bitmap) {
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        File file = new File(CacheUtils.getCacheDirectory(context, true, "icon") +dateTime+ ".png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
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

    public interface CameraCallBack {
        void cameraFinish(String filePath);

        void cropFinish(Bitmap bitmap, String cropPath);
    }

    private class RotateImageTask extends SimpleTask {
        @Override
        public void doInBackground() {
            try {
                Bitmap bitmap = BitmapDecoder.decodeSampledBitmapFromFile(getCameraResultPath(), ScreenUtils.getScreenHeight(mContext), ScreenUtils.getScreenHeight(mContext));
                bitmap = CameraPhotoUtil.rotateBitmap(bitmap, degree);

                ByteArrayOutputStream outArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outArray);

                FileUtils.writeFile(new File(getCameraResultPath()), outArray.toByteArray());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish(boolean canceled) {
            if (mCallBack != null) {
                mCallBack.cameraFinish(getCameraResultPath());
            }

        }
    }


}
