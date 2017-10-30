package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.ScheduleAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.youth.banner.listener.OnBannerClickListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 * 进度分类
 */
@ContentView(R.layout.activity_schedule)
public class ScheduleActivity extends Activity {
    @ViewInject(R.id.schedule_lv)
    ListView lv;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.state_layout)
    StateLayout state_layout;
    private List<ProBean.TxBean> list;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        svProgressHUD = new SVProgressHUD(this);
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
        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                svProgressHUD.showWithStatus("正在加载...");
                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                final String vip = preferences.getString("vip", "");
                OkHttpDownloadJsonUtil.downloadJson(ScheduleActivity.this, new Path(ScheduleActivity.this).SECOND(list.get(position).getId()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            Gson gson = new Gson();
                            ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                            String isbuy = tx.getIsbuy();
                            if (vip.equals("1") || isbuy.equals("1")) {
                                Intent intent = new Intent(ScheduleActivity.this, ScheduleDeatilActivity.class);
                                intent.putExtra("name", arrayList.get(position));
                                intent.putExtra("id", list.get(position).getId());
                                intent.putExtra("json", json);
                                startActivity(intent);
                            } else {
                                new SVProgressHUD(ScheduleActivity.this).showInfoWithStatus("未购买该体系");
                            }
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private SVProgressHUD svProgressHUD;

    public void getData() {

        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            if (json.equals("0")) {
                                state_layout.showNoNetworkView();
                                return;
                            } else if (json.equals("1")) {
                                state_layout.showTimeoutView();
                                return;
                            }
                            Gson gson = new Gson();
                            list = gson.fromJson(json, ProBean.class).getTx();
                            arrayList = new ArrayList<>();
                            for (ProBean.TxBean bean : list
                                    ) {
                                arrayList.add(bean.getStitle());
                            }
                            if (arrayList.size() == 0) {
                                state_layout.showEmptyView();
                            } else {
                                lv.setAdapter(new ScheduleAdapter(ScheduleActivity.this, arrayList));
                                state_layout.showContentView();
                            }
                        } catch (Exception e) {
                            state_layout.showErrorView();
                        }
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        svProgressHUD.dismiss();
    }
}
