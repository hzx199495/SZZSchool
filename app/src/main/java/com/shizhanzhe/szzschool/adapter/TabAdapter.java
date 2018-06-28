package com.shizhanzhe.szzschool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.shizhanzhe.szzschool.activity.ProjectDetailActivity;

import java.util.List;

/**
 * Created by zz9527 on 2017/5/3.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> tabTitle;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments,List<String> tabTitle) {
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
        return tabTitle.get(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
