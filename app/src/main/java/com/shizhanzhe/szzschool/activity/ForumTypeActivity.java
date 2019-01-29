package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2018/4/10.
 */
@ContentView(R.layout.activity_forumtype)
public class ForumTypeActivity extends Activity {
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.gv)
    GridView gv;
    private List<ForumBean.LtmodelBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String vip = preferences.getString("vip", "");
        String json = getIntent().getStringExtra("json");
        int type = getIntent().getIntExtra("type", 0);
        Gson gson = new Gson();
        List<ForumBean.LtmodelBean> ltmodel = gson.fromJson(json, ForumBean.class).getLtmodel();
        list = new ArrayList<>();
        if (type == 1) {
            title.setText("网络营销实战");
            for (int i = 0; i < 6; i++) {
                list.add(ltmodel.get(i));
            }
            gv.setAdapter(new ForumBKAdapter(this, list));
        } else if (type == 2) {
            title.setText("职业技能提升");
            for (int i = 6; i < ltmodel.size(); i++) {
                list.add(ltmodel.get(i));
            }
            gv.setAdapter(new ForumBKAdapter(this, list));
        }
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String fid = list.get(position).getFid();
                String name = list.get(position).getName();
                String txId = list.get(position).getSystemid();
                if (vip.equals("1")) {
                    Intent intent = new Intent();
                    intent.setClass(ForumTypeActivity.this, ForumBKActivity.class);
                    intent.putExtra("fid", fid);
                    intent.putExtra("name", name);
                    intent.putExtra("txId", txId);
                    startActivity(intent);
                } else {
                    if (MyApplication.isLogin) {
                        if (fid.equals("58")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForumTypeActivity.this).setTitle("无权限")
                                    .setMessage("未开通VIP，前往账户中心开通")
                                    .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ForumTypeActivity.this, UserZHActivity.class));
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(ForumTypeActivity.this, ForumBKActivity.class);
                            intent.putExtra("fid", fid);
                            intent.putExtra("name", name);
                            intent.putExtra("txId", txId);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(ForumTypeActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForumTypeActivity.this, LoginActivity.class));
                    }
                }
            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
