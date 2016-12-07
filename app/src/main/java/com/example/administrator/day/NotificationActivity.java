package com.example.administrator.day;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

/**
 * 通知栏弹出提醒
 * Created by zhangxix on 2016/12/1.
 */

public class NotificationActivity extends Activity{

    NotificationManager mNotificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Intent mIntentIn = getIntent();
        int mID = mIntentIn.getIntExtra("id",-1);
        String mTitle = mIntentIn.getStringExtra("title");
        String mDate = mIntentIn.getStringExtra("date");
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mID, mIntent, 0);
        Notification mNotification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("倒数日的新消息")
                .setSmallIcon(R.drawable.icon_tick)//后期要记得改图标
                .setContentTitle(mTitle)
                .setContentText(mDate)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(mPendingIntent)
                .build();
        mNotificationManager.notify(mID, mNotification);
    }
}
