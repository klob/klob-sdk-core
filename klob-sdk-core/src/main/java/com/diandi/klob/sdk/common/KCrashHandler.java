package com.diandi.klob.sdk.common;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.util.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-11-05  .
 * *********    Time : 16:43 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class KCrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    static final Object o = new Object();
    //CrashHandler实例
    //程序的Context对象
    protected Context mContext;
    PhoneInfo mPhoneInfo;
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    protected KCrashHandler() {
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //    MobclickAgent.reportError(mContext, ex);
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            //  android.os.Process.killProcess(android.os.Process.myPid());
            //  System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        L.e("UncaughtExceptionHandler", ex);

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {

                Looper.prepare();
                L.errorToast(ex.getMessage());
                Toast.makeText(mContext, "很抱歉,程序出现异常错误,请重启应用或清除应用缓存", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        mPhoneInfo = new PhoneInfo(mContext);
        //  mPhoneInfo.print();
        //保存日志文件
        saveCrashInfo2File(ex);
        return false;
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        final String causes = ex.toString();
        String info = "";
        int line = 0;
        final StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable Throwablecause = ex.getCause();
        while (Throwablecause != null) {
            Throwablecause.printStackTrace(printWriter);
            Throwablecause = Throwablecause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        final String summary = result;
        sb.append(result);
        for (Map.Entry<String, String> entry : mPhoneInfo.mPhotoInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            info += key + "=" + value + "\n";
            sb.append(key + "=" + value + "\n");
        }

        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = mContext.getFilesDir() + "/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                final String finalInfo = info;
                saveToServer(path + fileName, sb.toString(), finalInfo, causes, summary);
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    public void saveToServer(String path, String all, String info, String cause, String summary) {
    }

}