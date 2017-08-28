package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.ScheduleBean;
import com.shizhanzhe.szzschool.Bean.VideoBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.ScheduleDeatilAdapter;
import com.shizhanzhe.szzschool.adapter.Videoadapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/17.
 */

public class ScheduleTabFragment extends Fragment {

    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    ListView lv;

    public static ScheduleTabFragment newInstance(int type, String id) {
        ScheduleTabFragment fragment = new ScheduleTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        bundle.putString("id", id);
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
        getdata(getArguments().getString("id"));
        MyApplication.videotitle = "网络推广变现·掘金";

    }

    private void initView(final List<ScheduleBean.InfoBean.KcDataBean> list) {

        switch (type) {
            case 1:

                lv.setAdapter(new ScheduleDeatilAdapter(getContext(), list.get(0).getVdata()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (list.get(0).getVdata().get(position).getVdetail().getGuantime()!=null){
                            MyApplication.schedule=Integer.parseInt(list.get(0).getVdata().get(position).getVdetail().getGuantime());
                        }else {
                            MyApplication.schedule=0;
                        }
                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(0).getCid();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = list.get(0).getVdata().get(position).getVid();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.getA0().getChoice_kc().get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 2:


                lv.setAdapter(new ScheduleDeatilAdapter(getContext(), list.get(1).getVdata()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(1).getCid();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = list.get(1).getVdata().get(position).getVid();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.getA1().getChoice_kc().get(position).getMv_url());
                        getContext().startActivity(intent);

                    }
                });
                break;
            case 3:
                lv.setAdapter(new ScheduleDeatilAdapter(getContext(), list.get(2).getVdata()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(2).getCid();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = list.get(2).getVdata().get(position).getVid();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.getA2().getChoice_kc().get(position).getMv_url());
                        getContext().startActivity(intent);

                    }
                });
                break;
            case 4:

                lv.setAdapter(new ScheduleDeatilAdapter(getContext(), list.get(3).getVdata()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(3).getCid();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = list.get(3).getVdata().get(position).getVid();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.getA3().getChoice_kc().get(position).getMv_url());
                        getContext().startActivity(intent);

                    }
                });
                break;
            case 5:

                lv.setAdapter(new ScheduleDeatilAdapter(getContext(), list.get(4).getVdata()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        MyApplication.position = position;
                        MyApplication.videotypeid = list.get(4).getCid();
                        MyApplication.videotype = type;
                        MyApplication.videoitemid = list.get(4).getVdata().get(position).getVid();
                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, prodetaillist.getA4().getChoice_kc().get(position).getMv_url());
                        getContext().startActivity(intent);
                    }
                });
                break;
        }
    }
    List<ScheduleBean.InfoBean.KcDataBean> list;
    ProDeatailBean.CiBean prodetaillist;
    void getdata(String id) {
        Gson gson = new Gson();
        prodetaillist = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
        ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
        MyApplication.txId=tx.getId();
        MyApplication.videotitle=tx.getStitle();
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getActivity()).STUDYDETAIL(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                String json2=json.replace("\"vdetail\":[]","\"vdetail\":{}");
                list = gson.fromJson(json2, ScheduleBean.class).getInfo().getKc_data();
                initView(list);
            }
        });
    }
}
