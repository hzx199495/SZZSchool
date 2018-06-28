package com.shizhanzhe.szzschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
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
//    @ViewInject(R.id.gobuy)
//    TextView gobuy;
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

    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    private QMUITipDialog dialog;
    private QMUITipDialog ktdialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ktdialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
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
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.TGDETAIL(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                    Gson gson = new Gson();
                    List<TGDetailBean> bean = gson.fromJson(json, new TypeToken<List<TGDetailBean>>() {
                    }.getType());
                    title = bean.get(0).getTitle();
                    time = bean.get(0).getKaikedata();
                    img = bean.get(0).getThumb();
                    ktprice = bean.get(0).getPrice();
                    tgprice = bean.get(0).getPtmoney();
                    txid = bean.get(0).getTx().getId();
//                    yjprice= bean.get(0).getNowprice();
//                    gobuy.setText("￥" + yjprice);
                    kfm = bean.get(0).getKfm();
                    intro = bean.get(0).getTx().getIntroduce();
                    initView();
                }catch (Exception e){
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
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
            dialog.dismiss();

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
            dialog.dismiss();
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
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(4).setTipWord("没有开团权限").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            } else if (json.contains("1")) {
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(2).setTipWord("开团成功").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                                tg.setText("已开团");
                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                TGOpenFragment tgOpenFragment = TGOpenFragment.newInstance(title, img, time, intro, ktprice, tgprice, kfm,tuanid);
                                transaction.add(R.id.ll, tgOpenFragment);
                                transaction.commit();
                            } else if (json.contains("2")) {
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(4).setTipWord("数据库操作失败").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            } else if (json.contains("3")) {
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(4).setTipWord("余额不足，请充值").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            } else if (json.contains("4")) {
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(4).setTipWord("已经开团").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            } else if (json.contains("5")) {
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(4).setTipWord("无此团购").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
                            }
                            }catch (Exception e){
                                ktdialog = new QMUITipDialog.Builder(TGDetailActivity.this).setIconType(3).setTipWord("数据异常").create();
                                ktdialog.show();
                                mhandler.sendEmptyMessageDelayed(1,1500);
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getData();
//    }
}
