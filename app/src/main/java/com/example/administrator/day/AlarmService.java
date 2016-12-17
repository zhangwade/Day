/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-8 下午5:24
 */

package com.example.administrator.day;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by zhangxix on 2016/12/8.
 *
 * 检索数据库中的纪念日是否到了提醒的日子
 */

public class AlarmService extends IntentService {

    MyDatabaseHelper mMyDatebaseHelper;
    Cursor mCursor;

    public AlarmService(){
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent mIntent){
        String mSetDate;
        Calendar mCalendar = Calendar.getInstance();;
        int mDaysAhead;
        int mAgain;
        int mID;
        String mTitle;
        mMyDatebaseHelper = new MyDatabaseHelper(this, "myall.db3", 1);
        mCursor = mMyDatebaseHelper.getReadableDatabase().rawQuery("select * from whole", null);
        if( mCursor.moveToFirst() ){
            do{
                mSetDate = mCursor.getString(2);
                mDaysAhead = 0; //TODO 从SharedPreferences中读取 提前提醒的天数
                mAgain = mCursor.getInt(6);
                if( HasTheDayCome.hasTheDayCome(mSetDate, mCalendar, mDaysAhead, mAgain) ){
                    mID = mCursor.getInt(0);
                    mTitle = mCursor.getString(1);
                    NotificationActivity.actionStart(this, mID, mTitle, mSetDate);
                }
            }while( mCursor.moveToNext() );
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if( mCursor!=null )
            mCursor.close();
        if( mMyDatebaseHelper!=null )
            mMyDatebaseHelper.close();
    }
}
