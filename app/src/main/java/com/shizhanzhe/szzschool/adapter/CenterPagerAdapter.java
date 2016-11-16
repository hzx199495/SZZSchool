package com.shizhanzhe.szzschool.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hasee on 2016/11/7.
 */
public class CenterPagerAdapter extends PagerAdapter {
    private ArrayList<View> list;

    public CenterPagerAdapter(ArrayList<View> list) {
        this.list = list;
    }

    /**
     * 定义Item数量
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 判断当前视图是否是以前已经加载过的
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 加载当前item
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        container.addView(view);

        return view;
    }

    /**
     * 移除不常用的Item
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
