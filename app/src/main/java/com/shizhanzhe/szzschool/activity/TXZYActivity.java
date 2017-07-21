package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.TXZYBean;
import com.shizhanzhe.szzschool.Bean.VIP;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zz9527 on 2017/7/3.
 * 业务
 */
@ContentView(R.layout.activity_txzy)
public class TXZYActivity extends Activity {
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.zh)
    TextView zh;
    @ViewInject(R.id.money)
    EditText money;
    @ViewInject(R.id.btn)
    Button btn;
    @ViewInject(R.id.back)
    ImageView back;
    AlertDialog.Builder dialog;
    String date;
    int type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        dialog = new AlertDialog.Builder(this).setTitle("提示");
         type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            title.setText("打赏提现");
        } else if (type == 2) {
            title.setText("打赏转移");
        } else if (type == 3) {
            title.setText("团购获利提现");
        } else if (type == 4) {
            title.setText("团购获利转移");
        }else if (type == 5) {
            title.setText("购买VIP");
            getVIP();
        }
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String mzh = preferences.getString("uname", "");
        zh.setText(mzh);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(money.getText().toString().trim())) {
                    if (type == 1) {
                    getData(new Path(TXZYActivity.this).DSTIXIAN(money.getText().toString().trim()));
                    } else if (type == 2) {
                        getData(new Path(TXZYActivity.this).DSZHUANYI(money.getText().toString().trim()));
                    } else if (type == 3) {
                        getData(new Path(TXZYActivity.this).TGTIXIAN(money.getText().toString().trim()));
                    } else if (type == 4) {
                        getData(new Path(TXZYActivity.this).TGZHUANYI(money.getText().toString().trim()));
                    } else if (type == 5) {
                        getData(new Path(TXZYActivity.this).VIPBUY(money.getText().toString().trim(),date));
                    }

                } else {
                    Toast.makeText(TXZYActivity.this, "金额不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void getData(String path){
        SharedPreferences preferences =getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        OkHttpDownloadJsonUtil.downloadJson(this, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                TXZYBean bean = gson.fromJson(json, TXZYBean.class);
                int status = bean.getStatus();
                if (status==1){
                    dialog.setMessage("提交成功");
                    if (type==5){
                        editor.putString("vip","1");
                        editor.commit();
                    }
                }else if (status==2){
                    dialog.setMessage(bean.getInfo());
                }
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                dialog.create().show();
            }
        });
    }
    void getVIP(){
        OkHttpDownloadJsonUtil.downloadJson(this, Path.GETVIP(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                VIP bean = gson.fromJson(json, VIP.class);
                money.setText(bean.getVip());
                date=getDateStr(bean.getToday(),365);
            }
        });
    }
    public static String getDateStr(String day,int Num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime() + (long)Num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        Log.i("________date",dateOk);
        return dateOk;
    }
}
