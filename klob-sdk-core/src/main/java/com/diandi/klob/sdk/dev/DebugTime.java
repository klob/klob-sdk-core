package com.diandi.klob.sdk.dev;

import com.diandi.klob.sdk.util.L;

import java.util.ArrayList;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2016-01-27  .
 * *********    Time : 18:06 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class DebugTime {
    public final static String TAG = "DebugTime";
    public static ArrayList<Long> sStarts = new ArrayList<>();
    public static ArrayList<Long> sEnds = new ArrayList<>();
    public static ArrayList<Long> sAvarages = new ArrayList<>();
    static long sStartTime;
    static long sEndTime;

    public static void start() {
        start(0);
    }

    public static void start(int position) {
        sStarts.add(position, System.currentTimeMillis());
    }

    public static void end(String tag) {
        end(tag, 0);
    }

    public static void end(String tag, int position) {
        sEnds.add(position, System.currentTimeMillis());
        print(tag, position);

    }

    public static void clear() {
        sStartTime = 0;
        sEndTime = 0;
        sStarts = new ArrayList<>();
        sEnds = new ArrayList<>();
    }

    public static void print(String tag, int position) {
        L.d(TAG + tag + " time ", sEnds.get(position) - sStarts.get(position));

    }

}
