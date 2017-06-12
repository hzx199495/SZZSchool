package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/10/31.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.btn_login)
    Button mBtnLogin;
    @ViewInject(R.id.edit_uid)
    EditText mEditUid;
    @ViewInject(R.id.txtMobileNum)
    EditText mEditPsw;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        mBtnLogin.setOnClickListener(this);
        findViewById(R.id.tv_quick_sign_up).setOnClickListener(this);
        findViewById(R.id.RetrievePassword).setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setMessage("正在登录...Loading");

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
//        final String uname = preferences.getString("uname", "");
//        String upawd = preferences.getString("upawd", "");
//        if (TextUtils.isEmpty(uname) && TextUtils.isEmpty(upawd)) {
//
//        } else {
//            Log.i("________",uname+upawd);
//            String path = Path.UZER(uname, upawd);
//            MyApplication.path = path;
//            OkHttpDownloadJsonUtil.downloadJson(this, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                @Override
//                public void onsendJson(String json) {
//                    MyApplication.zh = uname;
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("data", json);
//                    startActivity(intent);
//                }
//            });
//        }
    }
    ProgressDialog dialog;
    /**
     * 登录按钮
     */
    String username;
    String b;
    private void login() {
        dialog.show();
        username = mEditUid.getText().toString();
        String password = mEditPsw.getText().toString();
        if (username != null && password.length() >= 6) {
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
            b = b2 + t + y;
            String path = Path.UZER(username, b);
            MyApplication.path = path;
            okhttp(path);
        } else {
            Toast.makeText(LoginActivity.this, "帐号或密码长度有误", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:    //登录
                login();
                break;
            case R.id.RetrievePassword:    //忘记密码
                startActivity(new Intent(this, FindPWActivity.class));
                break;
            case R.id.tv_quick_sign_up:    //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            default:
                break;
        }
    }
    public void okhttp(String path){
        OkHttpDownloadJsonUtil.downloadJson(this, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.length() <= 5) {
                    Toast.makeText(LoginActivity.this, "帐号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    editor.putString("uname", username);
                    editor.putString("upawd", b);
                    editor.commit();
                    dialog.setMessage("登陆成功");
                    MyApplication.zh = username;
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    intent.putExtra("data", json);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

}

