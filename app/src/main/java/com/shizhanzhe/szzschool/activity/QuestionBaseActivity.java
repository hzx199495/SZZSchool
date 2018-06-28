package com.shizhanzhe.szzschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shizhanzhe.szzschool.Bean.QuestionProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.TabAdapter;
import com.shizhanzhe.szzschool.fragment.QuestionListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 * 问答分类
 */

public class QuestionBaseActivity extends FragmentActivity {

    public static List<String> tabTitle=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_question);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        MyApplication.userType=1;
        tabTitle.clear();
        viewpager.setVisibility(View.VISIBLE);
        tab.setVisibility(View.VISIBLE);
        QuestionProBean bean=(QuestionProBean)getIntent().getSerializableExtra("bean");
            for (QuestionProBean.ChavideoBean cvideo:bean.getChavideo()
                 ) {
                tabTitle.add(cvideo.getCtitle());
            }
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {

            fragments.add(QuestionListFragment.newInstance(i + 1,bean));
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

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



}
