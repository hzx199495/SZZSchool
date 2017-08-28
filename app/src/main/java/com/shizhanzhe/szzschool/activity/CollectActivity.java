package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.CollectAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by hasee on 2016/11/28.
 * 我的收藏
 */
@ContentView(R.layout.activity_sc)
public class CollectActivity extends Activity {
    @ViewInject(R.id.lv_collect)
    GridView lv;
    @ViewInject(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

            OkHttpDownloadJsonUtil.downloadJson(CollectActivity.this, new Path(this).COLLECTLIST(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<CollectListBean> list = gson.fromJson(json, new TypeToken<List<CollectListBean>>() {
                    }.getType());
                    if (list.size()>0) {
                        CollectAdapter adapter = new CollectAdapter(getApplicationContext(), list);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.setClass(CollectActivity.this, DetailActivity.class);
                                String proid = list.get(position).getSysinfo().get(0).getId();
                                intent.putExtra("id", proid);
                                startActivity(intent);
                            }
                        });
                    }else {
                        lv.setVisibility(View.GONE);
                    }
                }
            });

        }


}

