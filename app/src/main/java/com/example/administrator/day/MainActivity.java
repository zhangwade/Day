/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-13 下午5:38
 */

package com.example.administrator.day;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.Window;

//import com.example.administrator.day.XCSlideView.R;


public class MainActivity extends BaseActivity{

    //MyDatabaseHelper dbHelper;
    //ListView listview;
    //Button all=null,anniversary=null,work=null,life=null;
    //Cursor cursor;
    //SimpleCursorAdapter adapter;
    //Handler handler;
    //private Context mContext;
    //屏幕宽度
    //private int mScreenWidth = 0;
    //private XCSlideView mSlideViewLeft;
    //private XCSlideView mSlideViewRight;
    public static final String SELECTED_ITEM="selected_item";//选中Tab项的key
    public String[] names=new String[]{"主题","壁纸","小部件","万年历","设置","倒数日专业版","问题反馈","分享","更新日志","关于"};
    //ActionBar actionBar;

    public ImageButton menuButton,addButton;
    public TextView tab1,tab2,tab3,tab4;
    public View tag1,tag2,tag3,tag4;
    public ListView lv;
    public DrawerLayout drawerLayout;

    public Fragment fragment;
    public FragmentTransaction ft;

    public Bundle args;


    public int[] imageIds=new int[]{R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,R.drawable.icon_back,
            R.drawable.icon_back,R.drawable.icon_back,};
    public int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.list_menu);
        List<Map<String,Object>> mListItems=new ArrayList<Map<String,Object>>();
        for(int i=0;i<names.length;i++){
            Map<String,Object> mListItem=new HashMap<String,Object>();
            mListItem.put("image",imageIds[i]);
            mListItem.put("name",names[i]);
            mListItems.add(mListItem);
        }
        SimpleAdapter mSimpleAdapter=new SimpleAdapter(this,mListItems,R.layout.item_root_menu,new String[]{"image","name"},new int[]{R.id.image_item_root_menu,R.id.text_item_root_menu});
        lv.setAdapter(mSimpleAdapter);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);//设置自定义标题
        /*actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//设置Actionbar的导航方式：Tab导航
        actionBar.addTab(actionBar.newTab().setText(R.string.all).setTabListener(this));//依次添加3个Tab标签，并添加事件监听器
        actionBar.addTab(actionBar.newTab().setText(R.string.anniversary).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.work).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.life).setTabListener(this));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_main);*/
        //init();
        index=1;
        tag1=(View)findViewById(R.id.view_navigation_tag1);
        tag2=(View)findViewById(R.id.view_navigation_tag2);
        tag3=(View)findViewById(R.id.view_navigation_tag3);
        tag4=(View)findViewById(R.id.view_navigation_tag4);
        tag1.setBackgroundColor(getResources().getColor(R.color.red));
        /*fragment=new ListviewFragment();
        args=new Bundle();
        args.putInt("section_number", index);//将选中Tab的序号传入bundle
        fragment.setArguments(args);
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);//加载fragment
        ft.commit();*/
        /*
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("itemName", names[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.slidemenu_item, new String[]{"itemName"}, new int[]{R.id.slidemenu_itemname});
        ListView listView = (ListView) findViewById(R.id.slidemenu);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        */
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout_root);
        menuButton = (ImageButton)findViewById(R.id.imagebtn_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(lv);
            }
        });
        addButton = (ImageButton) findViewById(R.id.imagebtn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        tab1=(TextView)findViewById(R.id.text_navigation_tab1);
        tab1.setOnClickListener(new TabListener());
        tab2=(TextView)findViewById(R.id.text_navigation_tab2);
        tab2.setOnClickListener(new TabListener());
        tab3=(TextView)findViewById(R.id.text_navigation_tab3);
        tab3.setOnClickListener(new TabListener());
        tab4=(TextView)findViewById(R.id.text_navigation_tab4);
        tab4.setOnClickListener(new TabListener());
        System.out.println("Main-oncreate");
    }
/*
    private void init() {
        mContext = this;
        initSlideView();
    }
    private void initSlideView() {

        mScreenWidth = DensityUtil.getScreenWidthAndHeight(mContext)[0];
        View menuViewLeft = LayoutInflater.from(mContext).inflate(R.layout.layout_slideview,null);
        mSlideViewLeft = XCSlideView.create(this, XCSlideView.Positon.LEFT);
        mSlideViewLeft.setMenuView(MainActivity.this, menuViewLeft);
        mSlideViewLeft.setMenuWidth(mScreenWidth * 7 / 9);
        ImageButton menu_button = (ImageButton)findViewById(R.id.menu);
        menu_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!mSlideViewLeft.isShow())
                    mSlideViewLeft.show();
            }
        });
        menuViewLeft.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSlideViewLeft.isShow()) {
                    mSlideViewLeft.dismiss();
                }
            }
        });

        mSlideViewRight = XCSlideView.create(this, XCSlideView.Positon.RIGHT);
        View menuViewRight = LayoutInflater.from(mContext).inflate(R.layout.layout_slideview,null);
        mSlideViewRight.setMenuView(MainActivity.this, menuViewRight);
        mSlideViewRight.setMenuWidth(mScreenWidth * 7 / 9);
        Button right = (Button)findViewById(R.id.btn_right);
        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!mSlideViewRight.isShow())
                    mSlideViewRight.show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(mSlideViewLeft.isShow()){
            mSlideViewLeft.dismiss();
            return ;
        }
        super.onBackPressed();
    }

        dbHelper=new MyDatabaseHelper(this,"myall.db3",1);
        listview=(ListView)findViewById(R.id.listview);
        cursor=dbHelper.getReadableDatabase().rawQuery("select * from whole",null);//打开软件即从数据库all表中查询数据，加载listview
        adapter=new SimpleCursorAdapter(MainActivity.this,R.layout.item,
                cursor,new String[]{"title","date","dayleft"},
                new int[]{R.id.title,R.id.date,R.id.dayleft},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listview.setAdapter(adapter);
        handler=new Handler();
        all=(Button)findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor=dbHelper.getReadableDatabase().rawQuery("select * from whole",null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();//通知listview更新
                    }
                }, 100);
            }
        });
        anniversary=(Button)findViewById(R.id.anniversary);
        anniversary.setOnClickListener(new ButtonClickListener("anniversary"));
        work=(Button)findViewById(R.id.work);
        work.setOnClickListener(new ButtonClickListener("work"));
        life=(Button)findViewById(R.id.life);
        life.setOnClickListener(new ButtonClickListener("life"));
        */

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu_add, menu);//装填R.menu.menu_add对应的菜单，并添加到menu中
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
            return true;
        }
        else{
            return super.onKeyDown(keyCode, event);
        }
    }
    */

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState.containsKey(SELECTED_ITEM))
            index=savedInstanceState.getInt(SELECTED_ITEM);
        System.out.println("Main-onrestoresave");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(SELECTED_ITEM, index);
        System.out.println("Main-onsave");
    }

    @Override
    public void onResume(){
        super.onResume();
        fragment=new ListviewFragment();
        args=new Bundle();
        args.putInt("section_number",index);//将选中Tab的序号传入bundle
        fragment.setArguments(args);
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout_content,fragment);//加载fragment
        ft.commit();
        System.out.println("Main-onresume");
    }
    /*
    @Override
    public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){}
    @Override
    public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){
        fragment=new ListviewFragment();
        args=new Bundle();
        args.putInt("section_number",tab.getPosition()+1);//将选中Tab的序号传入bundle
        fragment.setArguments(args);
        ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);//加载fragment
        ft.commit();
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction){}
*/
    public void changeStatus(int ind){
        switch (index){
            case 1:
                tag1.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 2:
                tag2.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 3:
                tag3.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case 4:
                tag4.setBackgroundColor(getResources().getColor(R.color.green));
                break;
        }
        switch (ind){
            case 1:
                tag1.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 2:
                tag2.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 3:
                tag3.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case 4:
                tag4.setBackgroundColor(getResources().getColor(R.color.red));
                break;
        }
        System.out.println("Main-changestatus");
    }
    public class TabListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            System.out.println("Main-onclick");
            int ind = 1;
            switch (view.getId()) {
                case R.id.text_navigation_tab1:
                    ind = 1;
                    break;
                case R.id.text_navigation_tab2:
                    ind = 2;
                    break;
                case R.id.text_navigation_tab3:
                    ind = 3;
                    break;
                case R.id.text_navigation_tab4:
                    ind = 4;
                    break;
            }
            System.out.println("ind="+ind);

            if (ind != index) {
                changeStatus(ind);
                index = ind;
                System.out.println("index="+index);
                fragment = new ListviewFragment();
                args = new Bundle();
                args.putInt("section_number", index);//将选中Tab的序号传入bundle
                fragment.setArguments(args);
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.framelayout_content, fragment);//加载fragment
                ft.commit();
                System.out.println("Main-ft.commit");
            }
        }
    }
    /*
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(dbHelper!=null)
            dbHelper.close();
    }

    public class ButtonClickListener implements View.OnClickListener{
        public String kind;
        public ButtonClickListener(String kind){
            this.kind=kind;
        }
        @Override
        public void onClick(View v){
            cursor=dbHelper.getReadableDatabase().
                    rawQuery("select * from whole where kind= ?", new String[]{kind});
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();//通知listview更新
                }
            }, 100);
        }
    }


    public class MyDatabaseHelper extends SQLiteOpenHelper{
        String CREATE_TABLE_SQL="create table whole(_id integer primary key " +
                "autoincrement,title,date,describe,dayleft,kind)";
        public MyDatabaseHelper(Context context,String name,int version){
            super(context,name,null,version);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_SQL);
            db.execSQL("insert into whole values(null,?,?,?,?,?)",
                    new String[]{"新年","2017-01-01 周一","无","233","life"});
            db.execSQL("insert into whole values(null,?,?,?,?,?)",
                    new String[]{"儿童节","2017-06-01 周一","无","234","work"});
            db.execSQL("insert into whole values(null,?,?,?,?,?)",
                    new String[]{"结婚纪念日","2017-02-05 周一","无","235","anniversary"});
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
            System.out.println("---onUpdate Called---"+oldversion+"--->"+newversion);
        }
    }
    */
}
