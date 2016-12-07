/*
 * Created by WadeZhang on 16-11-27 下午2:09.
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-11-27 下午2:09
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
 * 为每个纪念日设置通知栏弹出提醒
 * Created by Administrator on 2016/11/27 0027.
 */
public class Alarm {

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

    public void setAlarm( ){//设定闹钟提醒
        Bundle bundle = new Bundle();
        bundle.putString("title", mTitle);
        bundle.putString("date", mMonth+"-"+mDay);
        Intent intent = new Intent(mContext, NotificationActivity.class);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay, HOUR, MINUTE);
        calendar.add(Calendar.DAY_OF_MONTH, 0-DAYS_BEFOREHAND);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);
        if(mAgain == 0) alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            else alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), YEAR, pendingIntent);
    }
}
