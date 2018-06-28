package com.shizhanzhe.szzschool.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.BuyBean;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.MPagerAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;

import java.io.IOException;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.shizhanzhe.szzschool.activity.MyApplication.userType;

/**
 * Created by hasee on 2016/11/22.
 * 课程详情
 */
@ContentView(R.layout.activity_detail)
public class DetailActivity extends FragmentActivity implements View.OnClickListener {
    @ViewInject(R.id.buy)
    Button buy;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.study)
    Button study;
    @ViewInject(R.id.addwx)
    Button addwx;
    @ViewInject(R.id.share)
    ImageView share;
    @ViewInject(R.id.detail_iv)
    ImageView detail_iv;
    @ViewInject(R.id.detail_title)
    TextView detail_title;
    @ViewInject(R.id.detail_price)
    TextView detail_price;
    @ViewInject(R.id.collect)
    ImageView collect;
    @ViewInject(R.id.detail_study)
    TextView detail_study;
    @ViewInject(R.id.detail_yjprice)
    TextView detail_yjprice;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    private QMUITipDialog loading;
    private QMUITipDialog error;
    private QMUITipDialog success;
    private QMUITipDialog coll;

    private String id;
    private Dialog dialog;
    private String img;
    private String proprice;


    @ViewInject(R.id.tabLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.viewPager)
    ViewPager viewPager;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    error.dismiss();
                    break;
                case 2:
                    success.dismiss();
                    break;
                case 3:
                    coll.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.top));
        MobSDK.init(this, "211d17cfbf506", "751845082fc06c195f287737547c9165");

        loading = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
        back.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getData();
        dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(R.layout.dialog_buy);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        buy.setOnClickListener(this);
        study.setOnClickListener(this);
        share.setOnClickListener(this);
        addwx.setOnClickListener(this);
        collect.setOnClickListener(this);
//        state_layout.showLoadingView();
//        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
//            @Override
//            public void refreshClick() {
//                state_layout.showLoadingView();
//                getData();
//            }
//
//            @Override
//            public void loginClick() {
//
//            }
//        });


    }

    private void inintListener() {

//        intro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intro.setTextColor(getResources().getColor(R.color.blue2));
//                expand.setTextColor(Color.BLACK);
//                intro2.setTextColor(getResources().getColor(R.color.blue2));
//                expand2.setTextColor(Color.BLACK);
//                switchFragment(introFragment);
//            }
//        });
//        expand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intro.setTextColor(Color.BLACK);
//                expand.setTextColor(getResources().getColor(R.color.blue2));
//                intro2.setTextColor(Color.BLACK);
//                expand2.setTextColor(getResources().getColor(R.color.blue2));
//                if (proExpanFragment == null) {
//                    proExpanFragment = ProExpanFragment.newInstance(vjson, id);
//                }
//                switchFragment(proExpanFragment);
//            }
//        });
//
//        intro2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intro.setTextColor(getResources().getColor(R.color.blue2));
//                expand.setTextColor(Color.BLACK);
//                intro2.setTextColor(getResources().getColor(R.color.blue2));
//                expand2.setTextColor(Color.BLACK);
//                switchFragment(introFragment);
//            }
//        });
//        expand2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intro.setTextColor(Color.BLACK);
//                expand.setTextColor(getResources().getColor(R.color.blue2));
//                intro2.setTextColor(Color.BLACK);
//                expand2.setTextColor(getResources().getColor(R.color.blue2));
//                if (proExpanFragment == null) {
//                    proExpanFragment = ProExpanFragment.newInstance(vjson, id);
//                }
//                switchFragment(proExpanFragment);
//            }
//        });
//        intro.setTextColor(getResources().getColor(R.color.blue2));
//        expand.setTextColor(Color.BLACK);
//        intro2.setTextColor(getResources().getColor(R.color.blue2));
//        expand2.setTextColor(Color.BLACK);
//        switchFragment(introFragment);


        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MPagerAdapter(getSupportFragmentManager(), vjson, id, imgs));
        tabLayout.setupWithViewPager(viewPager);
    }

    String imgs;
//    // 切换Fragment方法
//    private void switchFragment(Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.proll, fragment).commit();
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buy:
                if (MyApplication.isLogin) {
                    dialog.show();
                    TextView pro = (TextView) dialog.getWindow().findViewById(R.id.buy_pro);
                    final TextView price = (TextView) dialog.getWindow().findViewById(R.id.buy_pr);
                    TextView usermoney = (TextView) dialog.getWindow().findViewById(R.id.usermoney);
                    Button btn = (Button) dialog.getWindow().findViewById(R.id.buy_yes);
                    pro.setText(name);
                    SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String money = preferences.getString("money", "");
                    usermoney.setText("账户余额：￥" + money);
                    price.setText("￥" + proprice);
                    btn.setOnClickListener(this);
                } else {
                    Toast.makeText(DetailActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                }
                break;
            case R.id.buy_yes:
                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                String uid = preferences.getString("uid", "");
                final String money = preferences.getString("money", "");
                if (Double.parseDouble(money) < Double.parseDouble(proprice)) {
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("余额不足，前往账户中心充值")
                            .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(DetailActivity.this, UserZHActivity.class));
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();


                } else {
                    QMUITipDialog buyDialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在购买...").create();
                    buyDialog.show();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("uid", uid).add("systemid", id)
                            .add("type", "1").add("pid", "0").add("coid", "0").add("catid", "0")
                            .build();
                    Request request = new Request.Builder()
                            .url("https://shizhanzhe.com/index.php?m=courSystem.buy&pc=1")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("fail", e.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    error = new QMUITipDialog.Builder(DetailActivity.this).setIconType(3).setTipWord("购买失败").create();
                                    error.show();
                                    dialog.dismiss();
                                    mhandler.sendEmptyMessageDelayed(1, 1500);
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
                                            editor.putString("money", Double.parseDouble(money) - Double.parseDouble(proprice) + "");
                                            editor.commit();
                                            success = new QMUITipDialog.Builder(DetailActivity.this).setIconType(2).setTipWord("购买成功").create();
                                            success.show();
                                            mhandler.sendEmptyMessageDelayed(2, 1500);
                                            getData();
                                        } else if (bean.getStatus() == 3) {
                                            success = new QMUITipDialog.Builder(DetailActivity.this).setIconType(4).setTipWord("账户余额不足").create();
                                            success.show();
                                            mhandler.sendEmptyMessageDelayed(2, 1500);
                                        }
                                        dialog.dismiss();
                                    }

                                });
                            }
                        }
                    });
                }
                break;
            case R.id.study:
                Intent intent2 = new Intent(DetailActivity.this, ProjectDetailActivity.class);
                intent2.putExtra("name", name);
                intent2.putExtra("json", vjson);
                startActivity(intent2);
                break;
            case R.id.addwx:

                if (isbuy.equals("1") || vip.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("联系客服加入交流群")
                            .setItems(new String[]{"QQ联系：800199188 (点击复制)", "微信联系：szz892 (点击复制)"}, actionListener);

                    builder.create().show();
//                    if (!"".equals(kefuhao)) {
//                        try {
//                            // 获取剪贴板管理服务
//                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                            //将文本数据（微信号）复制到剪贴板
//                            cm.setText(kefuhao);
//                            //跳转微信
//                            Intent intentwx = new Intent(Intent.ACTION_MAIN);
//                            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                            intentwx.addCategory(Intent.CATEGORY_LAUNCHER);
//                            intentwx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intentwx.setComponent(cmp);
//                            startActivity(intentwx);
//                            Toast.makeText(this, "微信号已复制到粘贴板，请添加使用", Toast.LENGTH_LONG).show();
//                        } catch (ActivityNotFoundException e) {
//                            e.printStackTrace();
//                            Toast.makeText(this, "您还没有安装微信，请安装后使用", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(this, "暂无客服微信号", Toast.LENGTH_LONG).show();
//                    }
                } else {
                    Toast.makeText(this, "未购买课程", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                showShare();
                break;
            case R.id.collect:
                if (MyApplication.isLogin) {
                    if (iv2 == 2) {
                        iv2 = 1;
                        collect.setImageResource(R.drawable.ic_courseplay_star1);
                        coll = new QMUITipDialog.Builder(DetailActivity.this).setIconType(4).setTipWord("取消收藏").create();
                        coll.show();
                        mhandler.sendEmptyMessageDelayed(3, 1500);
                    } else {
                        iv2 = 2;
                        collect.setImageResource(R.drawable.ic_courseplay_star2);
                        coll = new QMUITipDialog.Builder(DetailActivity.this).setIconType(4).setTipWord("已收藏").create();
                        coll.show();
                        mhandler.sendEmptyMessageDelayed(3, 1500);
                    }
                }
                break;
        }
    }

    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            switch (which) {
                case 0:
                    cm.setText("800199188");
                    break;
                case 1:
                    cm.setText("szz892");
                    break;
                default:
                    break;
            }
            Toast.makeText(DetailActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    };
    private String name;
    private String kefuhao;
    private String vjson;
    private int iv = 1;
    private int iv2 = 1;
    String vip;
    String isbuy;

    void getData() {
        loading.show();
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).COLLECTLIST(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                try {

                    Gson gson = new Gson();
                    List<CollectListBean> list = gson.fromJson(json, new TypeToken<List<CollectListBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        for (CollectListBean bean : list
                                ) {
                            if (bean.getSysinfo().get(0).getId().contains(id)) {
                                iv = 2;
                                iv2 = 2;
                                collect.setImageResource(R.drawable.ic_courseplay_star2);
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        vip = preferences.getString("vip", "");
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).SECOND(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                try {
                    vjson = json;
                    Gson gson = new Gson();
                    ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                    isbuy = tx.getIsbuy();
                    img = tx.getThumb();
                    name = tx.getStitle();
                    proprice = tx.getNowprice();
                    kefuhao = tx.getKefuhao();
                    detail_title.setText(tx.getStitle());
                    ImageLoader imageloader = ImageLoader.getInstance();
                    imageloader.displayImage(Path.IMG(tx.getThumb()), detail_iv, MyApplication.displayoptions);
                    if (tx.getStitle().contains("Photoshop")) {
                        detail_yjprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                        detail_yjprice.setVisibility(View.VISIBLE);
                        detail_yjprice.setText("原价：￥699");
                    }
                    detail_price.setText("￥" + tx.getNowprice());
                    detail_study.setText("学习人数：" + tx.getNum() + "人");
                    if (MyApplication.isLogin) {
                        if (vip.equals("1")) {
                            buy.setVisibility(View.GONE);
                            study.setVisibility(View.VISIBLE);
                            addwx.setVisibility(View.VISIBLE);
                            userType = 1;

                        } else {
                            if (isbuy.equals("0")) {
                                userType = 0;
                                buy.setVisibility(View.VISIBLE);
                                study.setVisibility(View.GONE);
                                addwx.setVisibility(View.VISIBLE);
                            } else if (isbuy.equals("1")) {
                                userType = 1;
                                buy.setVisibility(View.GONE);
                                study.setVisibility(View.VISIBLE);
                                addwx.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        buy.setVisibility(View.VISIBLE);
                        study.setVisibility(View.GONE);
                        addwx.setVisibility(View.VISIBLE);
                    }
                    imgs = tx.getDuoimg();
                    inintListener();
                    loading.dismiss();
                } catch (Exception e) {
                    loading.dismiss();
                    Toast.makeText(DetailActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("https://shizhanzhe.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(name);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(Path.IMG(img));
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://shizhanzhe.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("跟我一起加入实战者!");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("实战者学院");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://shizhanzhe.com");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (MyApplication.isLogin) {
            if (iv == 1 && iv != iv2) {
                OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).COLLECT(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                    }
                });
            } else if (iv == 2 && iv != iv2) {
                OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).DELCOLLECT(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                    }
                });
            }
        }
    }

}
