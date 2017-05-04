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
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
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
public class FragmentFl extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.fl_rg)
    RadioGroup rg;
    @ViewInject(R.id.fl_gv)
    GridView fl_gv;
    List<ProBean.TxBean> gvlist;
    List<ProBean.TgBean> tglist;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CLASSIFY(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                gvlist = gson.fromJson(json,ProBean.class).getTx();
                tglist=gson.fromJson(json,ProBean.class).getTg();
                final ArrayList<ProBean.TxBean> tx = new ArrayList<>();
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

                        String title = tx.get(position).getStitle();
                        String img = tx.get(position).getThumb();
                        String intro = tx.get(position).getIntroduce();
                        String catid = tx.get(position).getId();
                        String price = tx.get(position).getNowprice();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        intent.putExtra("price", price);
                        startActivity(intent);
                    }
                });
            }
        });
        rg.setOnCheckedChangeListener(this);

    }
    GVAdapter adapter;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rg_tx:
                final ArrayList<ProBean.TxBean> tx = new ArrayList<>();
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

                        String title = tx.get(position).getStitle();
                        String img = tx.get(position).getThumb();
                        String intro = tx.get(position).getIntroduce();
                        String catid = tx.get(position).getId();
                        String price = tx.get(position).getNowprice();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        intent.putExtra("price", price);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.rg_jj:
                final ArrayList<ProBean.TxBean> jj = new ArrayList<>();
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

                        String title = jj.get(position).getStitle();
                        String img = jj.get(position).getThumb();
                        String intro = jj.get(position).getIntroduce();
                        String catid = jj.get(position).getId();
                        String price = jj.get(position).getNowprice();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        intent.putExtra("price", price);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.rg_zy:
                final ArrayList<ProBean.TxBean> zy = new ArrayList<>();
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

                        String title = zy.get(position).getStitle();
                        String img = zy.get(position).getThumb();
                        String intro = zy.get(position).getIntroduce();
                        String catid = zy.get(position).getId();
                        String price = zy.get(position).getNowprice();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        intent.putExtra("price", price);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.rg_rm:
                final ArrayList<ProBean.TxBean> rm = new ArrayList<>();
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

                        String title = rm.get(position).getStitle();
                        String img = rm.get(position).getThumb();
                        String intro = rm.get(position).getIntroduce();
                        String catid = rm.get(position).getId();
                        String price = rm.get(position).getNowprice();
                        intent.putExtra("id", catid);
                        intent.putExtra("img", img);
                        intent.putExtra("title", title);
                        intent.putExtra("intro", intro);
                        intent.putExtra("price", price);
                        startActivity(intent);
                    }
                });
                break;

        }
    }
}

