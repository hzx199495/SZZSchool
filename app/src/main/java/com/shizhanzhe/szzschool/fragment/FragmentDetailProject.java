package com.shizhanzhe.szzschool.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.BuddyAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
public class FragmentDetailProject extends Fragment {
    @ViewInject(R.id.android_list)
    ExpandableListView elvCompany;

    ArrayList<ProBean2> gvlist;
    ArrayList<String> parent;
    HashMap<String, List<String>> map;

    public static FragmentDetailProject newInstance(String id,String uid, String token) {

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("uid", uid);
        args.putString("token", token);
        FragmentDetailProject fragment = new FragmentDetailProject();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail2, null);
        elvCompany = (ExpandableListView) view.findViewById(R.id.android_list);
        Bundle bundle = getArguments();
        String id=bundle.getString("id");
        String uid = bundle.getString("uid");
        String token =bundle.getString("token");
        String path = Path.SECOND(id,uid,token);
        Log.i("====", path);
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("====", json);
                Gson gson = new Gson();
                gvlist = gson.fromJson(json, new TypeToken<List<ProBean2>>() {
                }.getType());
                parent=new ArrayList<String>();
                for (int i = 0; i < gvlist.size(); i++) {
                    parent.add(gvlist.get(i).getCtitle());
                }
                map = new HashMap<String, List<String>>();
                for (int i = 0; i < parent.size(); i++) {
                    List<String> list = new ArrayList<String>();
                    List<ProBean2.ChoiceKcBean> choice_kc = gvlist.get(i).getChoice_kc();
                    for (int k = 0; k < choice_kc.size(); k++) {
                        list.add(choice_kc.get(k).getName());
                    }
                    map.put(parent.get(i), list);
                }
                ArrayList<String> parentImg=new ArrayList<String>();
                parentImg=null;
                BuddyAdapter adapter = new BuddyAdapter(parentImg,parent, map, getActivity());
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
                        parent.get(groupPosition)+ ":" +map.get(parent.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();

                Log.i("========",gvlist.get(groupPosition).getChoice_kc().get(childPosition).getMv_url());
                return false;
            }
        });
    }
}
