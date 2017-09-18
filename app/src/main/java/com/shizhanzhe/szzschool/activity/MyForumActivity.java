package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.adapter.ScheduleAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/9/15.
 */
@ContentView(R.layout.activity_myforum)
public class MyForumActivity extends Activity {
    @ViewInject(R.id.myforum_lv)
    ListView lv;
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
        final SVProgressHUD svProgressHUD = new SVProgressHUD(this);
        svProgressHUD.showWithStatus("正在加载...");
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    final List<ForumBean.LtmodelBean> list = gson.fromJson(json, ForumBean.class).getLtmodel();
                    final ArrayList<String> arrayList = new ArrayList<>();
                    for (ForumBean.LtmodelBean bean : list
                            ) {
                        arrayList.add(bean.getName());
                    }
                    lv.setAdapter(new ScheduleAdapter(MyForumActivity.this, arrayList));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MyForumActivity.this, MyForumDetailActivity.class);
                            intent.putExtra("title",list.get(position).getName());
                            intent.putExtra("fid",list.get(position).getFid());
                            startActivity(intent);
                        }
                    });
                    svProgressHUD.dismiss();
                }catch (Exception e){

                }

            }
        });
    }
}
