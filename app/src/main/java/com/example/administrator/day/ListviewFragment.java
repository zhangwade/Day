/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-11-19 下午4:23
 */

package com.example.administrator.day;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * 加载初始界面的类
 * Created by Administrator on 2016/5/27 0027.
 */
public class ListviewFragment extends Fragment {

    //public static String ARG_SECTION_NUMBER ="section_number";
    public MyDatabaseHelper dbHelper;
    public Cursor cursor;

    public Handler handler;
    public Intent intent;
    public Bundle bundle;
    public Bundle args;

    public SimpleCursorAdapter adapter;
    public ListView listView;

    public Thread t;

    public int itemId;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){
        System.out.println("Lv-oncreateview");
        listView=new ListView(getActivity());
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        args=getArguments();//获得传送来的Tab序号
        //ListviewThread listviewThread=new ListviewThread(args.getInt(ARG_SECTION_NUMBER));
        //listviewThread.start();
        dbHelper=new MyDatabaseHelper(getActivity(),"myall.db3",1);

        t=new Thread(new Runnable() {
            @Override
            public void run() {
                int i,itemid;
                Cursor mCursor;
                mCursor = dbHelper.getReadableDatabase().rawQuery("select * from whole", null);
                String mDate;
                int mAgain;
                int mLeftday;
                for(i=0,mCursor.moveToFirst();i<mCursor.getCount();i++,mCursor.moveToNext()){
                    mDate=mCursor.getString(2).substring(0,10);//date存储在每一行的第三列
                    mAgain=mCursor.getInt(6);
                    itemid=mCursor.getInt(0);
                    mLeftday=DayLeft.cal(mDate, mAgain);
                    dbHelper.getWritableDatabase().execSQL("update whole set dayleft=? where _id=?",
                            new String[]{Integer.toString(mLeftday),Integer.toString(itemid)});
                }

            }
        });
        t.start();

        switch (ListviewFragment.this.args.getInt("section_number")) {//根据Tab序号执行相应的数据库查询，以显示相关的事件
            case 1://全部
                System.out.println("Lv-sectionnumber=1");
                cursor = dbHelper.getReadableDatabase().rawQuery("select * from whole", null);
                System.out.println(cursor);
                break;
            case 2://纪念日
                System.out.println("Lv-sectionnumber=2");
                cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"纪念日"});
                System.out.println(cursor);
                break;
            case 3://工作
                System.out.println("Lv-sectionnumber=3");
                cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"工作"});
                System.out.println(cursor);
                break;
            case 4://生活
                System.out.println("Lv-sectionnumber=4");
                cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"生活"});
                System.out.println(cursor);
                break;
        }
        adapter=new SimpleCursorAdapter(getActivity(),R.layout.item,
                cursor,new String[]{"title","date","dayleft"},
                new int[]{R.id.text_title,R.id.text_date,R.id.text_dayleft},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                itemId = cursor.getInt(0);
                intent = new Intent(getActivity(), CheckActivity.class);
                bundle = new Bundle();
                bundle.putInt("itemid", itemId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        System.out.println(listView);
        return listView;
    }
    /*
    public class ListviewThread extends Thread{
        public int tabnum=0;
        ListviewThread(int tabnum){
            this.tabnum=tabnum;
        }
        public void run(){
            dbHelper=new MyDatabaseHelper(getActivity(),"myall.db3",1);
            switch (tabnum) {
                case 1:
                    cursor = dbHelper.getReadableDatabase().rawQuery("select * from whole", null);//打开软件即从数据库all表中查询数据，加载listview
                    break;
                case 2:
                    cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"anniversary"});
                    break;
                case 3:
                    cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"work"});
                    break;
                case 4:
                    cursor = dbHelper.getReadableDatabase(). rawQuery("select * from whole where kind= ?", new String[]{"life"});
                    break;
            }
        }
    }
*/
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(cursor!=null)
            cursor.close();
        if(dbHelper!=null)
            dbHelper.close();
        System.out.println("Lv-ondestroy");
    }
/*
    public class MyDatabaseHelper extends SQLiteOpenHelper {//数据库操作的要实现的类
        String CREATE_TABLE_SQL="create table whole(_id integer primary key " +//建表语句
                "autoincrement,title,date,describe,kind,dayleft,again)";
        public MyDatabaseHelper(Context context,String name,int version){
            super(context,name,null,version);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_SQL);//执行建表操作
            db.execSQL("insert into whole values(null,?,?,?,?,?,?)",
                    new String[]{"新年","2017-01-01 周一","无","life","233","1"});
            db.execSQL("insert into whole values(null,?,?,?,?,?,?)",
                    new String[]{"儿童节","2017-06-01 周一","无","work","233","1"});
            db.execSQL("insert into whole values(null,?,?,?,?,?,?)",
                    new String[]{"结婚纪念日","2017-02-05 周一","无","anniversary","233","1"});
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
            System.out.println("---onUpdate Called---"+oldversion+"--->"+newversion);
        }
    }
*/
}
