package com.shizhanzhe.szzschool.widge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shizhanzhe.szzschool.fragment.IntroFragment;
import com.shizhanzhe.szzschool.fragment.ProExpanFragment;

/**
 * Created by Administrator on 2017/10/25.
 */

public class MPagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabTitle = new String[]{"课程简介", "课程目录"};
    private IntroFragment firstFragment;
    private ProExpanFragment secondFragment;
    private String json,id,imgs;
    public MPagerAdapter(FragmentManager fm,String json,String id,String imgs) {
        super(fm);
        this.json=json;
        this.id=id;
        this.imgs=imgs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (firstFragment == null) {
                firstFragment = IntroFragment.newInstance(id, imgs);
            }
            return firstFragment;
        } else if (position == 1) {
            if (secondFragment == null) {
                secondFragment = ProExpanFragment.newInstance(json, id);
            }
            return secondFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

}
