package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyDetailActivity;
import com.shizhanzhe.szzschool.adapter.BuddyAdapter;
import com.shizhanzhe.szzschool.adapter.ListAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.SerializableHashMap;


import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
public class FragmentMyProject extends Fragment {
    @ViewInject(R.id.android_mylist)
    ExpandableListView elvCompany;
    ArrayList<String> parentPro;
    HashMap<String, List<String>> map;
    ArrayList<List<MyProBean.CourseBean>> course;
    public static FragmentMyProject newInstance(String uid, String token) {

        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("token", token);
        FragmentMyProject fragment = new FragmentMyProject();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, null);
        elvCompany = (ExpandableListView) view.findViewById(R.id.android_mylist);
        Bundle bundle = getArguments();
        String uid = bundle.getString("uid");
        String token = bundle.getString("token");
        String path = Path.MYCLASS(uid,token);
        Log.i("====", path);
        OkHttpDownloadJsonUtil.downloadJson(getContext(), path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("====", json);

                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(json).getAsJsonArray();

                Gson gson = new Gson();
                ArrayList<List<MyProBean.SysinfoBean>> sysinfo = new ArrayList<>();
                course = new ArrayList<>();

                //加强for循环遍历JsonArray
                for (JsonElement pro : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    MyProBean proBean = gson.fromJson(pro, MyProBean.class);
                    sysinfo.add(proBean.getSysinfo());
                    course.add(proBean.getCourse());
                }
                parentPro=new ArrayList<String>();
                for (int i = 0; i < sysinfo.size(); i++) {
                    List<MyProBean.SysinfoBean> sysinfoBeen = sysinfo.get(i);
                    for (int m=0;m<sysinfoBeen.size();m++) {
                        parentPro.add(sysinfoBeen.get(m).getStitle());
                        Log.i("=====",sysinfoBeen.get(m).getStitle());
                    }
                }
                map = new HashMap<String, List<String>>();
                for (int i = 0; i < parentPro.size(); i++) {
                    List<String> list = new ArrayList<String>();
                    List<MyProBean.CourseBean> courseBeen = course.get(i);
                    for (int k = 0; k < courseBeen.size(); k++) {
                        list.add(courseBeen.get(k).getCtitle());
                        Log.i("=====",courseBeen.get(k).getCtitle());
                    }
                    map.put(parentPro.get(i), list);
                }
                BuddyAdapter adapter = new BuddyAdapter(parentPro, map, getContext());
                elvCompany.setAdapter(adapter);
            }

        });

        setListeners();
        return view;
    }
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
                        parentPro.get(groupPosition)+ ":" +map.get(parentPro.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                List<MyProBean.CourseBean.ChoiceKcBean> choice_kc = course.get(groupPosition).get(childPosition).getChoice_kc();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> url = new ArrayList<String>();
                for (int i=0;i<choice_kc.size();i++){
                   name.add(choice_kc.get(i).getName());
                    url.add(choice_kc.get(i).getMv_url());
                }
                Intent intent=new Intent(getContext(),MyDetailActivity.class);
                intent.putStringArrayListExtra("name",name);
                intent.putStringArrayListExtra("url",url);
                intent.putExtra("title",course.get(groupPosition).get(childPosition).getCtitle());
                startActivity(intent);
                return false;
            }
        });
    }
}
