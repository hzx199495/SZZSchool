package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.Videoadapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/5/3.
 */

public class TabLayoutFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private ListView lv;


    public static TabLayoutFragment newInstance(int type) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.lv);
        getData();

    }
    ProBean2.CiBean list;
    protected void initView() {


        switch (type) {
            case 1:

                final ArrayList<VideoBean> videolist1 = new ArrayList<>();
                for (ProBean2.CiBean.A0Bean.ChoiceKcBean bean: list.getA0().getChoice_kc()
                     ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist1.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(),videolist1));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.videotype=type;
                        MyApplication.videoitemid=videolist1.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist1.get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 2:

                final ArrayList<VideoBean> videolist2 = new ArrayList<>();
                for (ProBean2.CiBean.A1Bean.ChoiceKcBeanX bean : list.getA1().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist2.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(),videolist2));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.videotype=type;
                        MyApplication.videoitemid=videolist2.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist2.get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 3:
                final ArrayList<VideoBean> videolist3 = new ArrayList<>();
                for (ProBean2.CiBean.A2Bean.ChoiceKcBeanXX bean: list.getA2().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist3.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(),videolist3));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.videotype=type;
                        MyApplication.videoitemid=videolist3.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist3.get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 4:
                final ArrayList<VideoBean> videolist4 = new ArrayList<>();
                for (ProBean2.CiBean.A3Bean.ChoiceKcBeanXXX bean: list.getA3().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist4.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(),videolist4));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.videotype=type;
                        MyApplication.videoitemid=videolist4.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist4.get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 5:
                final ArrayList<VideoBean> videolist5 = new ArrayList<>();
                for (ProBean2.CiBean.A4Bean.ChoiceKcBeanXXXX bean: list.getA4().getChoice_kc()
                        ) {
                    VideoBean video = new VideoBean();
                    video.setGrade(bean.getGrade());
                    video.setId(bean.getId());
                    video.setKc_hours(bean.getKc_hours());
                    video.setMv_url(bean.getMv_url());
                    video.setName(bean.getName());
                    video.setSort(bean.getSort());
                    videolist5.add(video);
                }
                lv.setAdapter(new Videoadapter(getContext(),videolist5));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyApplication.videotype=type;
                        MyApplication.videoitemid=videolist5.get(position).getId();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, videolist5.get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
        }


    }
    void getData(){
        Gson gson = new Gson();
        list = gson.fromJson(MyApplication.videojson, ProBean2.class).getCi();
        initView();

    }

}
