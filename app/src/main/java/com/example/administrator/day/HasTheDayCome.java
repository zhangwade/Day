/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-13 下午5:16
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

    public static boolean hasTheDayCome(String setDate, Calendar nowCalendar, int daysAhead, int again){  //纪念日是否到了要提醒的日子
        String mSetDate = setDate;
        Calendar mNowCalendar = nowCalendar;
        Calendar mSetCalendar = Calendar.getInstance();
        int mDaysAhead = daysAhead; //提前几天通知?
        int mAgain = again; //是否每年重复
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mSetCalendar.setTime(mSimpleDateFormat.parse(mSetDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mSetCalendar.set(Calendar.HOUR_OF_DAY, 9);
        mSetCalendar.set(Calendar.MINUTE, 0);
        mSetCalendar.set(Calendar.SECOND, 0);
        mNowCalendar.set(Calendar.HOUR_OF_DAY, 9);
        mNowCalendar.set(Calendar.MINUTE, 0);
        mNowCalendar.set(Calendar.SECOND, 0); //将2个要比较的日期 的 时分秒 初始化为一致
        Calendar mTempCalendar = (Calendar) mNowCalendar.clone();
        mTempCalendar.add(Calendar.HOUR_OF_DAY, mDaysAhead); //辅助值，现在的日期加上提前通知的天数，用作与纪念日的比较
        if(mAgain == 0){ //不重复
            if( !mNowCalendar.after(mSetCalendar) && !mTempCalendar.before(mSetCalendar) ) return true;
        }else{ //每年重复
            if( !mNowCalendar.after(mSetCalendar) ){ //纪念日还没到
                if( !mTempCalendar.before(mSetCalendar) ) return true;
            }else{ //纪念日已过，但设置是每年重复的，所以还是要判断是否即将到来
                mSetCalendar.set(Calendar.YEAR, mNowCalendar.get(Calendar.YEAR));
                if( mNowCalendar.after(mSetCalendar) ) mSetCalendar.set(Calendar.YEAR, mNowCalendar.get(Calendar.YEAR)+1);
                if( !mTempCalendar.before(mSetCalendar) ) return true;
            }
        }
        return false;
    }

    public static boolean hasTheTimeCome(int setHour, int setMinute, Calendar nowCalendar){ //重新调整提醒时间后，判断是否已到达此时间
        int mSetHour = setHour;
        int mSetMinute = setMinute;
        Calendar mNowCalendar = nowCalendar;
        Calendar mSetCalendar = (Calendar) mNowCalendar.clone();
        mSetCalendar.set(Calendar.HOUR_OF_DAY, mSetHour);
        mSetCalendar.set(Calendar.MINUTE, mSetMinute);
        mSetCalendar.set(Calendar.SECOND, 0);
        if( mNowCalendar.before(mSetCalendar) ) return false;
        return true;
    }
}
