package com.shizhanzhe.szzschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.TGDetailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.TGJoinFragment;
import com.shizhanzhe.szzschool.fragment.TGOpenFragment;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/3/10.
 */
@ContentView(R.layout.activity_tg)
public class TGDetailActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.tg)
    TextView tg;
    @ViewInject(R.id.buy)
    LinearLayout buy;
    @ViewInject(R.id.gobuy)
    TextView gobuy;
    String title;
    String yjprice;
    String id;
    String tuanid;
    String img;
    String tgprice;
    String time;
    String intro;
    String ktprice;
    String txid;
    String kfm;
    int type;
    String uid;
    String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
         uid = preferences.getString("uid", "");
         token = preferences.getString("token", "");
        Intent intent = getIntent();
        tuanid = intent.getStringExtra("tuanid");
        MyApplication.tuanid = tuanid;
        type = intent.getIntExtra("type", 0);
        OkHttpDownloadJsonUtil.downloadJson(this, Path.TGDETAIL(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<TGDetailBean> bean = gson.fromJson(json, new TypeToken<List<TGDetailBean>>() {
                }.getType());
                title = bean.get(0).getTitle();
                time = bean.get(0).getKaikedata();
                img = bean.get(0).getThumb();
                ktprice = bean.get(0).getPrice();
                tgprice = bean.get(0).getPtmoney();
                txid = bean.get(0).getTx().getId();
                yjprice = bean.get(0).getNowprice();
                gobuy.setText("￥" + yjprice);
                kfm = bean.get(0).getKfm();
                intro = bean.get(0).getTx().getIntroduce();
                initView();
            }
        });

        back.setOnClickListener(this);
        tg.setOnClickListener(this);
        buy.setOnClickListener(this);
    }

    void initView() {
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        if (type == 1) {
            tg.setText("立即开团");
            TGOpenFragment tgOpenFragment = TGOpenFragment.newInstance(title, img, time, intro, ktprice, tgprice, kfm);
            transaction.add(R.id.ll, tgOpenFragment);
            transaction.commit();


        } else if (type == 2) {
            tg.setText("立即参团");

            TGJoinFragment tgJoinFragment = TGJoinFragment.newInstance(title, img, time, tgprice, intro);
            transaction.add(R.id.ll, tgJoinFragment);
            transaction.commit();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy:
                Intent intent = new Intent();
                intent.setClass(TGDetailActivity.this, DetailActivity.class);
                intent.putExtra("id", txid);
                startActivity(intent);
            case R.id.back:
                finish();
                break;
            case R.id.tg:
                if (type == 1) {
                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "https://shizhanzhe.com/index.php?m=pcdata.kaituan&pc=1&tid=" + tuanid + "&uid=" + uid + "&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {

                            if (json.contains("0")) {
                                Toast.makeText(getApplicationContext(), "没有开团权限", Toast.LENGTH_SHORT).show();
                            } else if (json.contains("1")) {
                                Toast.makeText(getApplicationContext(), "开团成功", Toast.LENGTH_SHORT).show();
                                initView();
                            } else if (json.contains("2")) {
                                Toast.makeText(getApplicationContext(), "数据库操作失败", Toast.LENGTH_SHORT).show();
                            } else if (json.contains("3")) {
                                Toast.makeText(getApplicationContext(), "余额不足，请充值", Toast.LENGTH_SHORT).show();
                            } else if (json.contains("4")) {
                                Toast.makeText(getApplicationContext(), "已经开团", Toast.LENGTH_SHORT).show();
                            } else if (json.contains("5")) {
                                Toast.makeText(getApplicationContext(), "无此团购", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else if (type == 2) {
                    Intent intent2 = new Intent();
                    intent2.setClass(getApplicationContext(), KTListActivity.class);
                    intent2.putExtra("tuanid", tuanid);
                    intent2.putExtra("title", title);
                    intent2.putExtra("img", img);
                    intent2.putExtra("ctprice", tgprice);
                    startActivity(intent2);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.TGDETAIL(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<TGDetailBean> bean = gson.fromJson(json, new TypeToken<List<TGDetailBean>>() {
                }.getType());
                title = bean.get(0).getTitle();
                time = bean.get(0).getKaikedata();
                img = bean.get(0).getThumb();
                ktprice = bean.get(0).getPrice();
                tgprice = bean.get(0).getPtmoney();
                txid = bean.get(0).getTx().getId();
                yjprice = bean.get(0).getNowprice();
                gobuy.setText("￥" + yjprice);
                kfm = bean.get(0).getKfm();
                intro = bean.get(0).getTx().getIntroduce();
                initView();
            }
        });
    }
}
