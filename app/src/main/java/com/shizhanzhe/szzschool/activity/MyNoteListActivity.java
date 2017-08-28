package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.Bean.NoteBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.NoteAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/7/27.
 */
@ContentView(R.layout.activity_mynote)
public class MyNoteListActivity extends Activity {
    @ViewInject(R.id.mynote)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    int page=1;
    List<NoteBean> list;
    NoteAdapter adapter;
    RefreshLayout swipeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        init();
//        setListener();
        getData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                OkHttpDownloadJsonUtil.downloadJson(MyNoteListActivity.this, new Path(MyNoteListActivity.this).SECOND(list.get(position).getSid()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        MyApplication.videojson = json;
                        MyApplication.txId=list.get(position).getSid();
                        MyApplication.videotypeid=list.get(position).getPid();
                        MyApplication.videoitemid=list.get(position).getCoid();
                        Gson gson = new Gson();
                        ProDeatailBean.TxBean tx = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getTx();
                        MyApplication.proimg=tx.getThumb();
                        ProDeatailBean.CiBean bean = gson.fromJson(MyApplication.videojson, ProDeatailBean.class).getCi();
                        if (bean.getA0().getId().equals(list.get(position).getPid())){
                            MyApplication.videotype=1;
                            for (ProDeatailBean.CiBean.A0Bean.ChoiceKcBean b :
                                    bean.getA0().getChoice_kc()) {
                                if (b.getId().equals(list.get(position).getCoid())){
                                    Intent intent = PolyvPlayerActivity.newIntent(MyNoteListActivity.this, PolyvPlayerActivity.PlayMode.portrait, b.getMv_url());
                                    startActivity(intent);
                                }
                            }

                        }else if (bean.getA1().getId().equals(list.get(position).getPid())){
                            MyApplication.videotype=2;
                            for (ProDeatailBean.CiBean.A1Bean.ChoiceKcBeanX b :
                                    bean.getA1().getChoice_kc()) {
                                if (b.getId().equals(list.get(position).getCoid())){
                                    Intent intent = PolyvPlayerActivity.newIntent(MyNoteListActivity.this, PolyvPlayerActivity.PlayMode.portrait, b.getMv_url());
                                    startActivity(intent);
                                }
                            }
                        }else if (bean.getA2().getId().equals(list.get(position).getPid())){
                            MyApplication.videotype=3;
                            for (ProDeatailBean.CiBean.A2Bean.ChoiceKcBeanXX b :
                                    bean.getA2().getChoice_kc()) {
                                if (b.getId().equals(list.get(position).getCoid())){
                                    Intent intent = PolyvPlayerActivity.newIntent(MyNoteListActivity.this, PolyvPlayerActivity.PlayMode.portrait, b.getMv_url());
                                    startActivity(intent);
                                }
                            }
                        }else if (bean.getA0().getId().equals(list.get(position).getPid())){
                            MyApplication.videotype=4;
                            for (ProDeatailBean.CiBean.A3Bean.ChoiceKcBeanXXX b :
                                    bean.getA3().getChoice_kc()) {
                                if (b.getId().equals(list.get(position).getCoid())){
                                    Intent intent = PolyvPlayerActivity.newIntent(MyNoteListActivity.this, PolyvPlayerActivity.PlayMode.portrait, b.getMv_url());
                                    startActivity(intent);
                                }
                            }
                        }else {
                            MyApplication.videotype=5;
                            for (ProDeatailBean.CiBean.A4Bean.ChoiceKcBeanXXXX b :
                                    bean.getA4().getChoice_kc()) {
                                if (b.getId().equals(list.get(position).getCoid())){
                                    Intent intent = PolyvPlayerActivity.newIntent(MyNoteListActivity.this, PolyvPlayerActivity.PlayMode.portrait, b.getMv_url());
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });
            }
        });
    }
    void getData(){
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).NOTELIST("", page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<NoteBean>>() {
                }.getType());
                if (list!=null&&list.size()>0) {
                    adapter = new NoteAdapter(MyNoteListActivity.this, list,1);
                    lv.setAdapter(adapter);
                }else {
                    lv.setVisibility(View.GONE);
                }
            }
        });
    }
//    /**
//     * 初始化布局
//     */
//    @SuppressLint({"InlinedApi", "InflateParams"})
//    private void init() {
//        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
//        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
//    }
//
//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setOnLoadListener(this);
//    }
//
//    @Override
//    public void onRefresh() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                page = 1;
//                getData();
//                swipeLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onLoad() {
//        swipeLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeLayout.setLoading(false);
//                page++;
//
//                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), new Path(MyNoteListActivity.this).NOTELIST("", page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                    @Override
//                    public void onsendJson(String json) {
//                        Gson gson = new GsonBuilder()
//                                .setDateFormat("yyyy-MM-dd")
//                                .create();
//                        List<NoteBean> lists = gson.fromJson(json, new TypeToken<List<NoteBean>>() {
//                        }.getType());
//                        for (NoteBean b :
//                                lists) {
//                            list.add(b);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        }, 2000);
//    }
}
