package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.ListAdapter;
import com.shizhanzhe.szzschool.adapter.MyAdapter;
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
public class FragmentFl extends Fragment implements ListView.OnItemClickListener {
    @ViewInject(R.id.lv_title)
    ListView lv_title;
    @ViewInject(R.id.fl_gv)
    GridView fl_gv;
    private ArrayList<ProBean> gvlist;
    private MyAdapter  myadapter;
    private String[] strs = {"课程体系", "进阶课程", "职业课程", "推荐课程"};
    public static int mPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myadapter = new MyAdapter(getContext(), strs);
        lv_title.setAdapter(myadapter);
        lv_title.setOnItemClickListener(this);
//        rg.setOnCheckedChangeListener(this);
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CLASSIFY(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                gvlist = gson.fromJson(json, new TypeToken<List<ProBean>>() {
                }.getType());
            }
        });


    }
    GVAdapter adapter;

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.rg_tx:
//                ArrayList<ProBean> tx = new ArrayList<>();
//                for (int i=0;i<gvlist.size();i++) {
//                    if(gvlist.get(i).getCatid().equals("2")){
//                        tx.add(gvlist.get(i));
//                    }
//                }
//                adapter = new GVAdapter(tx, getActivity());
//                fl_gv.setAdapter(adapter);
//                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DetailActivity.class);
//
//                        String title = gvlist.get(position).getStitle();
//                        String img = gvlist.get(position).getThumb();
//                        String intro = gvlist.get(position).getIntroduce();
//                        String catid = gvlist.get(position).getCatid();
//                        intent.putExtra("id", catid);
//                        intent.putExtra("img", img);
//                        intent.putExtra("title", title);
//                        intent.putExtra("intro", intro);
//                        startActivity(intent);
//                    }
//                });
//                break;
//            case R.id.rg_jj:
//                ArrayList<ProBean> jj = new ArrayList<>();
//            for (int i=0;i<gvlist.size();i++) {
//            if(gvlist.get(i).getCatid().equals("3")){
//                jj.add(gvlist.get(i));
//            }
//        }
//                adapter = new GVAdapter(jj, getActivity());
//                fl_gv.setAdapter(adapter);
//                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DetailActivity.class);
//
//                        String title = gvlist.get(position).getStitle();
//                        String img = gvlist.get(position).getThumb();
//                        String intro = gvlist.get(position).getIntroduce();
//                        String catid = gvlist.get(position).getCatid();
//                        intent.putExtra("id", catid);
//                        intent.putExtra("img", img);
//                        intent.putExtra("title", title);
//                        intent.putExtra("intro", intro);
//                        startActivity(intent);
//                    }
//                });
//                break;
//            case R.id.rg_zy:
//                ArrayList<ProBean> zy = new ArrayList<>();
//                for (int i=0;i<gvlist.size();i++) {
//                    if(gvlist.get(i).getCatid().equals("4")){
//                        zy.add(gvlist.get(i));
//                    }
//                }
//                adapter = new GVAdapter(zy, getActivity());
//                fl_gv.setAdapter(adapter);
//                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DetailActivity.class);
//
//                        String title = gvlist.get(position).getStitle();
//                        String img = gvlist.get(position).getThumb();
//                        String intro = gvlist.get(position).getIntroduce();
//                        String catid = gvlist.get(position).getCatid();
//                        intent.putExtra("id", catid);
//                        intent.putExtra("img", img);
//                        intent.putExtra("title", title);
//                        intent.putExtra("intro", intro);
//                        startActivity(intent);
//                    }
//                });
//                break;
//            case R.id.rg_rm:
//                ArrayList<ProBean> rm = new ArrayList<>();
//                for (int i=0;i<gvlist.size();i++) {
//                    if(gvlist.get(i).getCatid().equals("2")){
//                        rm.add(gvlist.get(i));
//                    }
//                }
//                adapter = new GVAdapter(rm, getActivity());
//                fl_gv.setAdapter(adapter);
//                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DetailActivity.class);
//
//                        String title = gvlist.get(position).getStitle();
//                        String img = gvlist.get(position).getThumb();
//                        String intro = gvlist.get(position).getIntroduce();
//                        String catid = gvlist.get(position).getCatid();
//                        intent.putExtra("id", catid);
//                        intent.putExtra("img", img);
//                        intent.putExtra("title", title);
//                        intent.putExtra("intro", intro);
//                        startActivity(intent);
//                    }
//                });
//                break;
//        }
//    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lv_title.setItemChecked(1,true);
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        myadapter.notifyDataSetChanged();
        switch (position) {
            case 0:
                ArrayList<ProBean> tx = new ArrayList<>();
                for (int i=0;i<gvlist.size();i++) {
                    if(gvlist.get(i).getCatid().equals("2")){
                        tx.add(gvlist.get(i));
                    }
                }
                adapter = new GVAdapter(tx, getActivity());
                fl_gv.setAdapter(adapter);
                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);

                        String title = gvlist.get(position).getStitle();
                        String img = gvlist.get(position).getThumb();
                        String intro = gvlist.get(position).getIntroduce();
                        String catid = gvlist.get(position).getCatid();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                ArrayList<ProBean> jj = new ArrayList<>();
            for (int i=0;i<gvlist.size();i++) {
            if(gvlist.get(i).getCatid().equals("3")){
                jj.add(gvlist.get(i));
            }
        }
                adapter = new GVAdapter(jj, getActivity());
                fl_gv.setAdapter(adapter);
                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);

                        String title = gvlist.get(position).getStitle();
                        String img = gvlist.get(position).getThumb();
                        String intro = gvlist.get(position).getIntroduce();
                        String catid = gvlist.get(position).getCatid();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        startActivity(intent);
                    }
                });
                break;
            case 2:
                ArrayList<ProBean> zy = new ArrayList<>();
                for (int i=0;i<gvlist.size();i++) {
                    if(gvlist.get(i).getCatid().equals("4")){
                        zy.add(gvlist.get(i));
                    }
                }
                adapter = new GVAdapter(zy, getActivity());
                fl_gv.setAdapter(adapter);
                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);

                        String title = gvlist.get(position).getStitle();
                        String img = gvlist.get(position).getThumb();
                        String intro = gvlist.get(position).getIntroduce();
                        String catid = gvlist.get(position).getCatid();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        startActivity(intent);
                    }
                });
                break;
            case 3:
                ArrayList<ProBean> rm = new ArrayList<>();
                for (int i=0;i<gvlist.size();i++) {
                    if(gvlist.get(i).getCatid().equals("2")){
                        rm.add(gvlist.get(i));
                    }
                }
                adapter = new GVAdapter(rm, getActivity());
                fl_gv.setAdapter(adapter);
                fl_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DetailActivity.class);

                        String title = gvlist.get(position).getStitle();
                        String img = gvlist.get(position).getThumb();
                        String intro = gvlist.get(position).getIntroduce();
                        String catid = gvlist.get(position).getCatid();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}


