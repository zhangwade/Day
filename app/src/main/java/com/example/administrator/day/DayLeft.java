package com.example.administrator.day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算倒数还有多少天的类
 * Created by Administrator on 2016/7/15 0015.
 */
public class DayLeft {

    public int cal(String date,int again){

        long mNow = new Date().getTime();
        long mAim=0;
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String mNowTime=mSimpleDateFormat.format(new Date());

        try {
            mAim = mSimpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if( mNowTime.equals(date) )//同年月日
            return 0;
        if(mAim>mNow)//未到日子
            return (int) ((mAim-mNow)/(1000*60*60*24))+1;
        else{//已经过了日子
            if(again==0)//不重复
                return (int) ((mNow-mAim)/(1000*60*60*24));
            else {//重复
                String mDate1;
                String[] mNowStr=mNowTime.split("-");
                String[] mAimStr=date.split("-");
                if(mNowStr[1].equals(mAimStr[1])&&mNowStr[2].equals(mAimStr[2]))//同月日
                    return 0;
                if( (mNowStr[1].equals(mAimStr[1])&&Integer.parseInt(mNowStr[2])<Integer.parseInt(mAimStr[2]))
                        ||(Integer.parseInt(mNowStr[1])<Integer.parseInt(mAimStr[1])) ){//月日在现在的月日之后
                    mDate1=mNowStr[0]+"-"+mAimStr[1]+"-"+mAimStr[2];
                }
                else{//月日在现在的月日之前
                    mDate1=(Integer.parseInt(mNowStr[0])+1)+"-"+mAimStr[1]+"-"+mAimStr[2];
                }
                try {
                    mAim = mSimpleDateFormat.parse(mDate1).getTime();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return (int) ((mAim-mNow)/(1000*60*60*24))+1;
            }
        }
    }
}
