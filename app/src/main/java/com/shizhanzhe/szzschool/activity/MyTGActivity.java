package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.PagerAdapter;
import com.shizhanzhe.szzschool.fragment.KCFragment;
import com.shizhanzhe.szzschool.fragment.MyTGFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by zz9527 on 2017/6/13.
 */
@ContentView(R.layout.fragment_fl)
public class MyTGActivity extends FragmentActivity{
    @ViewInject(R.id.tablayout)
    TabLayout tabLayout;
    @ViewInject(R.id.tab_viewpager)
    ViewPager vp;
    @ViewInject(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        FragmentManager manager = getSupportFragmentManager();
        ArrayList<Fragment> list = new ArrayList<>();
        MyTGFragment kc = new MyTGFragment().newInstance(1);
        MyTGFragment ct = new MyTGFragment().newInstance(2);
        list.add(kc);
        list.add(ct);
        vp.setAdapter(new PagerAdapter(manager,list));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp);
        tabLayout.getTabAt(0).setText("我的开团");
        tabLayout.getTabAt(1).setText("我的参团");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTGActivity.this.finish();
            }
        });
    }
}
