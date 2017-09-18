package com.shizhanzhe.szzschool.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mob.commons.SHARESDK;
import com.shizhanzhe.szzschool.Bean.BuyBean;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.FragmentDetail;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.externalService;
import static android.R.attr.path;

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
    @ViewInject(R.id.videobtn)
    Button videobtn;
    @ViewInject(R.id.share)
    ImageView share;
    String id;
    Dialog dialog;
    String img;
    String proprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
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
        videobtn.setOnClickListener(this);
        study.setOnClickListener(this);
        share.setOnClickListener(this);
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
                }else{
                    Toast.makeText(DetailActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this,LoginActivity.class));
                }
                break;
            case R.id.buy_yes:
                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor= preferences.edit();
                String uid = preferences.getString("uid", "");
                final String money = preferences.getString("money", "");
                if (Double.parseDouble(money)<Double.parseDouble(proprice)){
                    dialog.dismiss();
                    new SVProgressHUD(DetailActivity.this).showInfoWithStatus("账户余额不足，无法购买");
                }else {
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
                                            editor.putString("money", Double.parseDouble(money)-Double.parseDouble(proprice)+"");
                                            editor.commit();
                                            new SVProgressHUD(DetailActivity.this).showSuccessWithStatus("购买成功");
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
            case R.id.videobtn:
                Intent intent = new Intent(DetailActivity.this, ProjectDetailActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.study:
                Intent intent2 = new Intent(DetailActivity.this, ProjectDetailActivity.class);
                intent2.putExtra("name", name);
                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                showShare();
                break;
        }
    }

    String name;

    void getData() {
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String vip = preferences.getString("vip", "");
        OkHttpDownloadJsonUtil.downloadJson(this, new Path(this).SECOND(id), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    Gson gson = new Gson();
                ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
                String isbuy = tx.getIsbuy();
                img = tx.getThumb();
                name = tx.getStitle();
                proprice=tx.getNowprice();
                if (MyApplication.isLogin){
                    if (vip.equals("1")) {
                        buy.setVisibility(View.GONE);
                        study.setVisibility(View.VISIBLE);
                    } else {
                        if (isbuy.equals("0")) {
                            videobtn.setVisibility(View.VISIBLE);
                            buy.setVisibility(View.VISIBLE);
                            study.setVisibility(View.GONE);
                        } else if (isbuy.equals("1")) {
                            buy.setVisibility(View.GONE);
                            study.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    videobtn.setVisibility(View.VISIBLE);
                    buy.setVisibility(View.VISIBLE);
                    study.setVisibility(View.GONE);
                }



                FragmentDetail fragmentDetail = new FragmentDetail().newInstance(id);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ll, fragmentDetail).commit();

                }catch (Exception e) {
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
        oks.setComment("评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("实战者学院");
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://shizhanzhe.com");
// 启动分享GUI
        oks.show(this);
    }
}
