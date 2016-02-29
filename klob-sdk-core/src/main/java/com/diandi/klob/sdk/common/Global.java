package com.diandi.klob.sdk.common;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.diandi.klob.sdk.XApplication;
import com.diandi.klob.sdk.cache.ACache;
import com.diandi.klob.sdk.concurrent.SimpleTask;
import com.diandi.klob.sdk.processor.Processor;
import com.diandi.klob.sdk.processor.WorkHandler;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

public class Global {



    public static void copy(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);

    }

    public static void hideSoftInputView(Activity activity) {

        InputMethodManager manager = ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showMsg(final String msg) {
        SimpleTask.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(XApplication.getInstance(), msg, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void execute(WorkHandler handler) {
        Processor.execute(handler);
    }



    private static ACache mACache;

    public static ACache getCache() {
        if (mACache == null) {
            mACache = ACache.get(XApplication.getInstance());
        }
        return mACache;
    }
}
