package com.diandi.klob.sdk.common;

import android.util.Log;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 20:27 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * Quantitative assessment for your adapter of your ListView;
 * <p>
 * Here is a sample .
 * <p>
 * public class YourAdapter extends BaseAdapter {
 * <p>
 * ListViewTool tool =new ListViewTool(true);
 *
 * @Override public int getCount() {
 * return 0;
 * }
 * @Override public Object getItem(int position) {
 * return null;
 * }
 * @Override public long getItemId(int position) {
 * return 0;
 * }
 * @Override public View getView(int position, View convertView, ViewGroup parent) {
 * tool.start();
 * <p>
 * do your work
 * **********
 * <p>
 * tool.getAverage(position);
 * return null;
 * }
 * }
 */


public class ListViewTool {
    //the total  time of getView() execution
    private static double total = 0;
    //the times of getView() execution
    private static int time = 0;
    private String TAG = getClass().getSimpleName();
    //getView() start
    private double start = 0;
    //getView() end
    private double end = 0;
    //the average  time of getView() execution
    private double average = 0;
    private boolean isDebug;

    public ListViewTool(boolean isDebug) {
        this.isDebug = isDebug;
        init();
    }

    public void init() {
        total = 0;
        start = 0;
        end = 0;
        time = 0;
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        end = System.currentTimeMillis();
        time++;
        total = total + end - start;
    }

    public void setTag(String extra) {
        TAG = TAG + extra;
    }

    public double getAverage(int position) {
        end();
        average = total / time;
        if (time % 100 == 0) {
            Log.d(TAG, "---------------------------------------------------------------------------------------------------------\n" +
                    "position:  " + position + "   total: " + total + "   time: " + time + "    average: " + average
                    + "\n------------------------------------------------------------------------------------------------------");

        }
        if (isDebug) {
            Log.d(TAG, "position:  " + position + "   total: " + total + "   time: " + time + "    average: " + average
            );
        }
        return average;
    }

}
