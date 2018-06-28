package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.RegisterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by zz9527 on 2017/7/20.
 * 修改密码
 */
@ContentView(R.layout.activity_xm)
public class XMActivity extends Activity{
    @ViewInject(R.id.reset_new_pass)
    EditText ed1;
    @ViewInject(R.id.reset_new_pass_two)
    EditText ed2;
    @ViewInject(R.id.reset)
    TextView reset;
    @ViewInject(R.id.back)
    ImageView back;
    private QMUITipDialog mdialog;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mdialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String p1 = ed1.getText().toString();
                String p2 = ed2.getText().toString();
                if (p1.length() != 0 && p2.length() != 0) {
                    if (p1.equals(p2)) {
                        OkHttpDownloadJsonUtil.downloadJson(XMActivity.this, new Path(XMActivity.this).CHANGE(p1), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                Gson gson = new Gson();
                                RegisterBean bean = gson.fromJson(json, RegisterBean.class);
                                if (bean.getStatus() == 1) {
                                    MM(p1);
                                    mdialog = new QMUITipDialog.Builder(XMActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(3, 1500);
                                    new Handler(new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(Message msg) {
                                            finish();
                                            return false;
                                        }
                                    }).sendEmptyMessageDelayed(0,1500);

                                } else if (bean.getStatus() == 2) {
                                    mdialog = new QMUITipDialog.Builder(XMActivity.this).setIconType(4).setTipWord(bean.getInfo()).create();
                                    mdialog.show();
                                    mhandler.sendEmptyMessageDelayed(1,1500);
                                }
                            }
                        });
                    } else {
                        mdialog = new QMUITipDialog.Builder(XMActivity.this).setIconType(4).setTipWord("密码输入不一致").create();
                        mdialog.show();
                        mhandler.sendEmptyMessageDelayed(1,1500);
                    }
                } else {
                    mdialog = new QMUITipDialog.Builder(XMActivity.this).setIconType(4).setTipWord("密码不能为空").create();
                    mdialog.show();
                    mhandler.sendEmptyMessageDelayed(1,1500);
                }
            }

        });
    }
    void MM(String password){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuffer sb = new StringBuffer(password);
        String s = sb.reverse().toString();
        s = s.replace("", "-"); //每个字符加个-
        String a[] = s.split("-");//截取字符串为数组
        String t = a[3] + a[4];
        String y = a[2] + a[5];
        a[2] = "";
        a[3] = "";
        a[4] = "";
        a[5] = "";
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            sb2.append(a[i]);
        }
        String b2 = sb2.toString();
        String b = b2 + t + y;
        editor.putString("upawd", b);
        editor.commit();
    }
}