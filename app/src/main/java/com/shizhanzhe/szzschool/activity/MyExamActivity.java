package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
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
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.swipe_container)
    RefreshLayout swipeLayout;
    @ViewInject(R.id.state_layout)
    StateLayout state_layout;
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
        state_layout.showLoadingView();
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
    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    private MyExamAdapter adapter;
    private List<MyExamBean> list;
    void getData() {
        OkHttpDownloadJsonUtil.downloadJson(MyExamActivity.this, new Path(MyExamActivity.this).MYExam(page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

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
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    list = gson.fromJson(json, new TypeToken<List<MyExamBean>>() {
                    }.getType());

                    if (list.size() > 0) {
                        adapter = new MyExamAdapter(MyExamActivity.this, list);
                        lv.setAdapter(adapter);
                        state_layout.showContentView();
                    } else {
                        state_layout.showEmptyView();
                    }
                }catch (Exception e){
                    state_layout.showErrorView();
                }

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
                        if (lists.size()==0){
                            Toast.makeText(MyExamActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
                        }else {
                            for (MyExamBean b :
                                    lists) {
                                list.add(b);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, 2000);
    }

}
