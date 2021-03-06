package com.diandi.klob.sdk.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-10-22  .
 * *********    Time : 16:52 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class JavaLog {
    public static final String TAG_MSG = "__MSG_";
    public static final String TAG_KEY = "__KEY_";
    public static final String TAG_VALUE = "__VALUE_";
    public static final String TAG_TYPE = "__TYPE_";
    public static final String TAG_BMOB_TYPE = "__type";
    public static final String MSG_BSON = "String is BSONString.";
    public static final String MSG_BSON_EMPTY = "String is empty BSON.";
    public static final String MSG_BSON_ARRAY = "String is BSONArrayString.";
    public static final String MSG_ERROR_EMPTY = "String is empty.";
    public static final String MSG_ERROR_NOTBSON = "String is not BSONString.";
    public static final String MSG_ERROR_UNKNOW = "Unknow Error.";
    public static final String MSG_ERROR_NULL_KEY = "Null key.";
    public static final String CHAR_POINT = ".";
    public static final String CHAR_COMMA = ",";
    public static final String CHAR_QUOTES = "\"";
    public static final String CHAR_RISK = ":";
    public static final String CHAR_LEFT_BIG = "{";
    public static final String CHAR_RIGHT_BIG = "}";
    public static final String CHAR_LEFT_MID = "[";
    public static final String CHAR_RIGHT_MID = "]";
    public static final String CHAR_NULL = "";
    public static final int TYPE_UNKNOW = -1;
    public static final int TYPE_INTEGER = 0;
    public static final int TYPE_LONG = 1;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_DOUBLE = 3;
    public static final int TYPE_BOOLEAN = 4;
    public static final int TYPE_STRING = 5;
    public static final int TYPE_ARRAY = 6;
    public static final int TYPE_OBJECT = 7;
    public static final int TYPE_BSON = 8;
    public static final int TYPE_DATE = 9;
    public static final int TYPE_BSON_ARRAY = 10;

    public static void d(String msg) {
        Log(msg);
    }

    public static void d(Object obj) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        if (obj == null) {
            Log("Object is null.");
        } else {
            Log(className + "\n   " + obj.toString());
        }
    }

    public static void d(String tag, Object obj) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        if (obj == null) {
            Log("Object is null.");
        } else {
            Log(tag + "\n" + className + "\n   " + obj.toString());
        }
    }

    public static void d(String tag, Object... objects) {
        String value = "";
        for (Object obj : objects) {
            value += obj != null ? obj.toString() + " " : "";
        }
        Log(Thread.currentThread().getStackTrace()[3].getMethodName() + "           " + value);
    }

    public static Object getFieldValue(Object o, Class<?> clz, String fieldName) {
        Object fieldValue = null;
        JavaLog.d(o.getClass());
        try {
            Field field1 = clz.getDeclaredField(fieldName);
            field1.setAccessible(true);
            fieldValue = field1.get(o);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }

    public static Object getFieldValue(Object o, String fieldName) {
        Object fieldValue = null;
        Class<?> clz = o.getClass();
        d("reflect class name is " + o);
        try {
            Field field1 = clz.getDeclaredField(fieldName);
            field1.setAccessible(true);
            fieldValue = field1.get(o);
            return fieldValue;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        d("reflect field is null");
        return fieldValue;
    }

    public static void Log(String str) {
        System.out.println(CHAR_LEFT_MID + Long2Time(0) + CHAR_RIGHT_MID + "-Log: " + str);
    }

    public static void Warn(Object obj) {
        if (obj == null) {
            Warn("Object is null.");
        } else {
            Warn(obj.toString());
        }
    }

    public static void Warn(String str) {
        System.err.println(CHAR_LEFT_MID + Long2Time(0) + CHAR_RIGHT_MID + "-Warn: " + str);
    }

    public static String Long2Time(long time) {
        if (time == 0) {
            time = new Date().getTime();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String nowTime = formatter.format(time);
        return nowTime;
    }

    public static String Long2DateTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = formatter.format(time);
        return nowTime;
    }

    public static long DateTime2Long(String date) {
        long time = 0;
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            time = mSimpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
