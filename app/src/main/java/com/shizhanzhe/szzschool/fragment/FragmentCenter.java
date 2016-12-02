package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.youth.banner.Banner;


import org.xutils.common.util.DensityUtil;
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
public class FragmentCenter extends Fragment {
@ViewInject(R.id.gridview_rm)
    MyGridView gv_rm;
@ViewInject(R.id.gridview_tj)
    MyGridView gv_tj;
    @ViewInject(R.id.banner)
    Banner banner;
    ImageOptions imageOptions;
    private ArrayList<ProBean> gvlist;
    public static FragmentCenter newInstance(String uid, String token) {

        Bundle args = new Bundle();
        args.putString("uid", uid);
        args.putString("token", token);
        FragmentCenter fragment = new FragmentCenter();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final String uid = bundle.getString("uid");
        final String token = bundle.getString("token");
        ArrayList<String> images = new ArrayList<>();
        images.add("http://2.huobox.com/var/upload/image/2016/10/20161008151033_67442.jpg");
        images.add("http://2.huobox.com/var/upload/image/2016/10/20161008151116_98799.jpg");
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.CENTER(uid, token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                gvlist = gson.fromJson(json, new TypeToken<List<ProBean>>() {
                }.getType());
                gv_rm.setAdapter(new GVAdapter(gvlist,getActivity()));
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
                intent.putExtra("id",proid);
                intent.putExtra("uid",uid);
                intent.putExtra("token",token);
                intent.putExtra("img",img);
                intent.putExtra("title",title);
                intent.putExtra("intro",intro);
                startActivity(intent);
            }
        });

    }

}
