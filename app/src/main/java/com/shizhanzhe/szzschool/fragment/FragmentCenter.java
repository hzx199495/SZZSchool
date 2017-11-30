package com.shizhanzhe.szzschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BannerBean;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;
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
    @ViewInject(R.id.gridview_wlyx)
    MyGridView gv_wlyx;
    @ViewInject(R.id.gridview_zyts)
    MyGridView gv_zyts;
    @ViewInject(R.id.gridview_tg)
    MyGridView gv_tg;
    @ViewInject(R.id.banner)
    Banner banner;
    @ViewInject(R.id.center_swip)
    VpSwipeRefreshLayout swip;
    @ViewInject(R.id.state_layout)
    StateLayout state_layout;
    private List<ProBean.TxBean> list;
    private List<ProBean.TgBean> tg;
    private View rootview;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = x.view().inject(this, inflater, null);
        return rootview;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        state_layout.showLoadingView();
        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                state_layout.showLoadingView();
                getData();
            }

            @Override
            public void loginClick() {

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                swip.setRefreshing(false);
            }
        }, 3000);
    }

    List<ProBean.TxBean> yx;
    List<ProBean.TxBean> ts;

    public void getData() {

        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")){
                        state_layout.showNoNetworkView();
                        return;
                    }else if (json.equals("1")){
                        state_layout.showTimeoutView();
                        return;
                    }
                    Gson gson = new Gson();
                    list = gson.fromJson(json, ProBean.class).getTx();
                    tg = gson.fromJson(json, ProBean.class).getTg();
                    yx = new ArrayList<>();
                    ts = new ArrayList<>();
                    for (ProBean.TxBean bean : list
                            ) {
                        if (bean.getCatid().equals("41")) {
                            yx.add(bean);
                        } else if (bean.getCatid().equals("42")) {
                            ts.add(bean);
                        }
                    }

                    GVAdapter yxAdapter = new GVAdapter(yx, getContext());
                    GVAdapter tsAdapter = new GVAdapter(ts, getContext());
                    gv_wlyx.setAdapter(yxAdapter);
                    gv_zyts.setAdapter(tsAdapter);
                    SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String ktagent = preferences.getString("ktagent", "");
                    TGAdapter tgAdapter = new TGAdapter(null, tg, getContext(), ktagent);
                    gv_tg.setAdapter(tgAdapter);
                    setBanner();
                    state_layout.showContentView();
//                    mSVProgressHUD.dismiss();
                } catch (Exception e) {
                    state_layout.showErrorView();
                }


            }
        });

        gv_wlyx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (yx.get(position).getStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    String proid = yx.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                } else if (yx.get(position).getStatus().equals("1")) {
                    Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gv_zyts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (ts.get(position).getStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    String proid = ts.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                } else if (ts.get(position).getStatus().equals("1")) {
                    Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    void setBanner() {
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), "https://shizhanzhe.com/index.php?m=pcdata.haibao1", new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {


                    Gson gson = new Gson();
                    final List<BannerBean> bean = gson.fromJson(json, new TypeToken<List<BannerBean>>() {
                    }.getType());
                    ArrayList<String> images = new ArrayList<>();
                    for (BannerBean b : bean
                            ) {
                        images.add(b.getImg());
                    }
                    banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), DetailActivity.class);
                            String proid = bean.get(position - 1).getId() + "";
                            intent.putExtra("id", proid);
                            startActivity(intent);
                        }

                    });
                } catch (Exception e) {

                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
