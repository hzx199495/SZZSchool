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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    SVProgressHUD svProgressHUD;
    public void getData() {
        svProgressHUD = new SVProgressHUD(this);
        svProgressHUD.showWithStatus("正在加载...");
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            Gson gson = new Gson();
                            final List<ProBean.TxBean> list = gson.fromJson(json, ProBean.class).getTx();
                            final ArrayList<String> arrayList = new ArrayList<>();
                            for (ProBean.TxBean bean : list
                                    ) {
                                arrayList.add(bean.getStitle());
                            }
                            lv.setAdapter(new ScheduleAdapter(ScheduleActivity.this, arrayList));
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                    SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                                    final String vip = preferences.getString("vip", "");
                                    OkHttpDownloadJsonUtil.downloadJson(ScheduleActivity.this, new Path(ScheduleActivity.this).SECOND(list.get(position).getId()), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                        @Override
                                        public void onsendJson(String json) {
                                            MyApplication.videojson = json;
                                            Gson gson = new Gson();
                                            ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                                            String isbuy = tx.getIsbuy();
                                            if (vip.equals("1") || isbuy.equals("1")) {
                                                Intent intent = new Intent(ScheduleActivity.this, ScheduleDeatilActivity.class);
                                                intent.putExtra("name", arrayList.get(position));
                                                intent.putExtra("id", list.get(position).getId());
                                                startActivity(intent);
                                            } else {
                                                new SVProgressHUD(ScheduleActivity.this).showInfoWithStatus("未购买该体系");
                                            }
                                        }
                                    });
                                }
                            });
                            svProgressHUD.dismiss();
                        } catch (Exception e) {
                        }
                    }

                }
        );
    }
}
