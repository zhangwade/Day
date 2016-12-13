/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-11-14 上午11:23
 */

package com.example.administrator.day;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public String createTableSql="create table whole(_id integer primary key " +//建表语句
            "autoincrement,title,date,describe,kind,dayleft,again)";
    public DayLeft d;
    public int leftDay;

    public MyDatabaseHelper(Context context,String name,int version){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createTableSql);//执行建表操作
        d=new DayLeft();
        leftDay=d.cal("2016-03-03",0);
        db.execSQL("insert into whole values(1,?,?,?,?,?,?)",
                new String[]{"新年","2016-03-03 周一","无","生活",Integer.toString(leftDay),"1"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        System.out.println("---onUpdate Called---"+oldversion+"--->"+newversion);
    }
}
