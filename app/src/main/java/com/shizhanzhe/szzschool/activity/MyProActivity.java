package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MyProAdapter;
import com.shizhanzhe.szzschool.adapter.TabAdapter;
import com.shizhanzhe.szzschool.fragment.ProFragment;
import com.shizhanzhe.szzschool.fragment.TabLayoutFragment;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 * 我的课程
 */
@ContentView(R.layout.activity_mypro)
public class MyProActivity extends FragmentActivity {

    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.tab)
    TabLayout tab;
    @ViewInject(R.id.viewpager)
    ViewPager viewpager;
    public static  List<String> tabTitle=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        tabTitle.clear();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        tabTitle.add("课程");
        tabTitle.add("收藏");
        tabTitle.add("笔记");
        for (int i = 0; i < tabTitle.size(); i++) {
            fragments.add(ProFragment.newInstance(i + 1));
        }
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments,tabTitle);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setTabMode(TabLayout.MODE_FIXED);

    }
}
