package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.PagerAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2016/11/7.
 */
@ContentView(R.layout.fragment_fl)
public class FragmentFl extends Fragment  {

    List<ProBean.TxBean> gvlist;
    List<ProBean.TgBean> tglist;
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
        vp.setAdapter(new PagerAdapter(manager,list));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp);
        tabLayout.getTabAt(0).setText("团购报名");
        tabLayout.getTabAt(1).setText("课程体系");
    }
}

