package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.TabAdapter;
import com.shizhanzhe.szzschool.fragment.ScheduleTabFragment;
import com.shizhanzhe.szzschool.fragment.TabLayoutFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/17.
 * 进度详情
 */
@ContentView(R.layout.activity_videolist)
public class ScheduleDeatilActivity extends FragmentActivity {
    @ViewInject(R.id.tab)
    TabLayout tab;
    @ViewInject(R.id.projectName)
    TextView name;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.viewpager)
    ViewPager viewpager;
    public static  List<String> tabTitle=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        tabTitle.clear();
        viewpager.setVisibility(View.VISIBLE);
        tab.setVisibility(View.VISIBLE);
        String json = getIntent().getStringExtra("json");
        Gson gson = new Gson();
        ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
        List<ProDeatailBean.CiBean> ci = gson.fromJson(json, ProDeatailBean.class).getCi();
        if (tx.getCatid().equals("41")){
            for (ProDeatailBean.CiBean bean:ci
                    ){
                tabTitle.add(bean.getCtitle());
            }
        }else{
            tabTitle.add("理论");
            tabTitle.add("精彩直播");
        }
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {
            fragments.add(ScheduleTabFragment.newInstance(i + 1,getIntent().getStringExtra("id"),json));
        }
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments,tabTitle);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (tabTitle.size()<5){
            tab.setTabMode(TabLayout.MODE_FIXED);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(getIntent().getStringExtra("name"));
    }

}
