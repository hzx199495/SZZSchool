package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MyProAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 */
@ContentView(R.layout.activity_mypro)
public class MyProActivity extends Activity {
    @ViewInject(R.id.mypro_gv)
    GridView gv;
    @ViewInject(R.id.nokc)
    TextView nokc;
    @ViewInject(R.id.back)
    ImageView back;

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
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).MYCLASS(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                final List<MyProBean> list = gson.fromJson(json, new TypeToken<List<MyProBean>>() {
                }.getType());
                MyProAdapter myProAdapter = new MyProAdapter(list, MyProActivity.this);
                if (list.size() > 0) {
                    nokc.setVisibility(View.GONE);
                    gv.setVisibility(View.VISIBLE);
                    gv.setAdapter(myProAdapter);
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(MyProActivity.this, DetailActivity.class);
                            String proid = list.get(position).getSysinfo().get(0).getId();
                            intent.putExtra("id", proid);
                            startActivity(intent);
                        }
                    });
                } else {
                    nokc.setVisibility(View.VISIBLE);
                    gv.setVisibility(View.GONE);
                }
            }
        });
    }
}
