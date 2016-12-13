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
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.FragmentDetail;
import com.shizhanzhe.szzschool.fragment.FragmentDetailProject;

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
public class DetailActivity extends FragmentActivity implements View.OnClickListener {
@ViewInject(R.id.buy)
    Button buy;
    String id;
    Dialog dialog;
    String img;
    String title;
//    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String uid = MyApplication.myid;
        String token = MyApplication.token;
         img = intent.getStringExtra("img");
        String intro = intent.getStringExtra("intro");
         title = intent.getStringExtra("title");

        FragmentDetail fragmentDetail = new FragmentDetail().newInstance(img, title, intro);
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
//                        TextView price = (TextView) dialog.getWindow().findViewById(R.id.buy_price);
                        Button btn = (Button) dialog.getWindow().findViewById(R.id.buy_yes);

                        pro.setText(title);
//                      price.setText(price);
                        btn.setOnClickListener(this);
                        break;
                    case R.id.buy_yes:
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
                                    }

                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.i("success", response.body().string());
                                if (response.isSuccessful()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(DetailActivity.this, "购买成功", Toast.LENGTH_LONG).show();
                                        }

                                    });
                                }

                            }
                        });
                        break;
                }


            }


}
