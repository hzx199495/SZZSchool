package com.shizhanzhe.szzschool.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.Bean.TGBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.TGDetailActivity;
import com.shizhanzhe.szzschool.adapter.MyCTAdapter;
import com.shizhanzhe.szzschool.adapter.MyKTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/6/13.
 */
@ContentView(R.layout.fragment_kc)
public class MyTGFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.lv)
    ListView lv;
    RefreshLayout swipeLayout;
    int type;
    public static MyTGFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MyTGFragment fragment = new MyTGFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setListener();
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        getData(type);
    }
    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void init(View view) {
        swipeLayout = (RefreshLayout)view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.darkgray, R.color.blue2, R.color.red, R.color.green);
    }
    void getData(int type){
        if (type == 1) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(),  new Path(getContext()).MYKT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<MyKTBean> kclist = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                    }.getType());
                    if (kclist != null && kclist.size() > 0) {
                        lv.setAdapter(new MyKTAdapter(getContext(), kclist));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent();
                                intent.setClass(getContext(), TGDetailActivity.class);
                                intent.putExtra("tuanid", kclist.get(position).getTuanid());
                                intent.putExtra("type", 1);
                                getContext().startActivity(intent);


                            }
                        });
                    }else {
                        swipeLayout.setVisibility(View.GONE);
                    }
                }
            });

        } else if (type == 2) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYCT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<MyCTBean> ctlist = gson.fromJson(json, new TypeToken<List<MyCTBean>>() {
                    }.getType());
                    if (ctlist != null && ctlist.size() > 0) {
                        lv.setAdapter(new MyCTAdapter(getContext(), ctlist));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.setClass(getContext(), TGDetailActivity.class);
                                intent.putExtra("tuanid", ctlist.get(position).getTuanid());
                                intent.putExtra("type", 2);
                                getContext().startActivity(intent);
                            }
                        });
                    }else {
                        swipeLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(type);
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }
}




