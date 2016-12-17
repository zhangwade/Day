/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-7 下午6:16
 */

package com.example.administrator.day;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static java.util.Calendar.YEAR;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
public class Alarm {
/*
    int mYear, mMonth, mDay, mAgain, mRequestCode;
    String mTitle;
    Context mContext;

    public static final int HOUR = 9;
    public static final int MINUTE = 0;//提醒的时间点
    public static final int DAYS_BEFOREHAND = 1;//提前几天提醒

    Alarm(Context context, int year, int month, int day, int again, int requestCode, String title){
        //again:是否需要周期性提醒，requestCode:利用SQLite每行数据的ID标识不同的PendingIntent
        this.mContext = context;
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mAgain = again;
        this.mRequestCode = requestCode;
        this.mTitle = title;
    }
*/
    public static void setAlarm(Context mContext){//设定闹钟提醒
        Intent mIntent = new Intent(mContext, AlarmService.class);
        PendingIntent mPendingIntent = PendingIntent.getService(mContext, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar mSetCalendar = Calendar.getInstance();
        int mHour = 0; //TODO
        int mMinute = 0; //TODO
        mSetCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mSetCalendar.set(Calendar.MINUTE, mMinute);
        mSetCalendar.set(Calendar.SECOND, 0);
        AlarmManager mAlarmManager = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mSetCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPendingIntent);
    }
}
