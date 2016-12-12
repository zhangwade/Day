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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 修改纪念日的类
 * Created by Administrator on 2016/6/7 0007.
 */
public class EditActivity extends BaseActivity {

    public ActionBar actionBar;

    public TextView actionBarTitle,date,kind,again;
    public ImageButton ok,back;
    public EditText title,describe;
    public Button delete;

    public Intent intent;
    public Bundle bundle;

    public int itemId,num,leftDay;

    public MyDatabaseHelper dbHelper;
    public Cursor cursor;


    public String items[]={"纪念日","工作","生活"};//事件分类的3个选项
    public String againItems[]={"不重复","每年重复"};
    public String[] newStr;
    public String titleStr,dateStr,describeStr,kindStr,againStr;

    public GetDayofWeek get;
    public Calendar c;

    public SharedPreferences preferences;//保存“分类”单选对话框所选的项
    public SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        actionBar=getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);//使用自定义的添加界面的actionbar
        actionBarTitle=(TextView)findViewById(R.id.text_title);
        actionBarTitle.setText("编辑");
        intent=getIntent();
        bundle=intent.getExtras();
        itemId=bundle.getInt("itemid");
        dbHelper=new MyDatabaseHelper(EditActivity.this,"myall.db3",1);
        cursor=dbHelper.getReadableDatabase().rawQuery("select * from whole where _id=?", new String[]{Integer.toString(itemId)});
        cursor.moveToFirst();
        preferences=getSharedPreferences("which", MODE_PRIVATE);//获取SharedPreferences实例
        editor=preferences.edit();//调用edit()方法获取Editor对象
        kindStr=cursor.getString(4);
        if(kindStr.equals("纪念日"))num=0;
            else if(kindStr.equals("工作"))num=1;
                else num=2;
        editor.putInt("which", num);//先往key为“which”中存入默认值
        editor.putInt("again", cursor.getInt(6));//往key为"again"中存入默认值
        editor.commit();//提交
        title=(EditText)findViewById(R.id.edittext_title);
        title.setText(cursor.getString(1));
        date=(TextView)findViewById(R.id.text_date);
        dateStr=cursor.getString(2);
        date.setText(dateStr);
        c=Calendar.getInstance();
        get=new GetDayofWeek();
        newStr=dateStr.split("-");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog=new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {//创建日期选择对话框
                        //date.setText("");
                        String chosen_date = new String(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);//选中的日期
                        date.setText(chosen_date + " 周" + get.getDayOfWeek(chosen_date));//在文本框中显示选定的日期
                    }
                }, Integer.parseInt(newStr[0])//设置日期选择器的初始日期
                        , Integer.parseInt(newStr[1])-1
                        , Integer.parseInt(newStr[2].substring(0,2)));
                datePickerDialog.show();
            }
        });

        kind=(TextView)findViewById(R.id.text_kind);
        kind.setText(kindStr);
        kind.setOnClickListener(new View.OnClickListener() {//选择“分类”，添加点击事件
            @Override
            public void onClick(View v) {
                num = preferences.getInt("which", 2);//每次点击都从SharedPreferences中提取which的值
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditActivity.this)//单选对话框
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
        if(cursor.getInt(6)==0) again.setText(againItems[0]);
            else again.setText(againItems[1]);
        again.setOnClickListener(new View.OnClickListener() {//选择“重复”，添加点击事件
            @Override
            public void onClick(View v) {
                num = preferences.getInt("again", 0);//每次点击都从SharedPreferences中提取again的值
                final AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this)//单选对话框
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
                builder.create().show();
            }
        });

        describe=(EditText)findViewById(R.id.edittext_describe);
        describe.setText(cursor.getString(3));
        delete=(Button)findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase().execSQL("delete from whole where _id=?",new String[]{Integer.toString(itemId)});
                intent=new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back=(ImageButton)findViewById(R.id.imagebtn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(EditActivity.this,CheckActivity.class);//返回主界面
                startActivity(intent);
            }
        });

        ok=(ImageButton)findViewById(R.id.imagebtn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleStr = title.getText().toString();
                if (titleStr.equals("")) {
                    Toast mToast = Toast.makeText(EditActivity.this, "标题不能为空", Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    dateStr = date.getText().toString();//分别获取事件的各种信息
                    describeStr = describe.getText().toString();
                    kindStr = kind.getText().toString();
                    num = preferences.getInt("again", 0);
                    //dbHelper = new MyDatabaseHelper(AddActivity.this, "myall.db3", 1);
                    new Thread() {
                        DayLeft mDayleft;
                        @Override
                        public void run() {
                            mDayleft = new DayLeft();
                            leftDay = mDayleft.cal(dateStr, num);
                            againStr = Integer.toString(num);
                            dbHelper.getWritableDatabase().execSQL("update whole" +//将新添加的事件插入数据库
                                    " set title=?,date=?,describe=?,kind=?,dayleft=?,again=? where _id=?",
                                    new String[]{ titleStr, dateStr, describeStr, kindStr, Integer.toString(leftDay), againStr,Integer.toString(itemId)});
                        }
                    }.start();
                    //again_str = Integer.toString(num);
                   // dbHelper.getWritableDatabase().execSQL("update whole" +//将新添加的事件插入数据库
                     //               "set title=?,date=?,describe=?,kind=?,dayleft=?,again=? where _id=?",
                     //       new String[]{ title_str, date_str, describe_str, kind_str, Integer.toString(leftday), again_str,Integer.toString(itemid)});
                    cursor.close();
                    intent = new Intent(EditActivity.this, MainActivity.class);//返回主界面
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
}
