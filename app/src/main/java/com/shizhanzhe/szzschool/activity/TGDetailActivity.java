package com.shizhanzhe.szzschool.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BuyBean;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.Bean.PersonalDataBean;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.Bean.TGDetailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.MyCTAdapter;
import com.shizhanzhe.szzschool.fragment.TGJoinFragment;
import com.shizhanzhe.szzschool.fragment.TGOpenFragment;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.json.JSONArray;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zz9527 on 2017/3/10.
 */
@ContentView(R.layout.activity_tg)
public class TGDetailActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.tg)
    TextView tg;
    @ViewInject(R.id.gobuy)
    TextView gobuy;
    @ViewInject(R.id.buy)
    LinearLayout buy;
    Dialog dialog;
    String title;
    String yjprice;
    String id;
    String tuanid;
    String state = "未开团";
    String img;
    String tgprice;
    String time;
    String intro;
    String ktprice;
    int type;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        Intent intent = getIntent();
        tuanid = intent.getStringExtra("tuanid");
        type = intent.getIntExtra("type", 0);
        Gson gson = new Gson();
        ProBean2.TxBean tx = gson.fromJson(MyApplication.tgjson, ProBean2.class).getTx();
        yjprice = tx.getNowprice();
        gobuy.setText("￥" + yjprice);
        intro = tx.getIntroduce();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.TGDETAIL(tuanid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                List<TGDetailBean> bean = gson.fromJson(json, new TypeToken<List<TGDetailBean>>(){}.getType());
                title = bean.get(0).getTitle();
                time = bean.get(0).getKaikedata();
                img = bean.get(0).getThumb();
                ktprice = bean.get(0).getPrice();
                tgprice=bean.get(0).getPtmoney();
                initView();
            }
        });
        dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(R.layout.dialog_buy);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        buy.setOnClickListener(this);
        back.setOnClickListener(this);
        tg.setOnClickListener(this);

    }

    void initView() {
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        if (type == 1) {
            OkHttpDownloadJsonUtil.downloadJson(this, "http://shizhanzhe.com/index.php?m=pcdata.mykaituan&pc=1&uid=" + MyApplication.myid + "&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    List<MyKTBean> list = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getTuanid().equals(tuanid)) {
                                state = "已开团";
                                tg.setText("已开团");
                            }else {
                                tg.setText("立即开团");
                                state = "未开团";
                            }
                        }
                    } else {
                        tg.setText("立即开团");
                        state = "未开团";
                        return;
                    }
                    tgOpenFragment = TGOpenFragment.newInstance(title, img, time, intro, state,ktprice,tgprice);
                    transaction.add(R.id.ll, tgOpenFragment);
                    transaction.commit();
                }
            });
        } else if (type == 2) {
            OkHttpDownloadJsonUtil.downloadJson(this, Path.MYCT(MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    Gson gson = new Gson();
                    final List<MyCTBean> ctlist = gson.fromJson(json, new TypeToken<List<MyCTBean>>() {
                    }.getType());
                    if (ctlist.size() > 0) {
                        for (int i = 0; i < ctlist.size(); i++) {
                            if (ctlist.get(i).getTuanid().equals(tuanid)) {
                                tg.setText("已参团");
                            }
                        }
                    } else {
                        tg.setText("立即参团");
                    }
                    tgJoinFragment = TGJoinFragment.newInstance(title, img, time, tgprice, intro);
                    transaction.add(R.id.ll, tgJoinFragment);
                    transaction.commit();
                }
            });
        }

    }

    TGJoinFragment tgJoinFragment;
    TGOpenFragment tgOpenFragment;

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.buy:
                dialog.show();
                TextView pro = (TextView) dialog.getWindow().findViewById(R.id.buy_pro);
                final TextView price = (TextView) dialog.getWindow().findViewById(R.id.buy_pr);
                Button btn = (Button) dialog.getWindow().findViewById(R.id.buy_yes);
                pro.setText(title);
                price.setText("￥" + yjprice);
                btn.setOnClickListener(this);
                break;
            case R.id.buy_yes:
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("uid", MyApplication.myid).add("systemid", id)
                        .add("type", "1").add("pid", "0").add("coid", "0").add("catid", "0")
                        .build();
                Request request = new Request.Builder()
                        .url("http://shizhanzhe.com/index.php?m=courSystem.buy&pc=1")
                        .post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("fail", e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TGDetailActivity.this, "购买失败", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String buy = response.body().string();
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    BuyBean bean = gson.fromJson(buy, BuyBean.class);
                                    if (bean.getStatus() == 1) {
                                        Toast.makeText(TGDetailActivity.this, "购买成功", Toast.LENGTH_LONG).show();
                                    } else if (bean.getStatus() == 3) {
                                        Toast.makeText(TGDetailActivity.this, "余额不足，请充值", Toast.LENGTH_LONG).show();
                                    }
                                    dialog.dismiss();
                                }

                            });
                        }

                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tg:
                if (type == 1) {
                     AlertDialog.Builder builder = new AlertDialog.Builder(TGDetailActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("确认支付￥"+ktprice+"开团？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "http://shizhanzhe.com/index.php?m=pcdata.kaituan&pc=1&tid=" + tuanid + "&uid=" + MyApplication.myid + "&token=" + MyApplication.token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    builder.create().show();

                } else if (type == 2) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), KTListActivity.class);
                    intent.putExtra("tuanid", tuanid);
                    intent.putExtra("title", title);
                    intent.putExtra("img", img);
                    intent.putExtra("ctprice",tgprice);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
