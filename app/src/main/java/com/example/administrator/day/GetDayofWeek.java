/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-13 下午5:37
 */

package com.example.administrator.day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class GetDayofWeek {

    public String getDayOfWeek(String date){//计算星期几

        String mWeek=new String("");;
        Calendar mCalendar=Calendar.getInstance();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            mCalendar.setTime(mSimpleDateFormat.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (mCalendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                mWeek += "日";
                break;
            case 2:
                mWeek += "一";
                break;
            case 3:
                mWeek += "二";
                break;
            case 4:
                mWeek += "三";
                break;
            case 5:
                mWeek += "四";
                break;
            case 6:
                mWeek += "五";
                break;
            case 7:
                mWeek += "六";
                break;
            default:
                break;
        }
        return mWeek;
    }
}
