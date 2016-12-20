/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-13 下午5:37
 */

package com.example.administrator.day;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 通知栏弹出提醒
 * Created by zhangxix on 2016/12/1.
 */

public class NotificationActivity extends BaseActivity{

    NotificationManager mNotificationManager;

    public static void actionStart(Context mContext, int mID, String mTitle, String mDate){//说明此Activity所需传入的信息
        Intent mIntent = new Intent(mContext, NotificationActivity.class);
        mIntent.putExtra("id", mID);
        mIntent.putExtra("title", mTitle);
        mIntent.putExtra("date", mDate);
        mContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent mIntentIn = getIntent();
        int mID = mIntentIn.getIntExtra("id",-1);
        String mTitle = mIntentIn.getStringExtra("title");
        String mDate = mIntentIn.getStringExtra("date");
        Calendar mNowCalendar = Calendar.getInstance();
        Calendar mSetCalendar = (Calendar) mNowCalendar.clone();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mSetCalendar.setTime(mSimpleDateFormat.parse(mDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mSetCalendar.set(Calendar.YEAR, mNowCalendar.get(Calendar.YEAR));
        if( mSetCalendar.before(mNowCalendar) )
            mDate = String.valueOf(mNowCalendar.get(Calendar.YEAR)+1) + mDate.substring(4, mDate.length());
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mID, mIntent, 0);
        Notification mNotification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("倒数日的新消息")
                .setSmallIcon(R.drawable.icon_tick)//TODO 后期要记得改图标
                .setContentTitle(mTitle)
                .setContentText(mDate)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(mPendingIntent)
                .build();
        mNotificationManager.notify(mID, mNotification);
    }
}
