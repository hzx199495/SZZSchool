package com.shizhanzhe.szzschool.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.Bean.QuestionListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.QuestionListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public class QuestionListFragment extends Fragment {

    private TabLayout tab;

    private ListView lv;
    private JSONArray arr, videoArr;
    private List<Video> list;
    private ArrayAdapter<String> adapter;
    private String con;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questionlist, container, false);
        lv = (ListView) v.findViewById(R.id.lv);
        tab = (TabLayout) v.findViewById(R.id.tl);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setTabMode(TabLayout.MODE_FIXED);
        con = getArguments().getString("con");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.text_item);
        init();
        showList(0);
        lv.setAdapter(adapter);
        return v;
    }

    private void init() {
        try {
            JSONObject obj = new JSONObject(con);
            arr = obj.getJSONArray("chavideo");
            if (arr.length()==1){
                tab.setSelectedTabIndicatorColor(Color.WHITE);
            }
            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = arr.optJSONObject(i);
                String name = item.getString("ctitle");
                tab.addTab(tab.newTab().setText(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showList(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), QuestionListActivity.class);
                intent.putExtra("videoId", list.get(i).id);
                intent.putExtra("name", list.get(i).name);
                intent.putExtra("sid", list.get(i).sid);
                intent.putExtra("pid", list.get(i).pid);
                startActivity(intent);
            }
        });
    }


    private void showList(int index) {
        JSONObject obj = arr.optJSONObject(index);
        videoArr = obj.optJSONArray("video");

        if (videoArr.length() == 0) {
            lv.setVisibility(View.GONE);
        } else {
            lv.setVisibility(View.VISIBLE);
            list = new ArrayList<Video>();
            adapter.clear();
            for (int i = 0; videoArr != null && i < videoArr.length(); i++) {
                Video v = new Video();
                v.id = videoArr.optJSONObject(i).optString("id");
                v.name = videoArr.optJSONObject(i).optString("title");
                v.sid = videoArr.optJSONObject(i).optString("sid");
                v.pid = videoArr.optJSONObject(i).optString("pid");
                list.add(v);
                adapter.add(v.name);
            }
            adapter.notifyDataSetChanged();
        }
    }

    class Video {
        String id;
        String name;
        String sid;
        String pid;
    }


}
