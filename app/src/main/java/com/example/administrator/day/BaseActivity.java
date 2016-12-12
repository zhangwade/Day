/*
 * Created by WadeZhang on 16-12-8 下午3:29
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-8 下午3:29
 */

package com.example.administrator.day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by zhangxix on 2016/12/8.
 *
 * Activity基类，用于提示正在运行的是哪一个Activity
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
    }
}
