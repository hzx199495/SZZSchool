package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
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
 * Created by hasee on 2016/12/28.
 */
@ContentView(R.layout.activity_forum)
public class ForumActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.gv_bk)
    MyGridView bk;
    @ViewInject(R.id.gv_forum)
    MyGridView gv;
    @ViewInject(R.id.put)
    TextView put;
    List<ForumBean.LtmodelBean> ltmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        getdata();
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        final String uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        bk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fid = ltmodel.get(position).getFid();
                String name = ltmodel.get(position).getName();
                Intent intent = new Intent();
                intent.setClass(ForumActivity.this,ForumBKActivity.class);
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
                String time = szan.get(position).getDateline();
                String pid = szan.get(position).getPid();
                String fid = szan.get(position).getFid();
                String logo = szan.get(position).getLogo();
                String rep = szan.get(position).getAlltip();

                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {

                    }
                });
                Intent intent = new Intent(ForumActivity.this, ForumItemActivity.class);
                intent.putExtra("pid",pid);
                intent.putExtra("title",title);
                intent.putExtra("name",name);
                intent.putExtra("img",logo);
                intent.putExtra("time",time);
                intent.putExtra("rep",rep);
                intent.putExtra("fid",fid);
                startActivity(intent);
            }
        });
        put.setOnClickListener(this);
    }
    List<ForumBean.SzanBean> szan;
    private void getdata() {
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                ltmodel = gson.fromJson(json, ForumBean.class).getLtmodel();
                szan = gson.fromJson(json, ForumBean.class).getSzan();
                ForumBKAdapter bkadapter = new ForumBKAdapter(getApplicationContext(), ltmodel);
                ForumLVAdapter lvadapter = new ForumLVAdapter(getApplicationContext(), szan);
                bk.setAdapter(bkadapter);
                gv.setAdapter(lvadapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.put:
                startActivity(new Intent(ForumActivity.this,PostActivity.class));
        }
    }
}
