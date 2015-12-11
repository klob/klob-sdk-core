package com.diandi.klob.sdk.util;


import android.text.TextUtils;
import android.util.Log;


import com.diandi.klob.sdk.common.Global;
import com.diandi.klob.sdk.core.BuildConfig;
import com.diandi.klob.sdk.processor.Processor;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class L {
    private static final String TAG = "tag";
    private static boolean isDebug = BuildConfig.DEBUG;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    static {
        Logger.init(" kloblog").hideThreadInfo();
    }

    public static boolean isLoggable() {
        return isDebug;
    }

    // 下面四个是默认tag的函数
    public static void setLoggable(boolean debug) {
        isDebug = debug;
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg + "     " + Thread.currentThread().getStackTrace()[3].getMethodName());
    }

    public static void d(String msg) {
        if (isDebug) {
            Logger.d(TAG, msg + "   \n" + Thread.currentThread().getStackTrace()[3].getMethodName());
        }
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String tag) {
        if (isDebug) {
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName());
        }
    }

    public static void elog(String tag, int count) {
        if (isDebug) {
            Logger.init("klob").setMethodCount(count).hideThreadInfo();
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName());
            Logger.init("klob").setMethodCount(3).hideThreadInfo();
        }
    }


    // 下面是传入自定义tag的函数

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg);
    }

    public static void d(String tag, List msg) {
        if (isDebug) {

            Logger.d(tag, msg == null ? " the obj is null" : "  size  " + msg.size() + "     " + Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg.toString());
        }
    }

    public static void dReflect(String tag, Object obj) {
        if (obj == null) {
            Logger.d(TAG, "the obj   is null");
        } else if (isDebug) {
            String info = "";
            Field[] fields = obj.getClass().getDeclaredFields();
            //  String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
            // String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character", "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    info = info + field.getName() + "  " + field.get(obj) + "\n";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Logger.d(tag, info);
        }
    }

    public static void dInvoke(String tag, Object obj, String methodName) {
        if (obj == null || TextUtils.isEmpty(methodName)) {
            return;
        }
        try {
            Class clazz = obj.getClass();
            Method m1 = clazz.getDeclaredMethod(methodName);
            m1.setAccessible(true);
            Object msg = m1.invoke(obj);
            L.d(tag, msg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void d(String tag, String msg) {
        if (isDebug)
            Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg);
    }

    public static void d(Object tag, String... msgs) {
        if (isDebug) {
            String value = null;
            for (String msg : msgs) {
                value += msg + "   ";
            }
            Logger.d(tag.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + value);
        }
    }

    public static void d(String tag, String... msgs) {
        if (isDebug) {
            String value = "";
            for (String msg : msgs) {
                value += msg + "   ";
            }
            Logger.d(tag, "    " + value);
        }
    }

    public static void d(String tag, Object object) {
        if (isDebug) {
            if (object != null)
                Logger.d(tag, "    " + object.getClass().getSimpleName() + "   " + object + "   ");
            else {
                Logger.d(tag, "   null " + "   " + "   ");
            }
        }
    }

    public static void d(String tag, double num) {
        if (isDebug)
            Log.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + "   " + num + "   ");
    }

    public static void d(String tag, String msg, Throwable e) {
        if (isDebug)
            Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "     " + e.toString());
    }

    public static void d(String tag, int msg, String e) {
        if (isDebug)
            Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "   " + e);
    }

    public static void d(String tag, int msg, Object... objects) {
        String value = "";
        if (isDebug) {
            for (Object obj : objects) {
                value += obj != null ? obj.toString() + " " : "";
            }
            Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "           " + value);
        }
    }

    public static void d(String tag, String msg, String e) {
        if (isDebug)
            Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + "    " + msg + "     " + e + "    ");
    }

    public static void d(String tag, Exception exception) {
        if (isDebug)
            if (exception.getCause() != null)
                Logger.d(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + exception.getCause().getMessage() + "   " + exception.getMessage() + "");
    }

    public static void e(String tag, List msg) {
        if (isDebug) {
            Logger.d(tag, msg == null ? " the obj is null" : "  size  " + msg.size() + "     " + Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg.toString());
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg);
    }

    public static void e(Object tag, String... msgs) {
        if (isDebug) {
            String value = null;
            for (String msg : msgs) {
                value += msg + "   ";
            }
            Logger.e(tag.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + value);
        }
    }

    public static void e(String tag, String... msgs) {
        if (isDebug) {
            String value = null;
            for (String msg : msgs) {
                value += msg + "   ";
            }
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + value);
        }
    }

    public static void e(String tag, Object object) {
        if (isDebug)
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + object.getClass().getSimpleName() + "   " + object + "   ");

    }

    public static void e(String tag, double num) {
        if (isDebug)
            Log.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + "   " + num + "   ");
    }

    public static void e(String tag, String msg, Throwable e) {
        if (isDebug)
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "     " + e.toString());
    }

    public static void e(String tag, int msg, String e) {
        if (isDebug)
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "   " + e);
    }

    public static void e(String tag, int msg, Object... objects) {
        String value = "";
        if (isDebug) {
            for (Object obj : objects) {
                value += obj != null ? obj.toString() + " " : "";
            }
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + msg + "           " + value);
        }
    }

    public static void e(String tag, String msg, String e) {
        if (isDebug)
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + "    " + msg + "     " + e + "    ");
    }

    public static void e(String tag, Exception exception) {
        if (isDebug)
            if (exception.getCause() != null)
                Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName() + "    " + exception.getCause().getMessage() + "   " + exception.getMessage() + "");
    }

    public static void json(JSONObject jsonObject) {
        if (isDebug) {
            L.d(jsonObject.toString());
        }
    }

    public static void json(String TAG, JSONObject jsonObject) {
        if (isDebug) {
            L.d(TAG, jsonObject.toString());
        }
    }

    public static void m(String tag) {
        if (isDebug) {
            Logger.e(tag, Thread.currentThread().getStackTrace()[3].getMethodName());
        }
    }

    public static void thread(String tag) {
        if (isDebug) {
            StackTraceElement[] stackTraceElements = Thread.currentThread()
                    .getStackTrace();
            Log.e(tag, ("The stackTraceElements length: "
                    + stackTraceElements.length));
            for (int i = 0; i < stackTraceElements.length; ++i) {
                Log.e(tag, "\n\n----  the " + i + " element  ----");
                Log.e(tag, "toString: " + stackTraceElements[i].toString());
                Log.e(tag, "ClassName: "
                        + stackTraceElements[i].getClassName());
                Log.e(tag, "FileName: "
                        + stackTraceElements[i].getFileName());
                Log.e(tag, "LineNumber: "
                        + stackTraceElements[i].getLineNumber());
                Log.e(tag, "MethodName: "
                        + stackTraceElements[i].getMethodName());
            }
        }
    }

    public static void errorLog(Exception e) {
        e.printStackTrace();
        errorToast(e);
        Logger.e("", "" + e);
    }

    public static void errorToast(final Exception e) {
        if (isLoggable()) {
            if (e != null && e.getCause() != null && e.getMessage() != null && e.getCause().getMessage() != null)
                Processor.post(new Runnable() {
                    @Override
                    public void run() {
                        Global.showMsg(e.getMessage() + e.getCause().getMessage());
                    }
                });
        }

    }

    public static void errorToast(final String e) {
        if (isLoggable()) {
            Processor.post(new Runnable() {
                @Override
                public void run() {
                    Global.showMsg(e);
                }
            });
        }

    }

}
