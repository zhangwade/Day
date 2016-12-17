/*
 * Created by WadeZhang on 16-12-13 下午5:42
 * Copyright (c) 2016. All rights reserved.
 *
 * Last modified 16-12-13 下午5:38
 */

package com.example.administrator.day;

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


public class MainActivity extends BaseActivity{

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
        index=1;
        tag1=(View)findViewById(R.id.view_navigation_tag1);
        tag2=(View)findViewById(R.id.view_navigation_tag2);
        tag3=(View)findViewById(R.id.view_navigation_tag3);
        tag4=(View)findViewById(R.id.view_navigation_tag4);
        tag1.setBackgroundColor(getResources().getColor(R.color.red));
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
}
