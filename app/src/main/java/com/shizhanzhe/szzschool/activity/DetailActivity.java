package com.shizhanzhe.szzschool.activity;

import android.app.Dialog;
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
import com.shizhanzhe.szzschool.Bean.CollectBean;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.fragment.FragmentDetail;
import com.shizhanzhe.szzschool.fragment.FragmentDetailProject;
import com.shizhanzhe.szzschool.pay.Pay;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

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
    String id;
    Dialog dialog;
    String img;
    String title;
    String proprice;
//    DbManager manager = DatabaseOpenHelper.getInstance();
//    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        back.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        String uid = MyApplication.myid;
        String token = MyApplication.token;
         img = intent.getStringExtra("img");
        String intro = intent.getStringExtra("intro");
         title = intent.getStringExtra("title");
        proprice = intent.getStringExtra("price");

        FragmentDetail fragmentDetail = new FragmentDetail().newInstance(id,img, title, intro,proprice);
        FragmentDetailProject fragmentDetailProject = new FragmentDetailProject().newInstance(id, uid, token);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.first, fragmentDetail).add(R.id.second, fragmentDetailProject)
                .commit();
        dialog = new Dialog(this,R.style.dialog);
        dialog.setContentView(R.layout.dialog_buy);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        buy.setOnClickListener(this);
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
                        price.setText(proprice);
                        btn.setOnClickListener(this);
                        break;
                    case R.id.buy_yes:
//                        if(MyApplication.money<proprice){}
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("uid", MyApplication.myid).add("systemid", id)
                                .add("type", "1").add("pid", "0").add("coid", "0").add("catid", "0")
                                .build();
                        Request request = new Request.Builder()
                                .url("http://2.huobox.com/index.php?m=courSystem.buy&pc=1")
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
                                            }else if(bean.getStatus()==3){
                                                Toast.makeText(DetailActivity.this, "余额不足，使用支付宝支付", Toast.LENGTH_LONG).show();
                                                new Pay(DetailActivity.this,proprice, new Pay.PayListener() {
                                                    @Override
                                                    public void refreshPriorityUI(String string) {
                                                        OkHttpDownloadJsonUtil.downloadJson(DetailActivity.this, MyApplication.path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                                            @Override
                                                            public void onsendJson(String json) {
                                                                Gson gson = new Gson();
                                                                LoginBean loginData = gson.fromJson(json, LoginBean.class);
                                                                String token = loginData.getToken();
                                                                String mymoney=loginData.getMoney();
                                                                MyApplication.token=token;
                                                                MyApplication.money=mymoney;
                                                                Toast.makeText(DetailActivity.this, "购买成功", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                });
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
                }


            }


}
