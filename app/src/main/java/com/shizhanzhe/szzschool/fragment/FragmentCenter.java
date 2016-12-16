package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.SearchBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.youth.banner.Banner;


import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
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
@ViewInject(R.id.gridview_tj)
    MyGridView gv_tj;
@ViewInject(R.id.banner)
    Banner banner;
@ViewInject(R.id.center_swip)
    SwipeRefreshLayout swip;
    ImageOptions imageOptions;
    GVAdapter gvAdapter;
    DbManager manager = DatabaseOpenHelper.getInstance();
    private ArrayList<ProBean> gvlist;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> images = new ArrayList<>();
        DbManager manager = DatabaseOpenHelper.getInstance();
        try {
            manager.delete(SearchBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        images.add("http://2.huobox.com/var/upload/image/2016/10/20161008151033_67442.jpg");
        images.add("http://2.huobox.com/var/upload/image/2016/10/20161008151116_98799.jpg");
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        getData();
        swip.setColorSchemeColors(R.color.red,R.color.green,R.color.blue2,R.color.white);
        swip.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        gvAdapter.GVAdapterClear();
        getData();
        swip.setRefreshing(false);
        Toast.makeText(getContext(),"刷新完成",Toast.LENGTH_SHORT).show();
    }
    public void getData(){
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CENTER(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                gvlist = gson.fromJson(json, new TypeToken<List<ProBean>>() {
                }.getType());
                gvAdapter = new GVAdapter(gvlist,getActivity());
                gv_rm.setAdapter(gvAdapter);
                for (int i=0;i<gvlist.size();i++){
                    try {
                        manager.save(new SearchBean(gvlist.get(i).getId(),gvlist.get(i).getThumb(),gvlist.get(i).getStitle(),gvlist.get(i).getIntroduce(),gvlist.get(i).getCatid()));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gv_rm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                String title=gvlist.get(position).getStitle();
                String img=gvlist.get(position).getThumb();
                String intro=gvlist.get(position).getIntroduce();
                String proid = gvlist.get(position).getId();
                String price=gvlist.get(position).getNowprice();
                intent.putExtra("id",proid);
                intent.putExtra("img",img);
                intent.putExtra("title",title);
                intent.putExtra("intro",intro);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
    }
}
