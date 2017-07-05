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

import com.google.gson.Gson;
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

import java.util.List;

/**
 * Created by zz9527 on 2017/6/12.
 */
@ContentView(R.layout.fragment_kc)
public class KCFragment extends Fragment {
    @ViewInject(R.id.gv)
    GridView gv;
    public static KCFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        KCFragment fragment = new KCFragment();
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
        Bundle bundle = getArguments();
        final int type = bundle.getInt("type");
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CENTER(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                final List<ProBean.TxBean> tx = gson.fromJson(json, ProBean.class).getTx();
                List<ProBean.TgBean> tg = gson.fromJson(json, ProBean.class).getTg();
                if (type==0){
                   gv.setAdapter(new TGAdapter(tg,getContext()));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }else if (type==1){
                    gv.setNumColumns(2);
                    gv.setAdapter(new GVAdapter(tx, getContext()));
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = tx.get(position).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
