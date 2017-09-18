package com.shizhanzhe.szzschool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.shizhanzhe.szzschool.activity.ProjectDetailActivity;

import java.util.List;

/**
 * Created by zz9527 on 2017/5/3.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String [] tabTitle;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments,String [] tabTitle) {
        super(fm);
        this.fragments = fragments;
        this.tabTitle=tabTitle;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout标题
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];

    }
}
