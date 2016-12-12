package com.example.administrator.day;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class CheckActivity extends BaseActivity {

    public ActionBar actionBar;

    public TextView actionBarTitle;
    public TextView t1,t2,t3,t4;
    public ImageButton back,next;

    public int itemId;
    public int leftDay,again;

    public long aim=0,now;

    public Bundle bundle;
    public Intent intent;

    public MyDatabaseHelper dbHelper;
    public Cursor cursor;

    public String aimDate,title;

    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        actionBar=getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);//使用自定义的添加界面的actionbar
        actionBarTitle=(TextView)findViewById(R.id.text_title);
        actionBarTitle.setText("查看");
        intent=getIntent();
        bundle=intent.getExtras();
        itemId=bundle.getInt("itemid");
        t1=(TextView)findViewById(R.id.text_remind);
        t2=(TextView)findViewById(R.id.text_daytitle);
        t3=(TextView)findViewById(R.id.text_dayleft);
        t4=(TextView)findViewById(R.id.text_date);
        dbHelper=new MyDatabaseHelper(CheckActivity.this,"myall.db3",1);
        cursor=dbHelper.getReadableDatabase().rawQuery("select * from whole where _id=?", new String[]{Integer.toString(itemId)});
        cursor.moveToFirst();
        aimDate=cursor.getString(2);
        t4.setText(aimDate);
        aimDate.substring(0,10);

        try {
            aim = sdf.parse(aimDate).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        now=new Date().getTime();
        leftDay=cursor.getInt(5);
        t3.setText(Integer.toString(leftDay));
        title=cursor.getString(1);
        again=cursor.getInt(6);
        if(leftDay==0||again==1||aim>now)
            t2.setText(title);
        else if(again==0&&aim<now){
            t1.setText(title);
            t2.setText("已经过去");
        }

        back=(ImageButton)findViewById(R.id.imagebtn_back);
        back.setOnClickListener(new View.OnClickListener() {//顶部左侧返回按钮的点击事件
            @Override
            public void onClick(View v) {
                intent=new Intent(CheckActivity.this,MainActivity.class);//返回主界面
                startActivity(intent);
            }
        });

        next=(ImageButton)findViewById(R.id.imagebtn_ok);
        next.setOnClickListener(new View.OnClickListener() {//顶部左侧返回按钮的点击事件
            @Override
            public void onClick(View v) {
                intent=new Intent(CheckActivity.this,EditActivity.class);//转到编辑界面
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(cursor!=null)
            cursor.close();
        if(dbHelper!=null)
            dbHelper.close();
    }
}
