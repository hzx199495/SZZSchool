package com.shizhanzhe.szzschool.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.BuyBean;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.Bean.ProBean2;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.FragmentDetail;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2016/11/22.
 */
@ContentView(R.layout.activity_detail)
public class  DetailActivity extends FragmentActivity implements View.OnClickListener {
@ViewInject(R.id.buy)
    Button buy;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.study)
            Button study;
    @ViewInject(R.id.videobtn)
    Button videobtn;
    String id;
    Dialog dialog;
    String img;
    String title;
    String proprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        back.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getData();
        dialog = new Dialog(this,R.style.dialog);
        dialog.setContentView(R.layout.dialog_buy);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        buy.setOnClickListener(this);
        videobtn.setOnClickListener(this);
        study.setOnClickListener(this);
    }
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buy:
                        dialog.show();
                        TextView pro = (TextView) dialog.getWindow().findViewById(R.id.buy_pro);
                        final TextView price = (TextView) dialog.getWindow().findViewById(R.id.buy_pr);
                        Button btn = (Button) dialog.getWindow().findViewById(R.id.buy_yes);
                        pro.setText(title);
                        price.setText("￥"+proprice);
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
                                        Toast.makeText(DetailActivity.this, "购买失败", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }

                                });
                            }

                            @Override
                            public void onResponse(Call call,  Response response) throws IOException {
                                final String buy = response.body().string();
                                if (response.isSuccessful()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Gson gson = new Gson();
                                            BuyBean bean = gson.fromJson(buy, BuyBean.class);
                                            if (bean.getStatus()==1){
                                                Toast.makeText(DetailActivity.this, "购买成功", Toast.LENGTH_LONG).show();
                                                getData();
                                            }else if(bean.getStatus()==3){
                                                Toast.makeText(DetailActivity.this, "余额不足,请充值", Toast.LENGTH_LONG).show();
                                            }
                                            dialog.dismiss();
                                        }

                                    });
                                }
                            }
                        });
                        break;
                    case R.id.videobtn:
                        Intent intent = new Intent(DetailActivity.this, ProjectDetailActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.study:
                        Intent intent2 = new Intent(DetailActivity.this, ProjectDetailActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.back:
                        finish();
                        break;
                }


            }

    void getData(){
        OkHttpDownloadJsonUtil.downloadJson(this, Path.SECOND(id, MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                ProBean2.TxBean tx = gson.fromJson(json, ProBean2.class).getTx();
                String isbuy = tx.getIsbuy();
                if (MyApplication.vip.equals("1")){
                    buy.setVisibility(View.GONE);
                    study.setVisibility(View.VISIBLE);
                }else{
                    if (isbuy.equals("0")){

                    }else if (isbuy.equals("1")){
                        buy.setVisibility(View.GONE);
                        study.setVisibility(View.VISIBLE);
                    }
                }

                FragmentDetail fragmentDetail = new FragmentDetail().newInstance(id);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ll, fragmentDetail).commit();
            }
        });
    }
}
