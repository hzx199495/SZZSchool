package com.shizhanzhe.szzschool.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.Bean.TGsqlBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.youth.banner.Banner;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
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
    DbManager manager = DatabaseOpenHelper.getInstance();
    List<ProBean.TxBean> rm;
    List<ProBean.TgBean> tg;
    View rootview;
    ProgressDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMessage("正在加载...Loading");
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
        dialog.show();
        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2,R.color.red,R.color.green_color,R.color.dimgray);
        ArrayList<String> images = new ArrayList<>();
        images.add("http://m.shizhanzhe.com/style/images/banner.jpg");
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                dialog.show();
                getData();
                swip.setRefreshing(false);
            }
        }, 3000);
    }

    public void getData() {

        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CENTER(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                rm = gson.fromJson(json, ProBean.class).getTx();
                Log.i("_____",rm.get(0).getStitle());
                gvAdapter = new GVAdapter(rm, getContext());
                gv_rm.setAdapter(gvAdapter);

                for (int i = 0; i < rm.size(); i++) {
                    try {
                        manager.save(new SearchBean(rm.get(i).getId(), rm.get(i).getThumb(), rm.get(i).getStitle(), rm.get(i).getIntroduce(), rm.get(i).getNowprice(), rm.get(i).getCatid()));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                tg = gson.fromJson(json, ProBean.class).getTg();
                tgAdapter = new TGAdapter(tg, getContext());
                gv_tg.setAdapter(tgAdapter);
//                for (int j = 0; j < tg.size(); j++) {
//                    try {
//                        List<SearchBean> proid = manager.selector(SearchBean.class).where("proid", "=", tg.get(j).getTxid()).findAll();
//                        manager.save(new TGsqlBean(tg.get(j).getId(), proid.get(0).getProid(), proid.get(0).getImg(), proid.get(0).getTitle(), proid.get(0).getIntro(), tg.get(j).getKaikedata(), proid.get(0).getPrice(), tg.get(j).getPtmoney()));
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
//                }
                dialog.dismiss();
            }
        });

        gv_rm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                String title = rm.get(position).getStitle();
                String img = rm.get(position).getThumb();
                String intro = rm.get(position).getIntroduce();
                String proid = rm.get(position).getId();
                String price = rm.get(position).getNowprice();
                intent.putExtra("id", proid);
                intent.putExtra("img", img);
                intent.putExtra("title", title);
                intent.putExtra("intro", intro);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            rootview.invalidate();
//        }
//    }
}
