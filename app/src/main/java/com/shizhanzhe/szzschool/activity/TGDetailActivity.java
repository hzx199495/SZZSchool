package com.shizhanzhe.szzschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
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
 * 团购详情
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
    private String title;
    private String yjprice;
    private String id;
    private String tuanid;
    private String img;
    private String tgprice;
    private String time;
    private String intro;
    private  String ktprice;
    private String txid;
    private String kfm;
    private int type;
    private String uid;
    private String token;
    private int ct,kt;
    private StateLayout state_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        state_layout= (StateLayout)findViewById(R.id.state_layout);
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
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
         uid = preferences.getString("uid", "");
         token = preferences.getString("token", "");
        Intent intent = getIntent();
        tuanid = intent.getStringExtra("tuanid");
        ct = intent.getIntExtra("ct",0);
        kt = intent.getIntExtra("kt",0);

        type = intent.getIntExtra("type", 0);
        getData();
        back.setOnClickListener(this);
        tg.setOnClickListener(this);
        buy.setOnClickListener(this);
    }
    void getData(){
        OkHttpDownloadJsonUtil.downloadJson(this, Path.TGDETAIL(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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

                }catch (Exception e){
                    state_layout.showErrorView();
                }
            }
        });
    }
    void initView() {
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        if (type == 1) {
            if (kt==1){
                tg.setText("已开团");
                tg.setEnabled(false);
            }else{
                tg.setText("立即开团");
            }

            TGOpenFragment tgOpenFragment = TGOpenFragment.newInstance(title, img, time, intro, ktprice, tgprice, kfm,tuanid);
            transaction.add(R.id.ll, tgOpenFragment);
            transaction.commit();
            state_layout.showContentView();

        } else if (type == 2) {
            if (ct==1){
                tg.setText("已参团");
                tg.setEnabled(false);
            }else{
                tg.setText("立即参团");
            }
            TGJoinFragment tgJoinFragment = TGJoinFragment.newInstance(title, img, time, tgprice, intro);
            transaction.add(R.id.ll, tgJoinFragment);
            transaction.commit();
            state_layout.showContentView();
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
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tg:
                if (type == 1) {
                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), new Path(this).TGKT(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {
                            try {

                            if (json.contains("0")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("没有开团权限");
                            } else if (json.contains("1")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("开团成功");
                                tg.setText("已开团");
                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                TGOpenFragment tgOpenFragment = TGOpenFragment.newInstance(title, img, time, intro, ktprice, tgprice, kfm,tuanid);
                                transaction.add(R.id.ll, tgOpenFragment);
                                transaction.commit();
                            } else if (json.contains("2")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("数据库操作失败");
                            } else if (json.contains("3")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("余额不足，请充值");
                            } else if (json.contains("4")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("已经开团");
                            } else if (json.contains("5")) {
                                new SVProgressHUD(TGDetailActivity.this).showInfoWithStatus("无此团购");
                            }
                            }catch (Exception e){
                                Toast.makeText(TGDetailActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
        getData();
    }
}
