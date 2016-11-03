package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.VideoActivity;
import com.shizhanzhe.szzschool.adapter.BuddyAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;


import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
public class FragmentProject extends Fragment {
    @ViewInject(R.id.android_list)
    ExpandableListView elvCompany;


    ArrayList<String> parent;
    HashMap<String, List<String>> map;

    public static FragmentProject newInstance(String ut) {

        Bundle args = new Bundle();
        args.putString("ut", ut);
        FragmentProject fragment = new FragmentProject();
        fragment.setArguments(args);
        return fragment;
    }

    // 一级条目
//    private String[] group = new String[] { "一级课程1","一级课程2" };
    //二级条目
//    private String[][] carsList = new String[][] {{ "Android", "IOS", "JAVA", "C",
//            "PHP" },{"1","2","3","4","5"}};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, null);
        elvCompany = (ExpandableListView) view.findViewById(R.id.android_list);
        Bundle bundle = getArguments();
        String ut = bundle.getString("ut");
        String path = "http://shizhanzhe.com/index.php?m=pcdata.course_data&pc=1&" + ut;
        Log.i("====", path);
        OkHttpDownloadJsonUtil.downloadJson(getContext(), path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("====", json);
                Gson gson = new Gson();
                StringBuffer sb = new StringBuffer (json);
                Log.i("======",sb.length()+"");
                sb.deleteCharAt(1);//减1是因为java中String的索引是从0开始的，如果我们所指定的index以0开始的话，这里可以不用减1
                sb.deleteCharAt(sb.length()-1);
                Log.i("======",sb.toString()+sb.length());
                //父标题集合
                List<ProBean.SysinfoBean> sysinfo = gson.fromJson(sb.toString(), ProBean.class).getSysinfo();
                //子标题集合
                List<ProBean.CourseBean> course = gson.fromJson(sb.toString(), ProBean.class).getCourse();

                parent=new ArrayList<String>();
                for (int i = 0; i < sysinfo.size(); i++) {
                    parent.add(sysinfo.get(i).getStitle());
                    Log.i("====",sysinfo.get(i).getStitle()+"");
                }

                map = new HashMap<String, List<String>>();
                for (int i = 0; i < parent.size(); i++) {
                    List<String> list = new ArrayList<String>();
                    for (int k = 0; k < course.size(); k++) {
                        list.add(course.get(k).getCtitle());
                    }
                    map.put(parent.get(i), list);
                }
                BuddyAdapter adapter = new BuddyAdapter(parent, map, getContext());
                elvCompany.setAdapter(adapter);
            }

        });


        setListeners();

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

    private void setListeners() {
        // 分组展开
        elvCompany.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });
        // 分组关闭
        elvCompany.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // 子项点击
        elvCompany.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent2, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(),
                        parent.get(groupPosition)+ ":" +map.get(parent.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), VideoActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
