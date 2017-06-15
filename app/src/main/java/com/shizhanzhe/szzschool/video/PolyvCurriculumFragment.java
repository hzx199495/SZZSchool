package com.shizhanzhe.szzschool.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.shizhanzhe.szzschool.R;

import java.util.ArrayList;

public class PolyvCurriculumFragment extends Fragment {
    // viewpager切换的时候，fragment执行销毁View方法，但fragment对象没有被销毁
    // 课程目录的listView
    private ListView lv_cur;
    // fragmentView
    private View view;
    // 加载中控件
    private ProgressBar pb_loading;

    private String videoId = "";
    private static final int SETTING = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_tab_cur, container, false);
        return view;
    }


    private void findIdAndNew() {
        lv_cur = (ListView) view.findViewById(R.id.lv_cur);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }








}
