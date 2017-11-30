package com.shizhanzhe.szzschool.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.BuyBean;
import com.shizhanzhe.szzschool.Bean.CollectListBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.IntroFragment;
import com.shizhanzhe.szzschool.fragment.ProExpanFragment;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.MyScrollView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
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
public class DetailActivity extends FragmentActivity implements View.OnClickListener, MyScrollView.MyScrollListener {
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
    @ViewInject(R.id.rg)
    RadioGroup rg;
    @ViewInject(R.id.rg2)
    LinearLayout rg2;
    @ViewInject(R.id.detail_price)
    TextView detail_price;
    @ViewInject(R.id.collect)
    ImageView collect;
    @ViewInject(R.id.detail_study)
    TextView detail_study;
    @ViewInject(R.id.detail_yjprice)
    TextView detail_yjprice;
    //    @ViewInject(R.id.state_layout)
//    StateLayout state_layout;
    @ViewInject(R.id.intro)
    RadioButton intro;
    @ViewInject(R.id.expand)
    RadioButton expand;
    @ViewInject(R.id.intro2)
    TextView intro2;
    @ViewInject(R.id.expand2)
    TextView expand2;
    @ViewInject(R.id.myscroll)
    MyScrollView myscroll;
    @ViewInject(R.id.layout_login_topbar)
    RelativeLayout top;
    private String id;
    private Dialog dialog;
    private String img;
    private String proprice;
    private ProExpanFragment proExpanFragment;
    private IntroFragment introFragment;

    int rgheight;
    int topheight;
    int select = 1;
    SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        MobSDK.init(this, "211d17cfbf506", "751845082fc06c195f287737547c9165");
        svProgressHUD = new SVProgressHUD(this);
        svProgressHUD.showWithStatus("正在加载...");
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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.intro:
                        select = 1;
                        intro.setTextColor(getResources().getColor(R.color.blue2));
                        expand.setTextColor(Color.BLACK);
                        intro2.setTextColor(getResources().getColor(R.color.blue2));
                        expand2.setTextColor(Color.BLACK);
                        fragment = introFragment;
                        break;
                    case R.id.expand:
                        select = 2;
                        intro.setTextColor(Color.BLACK);
                        expand.setTextColor(getResources().getColor(R.color.blue2));
                        intro2.setTextColor(Color.BLACK);
                        expand2.setTextColor(getResources().getColor(R.color.blue2));
                        if (proExpanFragment == null) {
                            proExpanFragment = ProExpanFragment.newInstance(vjson, id);
                        }
                        fragment = proExpanFragment;
                        break;
                }
                switchFragment(fragment);
            }
        });
        intro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 1) {

                } else {
                    intro2.setTextColor(getResources().getColor(R.color.blue2));
                    expand2.setTextColor(Color.BLACK);
                    rg.check(R.id.intro);
                }
            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 2) {

                } else {
                    intro2.setTextColor(Color.BLACK);
                    expand2.setTextColor(getResources().getColor(R.color.blue2));
                    rg2.setVisibility(View.GONE);
                    rg.check(R.id.expand);
                }
            }
        });
        rg.check(R.id.intro);
    }

    // 切换Fragment方法
    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.proll, fragment).commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buy:
                if (MyApplication.isLogin) {
                    dialog.show();
                    TextView pro = (TextView) dialog.getWindow().findViewById(R.id.buy_pro);
                    final TextView price = (TextView) dialog.getWindow().findViewById(R.id.buy_pr);
                    Button btn = (Button) dialog.getWindow().findViewById(R.id.buy_yes);
                    pro.setText(name);
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
                    Toast.makeText(this, "正在发送购买请求...", Toast.LENGTH_SHORT).show();
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
                                    new SVProgressHUD(DetailActivity.this).showErrorWithStatus("购买失败");
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
                                            editor.putString("money", Double.parseDouble(money) - Double.parseDouble(proprice) + "");
                                            editor.commit();
                                            new SVProgressHUD(DetailActivity.this).showSuccessWithStatus("购买成功,添加微信号入群");
                                            getData();
                                        } else if (bean.getStatus() == 3) {
                                            new SVProgressHUD(DetailActivity.this).showInfoWithStatus("账户余额不足，无法购买");
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
                    if (!"".equals(kefuhao)) {
                        try {
                            // 获取剪贴板管理服务
                            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            //将文本数据（微信号）复制到剪贴板
                            cm.setText(kefuhao);
                            //跳转微信
                            Intent intentwx = new Intent(Intent.ACTION_MAIN);
                            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                            intentwx.addCategory(Intent.CATEGORY_LAUNCHER);
                            intentwx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentwx.setComponent(cmp);
                            startActivity(intentwx);
                            Toast.makeText(this, "微信号已复制到粘贴板，请添加使用", Toast.LENGTH_LONG).show();
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "您还没有安装微信，请安装后使用", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "暂无客服微信号", Toast.LENGTH_LONG).show();
                    }
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
                        Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        iv2 = 2;
                        collect.setImageResource(R.drawable.ic_courseplay_star2);
                        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }
    }

    private String name;
    private String kefuhao;
    private String vjson;
    private int iv = 1;
    private int iv2 = 1;
    String vip;
    String isbuy;

    void getData() {

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
                    introFragment = IntroFragment.newInstance(id, tx.getDuoimg());
                    inintListener();
                    myscroll.setMyScrollListener(DetailActivity.this);
                    svProgressHUD.dismiss();
                } catch (Exception e) {
                    svProgressHUD.dismiss();
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
    public void onStop() {
        super.onStop();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            topheight = top.getMeasuredHeight();  //获取位置2，不就是搜索栏的高度么，啊哈哈哈，是不是很机智，当然你也可以用getButtom，一样的，看你自己
            rgheight = detail_iv.getMeasuredHeight() + detail_title.getMeasuredHeight() + detail_study.getMeasuredHeight() + detail_price.getMeasuredHeight() + topheight; //获取位置3，即内部绿色栏的顶部到布局顶部的距离
        }
    }

    @Override
    public void sendDistanceY(int scrollY) {
        if (scrollY > rgheight) {  //如果滑动的距离大于或等于二者距离，显示外部
            rg2.setVisibility(View.VISIBLE);
        } else {  //反之隐藏
            rg2.setVisibility(View.GONE);
        }
    }
}
