package com.example.administrator.day;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 处理新增纪念日的类
 * Created by Administrator on 2016/5/28 0028.
 */
public class AddActivity extends Activity {

    public String items[]={"纪念日","工作","生活"};//事件分类的3个选项
    public String againItems[]={"不重复","每年重复"};//重复的2个选项
    public String titleStr,dateStr,describeStr,kindStr,againStr;
    public String nowDate;

    public MyDatabaseHelper dbHelper;
    public Cursor cursor;

    public ActionBar actionBar;

    public ImageButton back,ok;
    public TextView kind,date,again;
    public EditText title,describe;

    public Intent intent;

    public Calendar c;
    public GetDayofWeek get;

    public SharedPreferences preferences;//保存“分类”单选对话框所选的项
    public SharedPreferences.Editor editor;

    public int leftDay;
    public int num=2,itemId;//“分类”单选对话框所选的项，初始值为2，即第三项

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        actionBar=getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);//使用自定义的添加界面的actionbar
        kind=(TextView)findViewById(R.id.text_kind);
        preferences=getSharedPreferences("which", MODE_PRIVATE);//获取SharedPreferences实例
        editor=preferences.edit();//调用edit()方法获取Editor对象
        editor.putInt("which", 2);//先往key为“which”中存入默认值2，即默认选中第三项
        editor.putInt("again", 0);//往key为"again"中存入默认值0，即默认选中第一项
        editor.commit();//提交

        kind.setOnClickListener(new View.OnClickListener() {//选择“分类”，添加点击事件
            @Override
            public void onClick(View v) {
                num = preferences.getInt("which", 2);//每次点击都从SharedPreferences中提取which的值
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddActivity.this)//单选对话框
                        .setTitle("分类")
                        .setSingleChoiceItems(items, num, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                kind.setText(items[which]);//选中一项后显示到textview中
                                editor.putInt("which", which);//将选中的新的一项保存在“which”中
                                editor.commit();//提交修改
                                dialog.dismiss();//没有设置取消和确定按钮，选中一项后直接令对话框消失
                            }
                        });
                mBuilder.create().show();
            }
        });

        again=(TextView)findViewById(R.id.text_again);
        again.setOnClickListener(new View.OnClickListener() {//选择“重复”，添加点击事件
            @Override
            public void onClick(View v) {
                num = preferences.getInt("again", 0);//每次点击都从SharedPreferences中提取again的值
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddActivity.this)//单选对话框
                        .setTitle("重复")
                        .setSingleChoiceItems(againItems, num, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                again.setText(againItems[which]);//选中一项后显示到textview中
                                editor.putInt("again", which);//将选中的新的一项保存在“again”中
                                editor.commit();//提交修改
                                dialog.dismiss();//没有设置取消和确定按钮，选中一项后直接令对话框消失
                            }
                        });
                mBuilder.create().show();
            }
        });

        back=(ImageButton)findViewById(R.id.imagebtn_back);
        back.setOnClickListener(new View.OnClickListener() {//顶部左侧返回按钮的点击事件
            @Override
            public void onClick(View v) {
                intent=new Intent(AddActivity.this,MainActivity.class);//返回主界面
                startActivity(intent);
                finish();
            }
        });

        title=(EditText)findViewById(R.id.edittext_title);
        describe=(EditText)findViewById(R.id.edittext_describe);
        date=(TextView)findViewById(R.id.text_date);//“日期”选择
        //date.setInputType(InputType.TYPE_NULL);
        c=Calendar.getInstance();
        get=new GetDayofWeek();
        nowDate = new String(c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));

        date.setText(nowDate+" 周"+get.getDayOfWeek(nowDate));//在文本框中显示初始日期
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog mDatePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {//创建日期选择对话框
                        //date.setText("");
                        String mChosenDate=new String(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);//选中的日期
                        date.setText(mChosenDate+" 周"+get.getDayOfWeek(mChosenDate));//在文本框中显示选定的日期
                    }
                },c.get(Calendar.YEAR)//设置日期选择器的初始日期
                 ,c.get(Calendar.MONTH)
                 ,c.get(Calendar.DAY_OF_MONTH));
                mDatePickerDialog.show();
            }
        });

        ok=(ImageButton)findViewById(R.id.imagebtn_ok);//顶部右侧完成按钮的点击事件
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = title.getText().toString();
                if (titleStr.equals("")) {
                    Toast mToast = Toast.makeText(AddActivity.this, "标题不能为空", Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    dateStr = date.getText().toString();//分别获取事件的各种信息
                    describeStr = describe.getText().toString();
                    kindStr = kind.getText().toString();
                    num = preferences.getInt("again", 0);
                    againStr = Integer.toString(num);
                    dbHelper = new MyDatabaseHelper(AddActivity.this, "myall.db3", 1);
                    cursor = dbHelper.getReadableDatabase().rawQuery("select * from whole", null);
                    cursor.moveToLast();
                    itemId = cursor.getInt(0) + 1;
                    new Thread() {
                        DayLeft mDayleft;

                        @Override
                        public void run() {
                            mDayleft = new DayLeft();
                            leftDay = mDayleft.cal(dateStr, num);
                        }
                    }.start();
                    dbHelper.getWritableDatabase().execSQL("insert into " +//将新添加的事件插入数据库
                                    "whole values(?,?,?,?,?,?,?)",
                            new String[]{Integer.toString(itemId), titleStr, dateStr, describeStr, kindStr, Integer.toString(leftDay), againStr});
                    cursor.close();
                    intent = new Intent(AddActivity.this, MainActivity.class);//返回主界面
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
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
/*
    public String getDayOfWeek(String date){//计算星期几
        Week=new String("");
        try {

            c.setTime(format.parse(date));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                Week += "日";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }
        return Week;
    }
*/
}
