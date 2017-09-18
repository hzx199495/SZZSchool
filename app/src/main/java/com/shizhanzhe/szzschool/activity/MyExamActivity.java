package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyExamBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MyExamAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */
@ContentView(R.layout.activity_myexam)
public class MyExamActivity extends Activity implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.lv)
    ListView lv;
    @ViewInject(R.id.nodata)
    ImageView nodata;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.swipe_container)
    RefreshLayout swipeLayout;
    private SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
        setListener();
        getData();
    }
    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    MyExamAdapter adapter;
    List<MyExamBean> list;
    void getData() {
        svProgressHUD = new SVProgressHUD(this);
        svProgressHUD.showWithStatus("正在加载...");
        OkHttpDownloadJsonUtil.downloadJson(MyExamActivity.this, new Path(MyExamActivity.this).MYExam(page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    list = gson.fromJson(json, new TypeToken<List<MyExamBean>>() {
                    }.getType());

                    if (list.size() > 0) {
                        nodata.setVisibility(View.GONE);
                        adapter = new MyExamAdapter(MyExamActivity.this, list);
                        lv.setAdapter(adapter);
                    } else {
                        nodata.setVisibility(View.VISIBLE);
                    }
                    svProgressHUD.dismiss();
                }catch (Exception e){}

            }
        });
    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                page = 1;
                getData();
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }
    int page=1;
    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setLoading(false);
                page++;
                OkHttpDownloadJsonUtil.downloadJson(MyExamActivity.this, new Path(MyExamActivity.this).MYExam(page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd")
                                .create();
                        List<MyExamBean> lists = gson.fromJson(json, new TypeToken<List<MyExamBean>>() {
                        }.getType());
                        for (MyExamBean b :
                                lists) {
                            list.add(b);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 2000);
    }

}
