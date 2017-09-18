package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.Bean.NoteBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.CollectAdapter;
import com.shizhanzhe.szzschool.adapter.MyProAdapter;
import com.shizhanzhe.szzschool.adapter.NoteAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;
import com.tencent.tinker.android.dex.Code;

import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */

public class ProFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private GridView gv;

    public static ProFragment newInstance(int type) {
        ProFragment fragment = new ProFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout_pro, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv = (GridView) view.findViewById(R.id.gv);
        initView();
    }

    int page = 1;

    protected void initView() {

        switch (type) {
            case 1:
                gv.setNumColumns(2);
                OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYCLASS(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                    @Override
                    public void onsendJson(String json) {
                        try {
                        Gson gson = new Gson();
                        final List<MyProBean> list = gson.fromJson(json, new TypeToken<List<MyProBean>>() {
                        }.getType());
                        MyProAdapter myProAdapter = new MyProAdapter(list, getContext());
                        if (list.size() > 0) {
                            gv.setAdapter(myProAdapter);
                            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), DetailActivity.class);
                                    String proid = list.get(position).getSysinfo().get(0).getId();
                                    intent.putExtra("id", proid);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            gv.setVisibility(View.GONE);
                        }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 2:
                gv.setNumColumns(2);
                OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).COLLECTLIST(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


                    @Override
                    public void onsendJson(String json) {
                        try {


                            Gson gson = new Gson();
                            final List<CollectListBean> list = gson.fromJson(json, new TypeToken<List<CollectListBean>>() {
                            }.getType());
                            if (list.size() > 0) {
                                CollectAdapter adapter = new CollectAdapter(getContext(), list);
                                gv.setAdapter(adapter);
                                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.setClass(getContext(), DetailActivity.class);
                                        String proid = list.get(position).getSysinfo().get(0).getId();
                                        intent.putExtra("id", proid);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                gv.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 3:
                gv.setNumColumns(1);
                OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).NOTELIST("", page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


                    @Override
                    public void onsendJson(String json) {
                        try {
                        Gson gson = new Gson();
                        final List<NoteBean> list = gson.fromJson(json, new TypeToken<List<NoteBean>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            NoteAdapter adapter = new NoteAdapter(getContext(), list, 1);
                            gv.setAdapter(adapter);
                        } else {
                            gv.setVisibility(View.GONE);
                        }
                        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SECOND(list.get(position).getSid()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                    @Override
                                    public void onsendJson(String json) {
                                        MyApplication.videojson = json;
                                        MyApplication.txId=list.get(position).getSid();
                                        MyApplication.videotypeid=list.get(position).getPid();
                                        MyApplication.videoitemid=list.get(position).getCoid();
                                        Gson gson = new Gson();
                                        ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
                                        MyApplication.proimg=tx.getThumb();
                                        MyApplication.videotitle = tx.getStitle();
                                        List<ProDeatailBean.CiBean> bean = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
                                        for (ProDeatailBean.CiBean b:bean){
                                            ;
                                            if (b.getId().contains(list.get(position).getPid())){
                                                MyApplication.videotype=1;

                                                for (ProDeatailBean.CiBean.ChoiceKcBean c :
                                                        b.getChoice_kc()) {
                                                    if (c.getId().contains(list.get(position).getCoid())){
                                                        Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, c.getMv_url());
                                                        startActivity(intent);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });
                            }
                        });
                        }catch (Exception e){
                            Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }


    }
}
