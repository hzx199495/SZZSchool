package com.shizhanzhe.szzschool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragment_center)
public class FragmentCenter extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gridview_rm)
    MyGridView gv_rm;
    @ViewInject(R.id.gridview_tg)
    MyGridView gv_tg;
    @ViewInject(R.id.banner)
    Banner banner;
    @ViewInject(R.id.center_swip)
    SwipeRefreshLayout swip;
    GVAdapter gvAdapter;
    TGAdapter tgAdapter;
    List<ProBean.TxBean> rm;
    List<ProBean.TgBean> tg;
    View rootview;
    SVProgressHUD mSVProgressHUD;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("加载中...");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = x.view().inject(this, inflater, null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSVProgressHUD.show();

        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2,R.color.red,R.color.green_color,R.color.dimgray);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mSVProgressHUD.showWithStatus("加载中...");
                getData();
                swip.setRefreshing(false);
            }
        }, 3000);
    }

    public void getData() {
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                Log.e("________",json);
                Gson gson = new Gson();
                rm = gson.fromJson(json, ProBean.class).getTx();
                tg = gson.fromJson(json, ProBean.class).getTg();
                Log.e("________size",rm.get(0).getStitle()+"");
                gvAdapter = new GVAdapter(rm, getContext());
                gv_rm.setAdapter(gvAdapter);
                SharedPreferences preferences =getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
                String ktagent = preferences.getString("ktagent", "");
                tgAdapter = new TGAdapter(null,tg,getContext(),ktagent);
                gv_tg.setAdapter(tgAdapter);
                ArrayList<String> images = new ArrayList<>();
                images.add(Path.IMG(rm.get(0).getThumb()));
                images.add(Path.IMG(rm.get(1).getThumb()));
                banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                banner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = rm.get(position-1).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);
                    }
                });
                mSVProgressHUD.dismiss();
            }
        });

        gv_rm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                String proid = rm.get(position).getId();
                intent.putExtra("id", proid);
                startActivity(intent);
            }
        });

    }
}
