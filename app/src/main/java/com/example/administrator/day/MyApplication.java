/*
 * Created by WadeZhang on 16-12-20 下午2:50
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-20 下午2:39
 */

package com.example.administrator.day;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangxix on 2016/12/20.
 */

public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate(){
        super.onCreate();
        sContext=this;
    }

    public static Context getContext(){
        return sContext;
    }
}
