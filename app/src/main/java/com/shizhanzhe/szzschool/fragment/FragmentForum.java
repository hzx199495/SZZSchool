package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ForumBKActivity;
import com.shizhanzhe.szzschool.activity.ForumItemActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.adapter.ForumBKAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragmetn_forum)
public class FragmentForum extends Fragment {
    @ViewInject(R.id.gv_bk)
    MyGridView bk;
    @ViewInject(R.id.gv_forum)
    MyGridView gv;
    List<ForumBean.LtmodelBean> ltmodel;
    List<ForumBean.SzanBean> szan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getdata();
        bk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fid = ltmodel.get(position).getFid();
                String name = ltmodel.get(position).getName();
                Intent intent = new Intent();
                intent.setClass(getActivity(),ForumBKActivity.class);
                intent.putExtra("fid",fid);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = szan.get(position).getSubject();
                String name = szan.get(position).getRealname();
                long time = szan.get(position).getDateline();
                String pid = szan.get(position).getPid();
                String fid = szan.get(position).getFid();
                OkHttpDownloadJsonUtil.downloadJson(getContext(), "http://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + MyApplication.myid + "&pid=" + pid + "+&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {

                    }
                });
                Intent intent = new Intent(getActivity(), ForumItemActivity.class);
                intent.putExtra("pid",pid);
                intent.putExtra("title",title);
                intent.putExtra("name",name);
                intent.putExtra("time",time);
                intent.putExtra("fid",fid);
                startActivity(intent);
            }
        });
    }
    private void getdata() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                ltmodel = gson.fromJson(json, ForumBean.class).getLtmodel();
                szan = gson.fromJson(json, ForumBean.class).getSzan();
                ForumBKAdapter bkadapter = new ForumBKAdapter(getContext(), ltmodel);
                ForumLVAdapter lvadapter = new ForumLVAdapter(getContext(), szan);
                bk.setAdapter(bkadapter);
                gv.setAdapter(lvadapter);
            }
        });
    }
}
