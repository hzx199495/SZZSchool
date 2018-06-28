package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
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
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.iv)
    ImageView iv;
    private QMUITipDialog dialog;
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
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
        lv.setEmptyView(iv);
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
        setListener();
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
        getData();
//        state_layout.setTipText(StateLayout.EMPTY, "");
//        state_layout.showLoadingView();
//        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
//            @Override
//            public void refreshClick() {
//                state_layout.showLoadingView();
//                getData();
//            }
//
//            @Override
//            public void loginClick() {
//
//            }
//        });
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
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(MyExamActivity.this, new Path(MyExamActivity.this).MYExam(page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
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
                    } else {
                        empty.show("","暂无数据");
                    }
                    dialog.dismiss();
                }catch (Exception e){
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(MyExamActivity.this, ExamActivity.class);
                        intent.putExtra("videoId", list.get(position).getCoid());
                        intent.putExtra("txId", list.get(position).getSid());
                        startActivity(intent);
                    }
                });
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
