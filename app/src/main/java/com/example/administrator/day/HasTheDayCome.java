/*
 * Created by WadeZhang on 16-12-12 下午4:20
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-12 下午4:20
 */

package com.example.administrator.day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangxix on 2016/12/12.
 *
 * 判断日期是否到达
 */

public class HasTheDayCome {

    String mSetDate = null;
    Calendar mSetCalendar, mNowCalendar;
    int mSetHour = 0, mSetMinute = 0;
    int mDaysAhead = 0; //提前几天通知?
    int mAgain = 0;

    public HasTheDayCome(String mSetDate, Calendar mNowCalendar, int mDaysAhead, int mAgain){
        this.mSetDate = mSetDate;
        this.mNowCalendar = mNowCalendar;
        this.mDaysAhead = mDaysAhead;
        this.mAgain = mAgain;
    }

    public HasTheDayCome(int mSetHour, int mSetMinute, Calendar mNowCalendar){
        this.mSetHour = mSetHour;
        this.mSetMinute = mSetMinute;
        this.mNowCalendar = mNowCalendar;
    }

    public boolean hasTheDayCome(){
        mSetCalendar = Calendar.getInstance();
        if(mSetDate != null){
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                mSetCalendar.setTime(mSimpleDateFormat.parse(mSetDate));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mSetCalendar.set(Calendar.HOUR_OF_DAY, 0);
            mSetCalendar.set(Calendar.MINUTE, 0);
            mSetCalendar.set(Calendar.SECOND, 0);
        }
        return false;
    }
}
