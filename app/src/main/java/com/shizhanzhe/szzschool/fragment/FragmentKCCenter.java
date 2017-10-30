package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.PagerAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by hasee on 2016/11/7.
 * 课程
 */

@ContentView(R.layout.fragment_fl)
public class FragmentKCCenter extends Fragment {
    @ViewInject(R.id.tablayout)
    TabLayout tabLayout;
    @ViewInject(R.id.tab_viewpager)
    ViewPager vp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager manager = getChildFragmentManager();
        ArrayList<Fragment> list = new ArrayList<>();
        KCFragment tg = new KCFragment().newInstance(0);
        KCFragment tx = new KCFragment().newInstance(1);
        list.add(tg);
        list.add(tx);
        vp.setAdapter(new PagerAdapter(manager, list));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp);
        tabLayout.getTabAt(0).setText("团购报名");
        tabLayout.getTabAt(1).setText("课程体系");


    }

}

