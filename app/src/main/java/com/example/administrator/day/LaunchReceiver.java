/*
 * Created by WadeZhang on 16-12-20 下午2:49
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-20 下午2:49
 */

package com.example.administrator.day;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhangxix on 2016/12/20.
 */

public class LaunchReceiver extends BroadcastReceiver {

    public static final String ALARM_XML_FILE_NAME = "alarm"; //保存闹钟提示信息（通知时间和提前几天）的xml文件名称
    public static final String ALARM_HOUR = "alarmHour";
    public static final String ALARM_MINUTE = "alarmMinute"; //通知时间
    public static final String SHUT_DOWN_TIME = "shutDownTime"; //设备关机的时间

    @Override
    public void onReceive(Context context, Intent intent){
        Calendar mLaunchCalendar = Calendar.getInstance();
        Calendar mShutdownCalendar = Calendar.getInstance();
        SharedPreferences mSharedPreferences = context.getSharedPreferences(ALARM_XML_FILE_NAME, MODE_PRIVATE);
        int mHour = mSharedPreferences.getInt(ALARM_HOUR, 9);
        int mMinute = mSharedPreferences.getInt(ALARM_MINUTE, 0);
        if( HasTheDayCome.hasTheTimeCome(mHour, mMinute, mShutdownCalendar) && HasTheDayCome.hasTheTimeCome(mHour, mMinute, mLaunchCalendar) )
            Alarm.setAlarm(MyApplication.getContext(), 1);
        else Alarm.setAlarm(MyApplication.getContext(), 0);
        Log.d("LaunchReceiver", "Launch");
    }
}
