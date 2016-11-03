package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;

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
    @ViewInject(R.id.edit_psw)
    EditText mEditPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        mBtnLogin.setOnClickListener(this);
        findViewById(R.id.tv_quick_sign_up).setOnClickListener(this);
    }


    /**
     * 登录按钮
     */
    private void login() {
        String username = mEditUid.getText().toString();
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
             String b = b2 + t + y;
             String path = "http://shizhanzhe.com/index.php?m=pcdata.user_data&pc=1&username="
                    + username + "&password="
                    + b;
            Log.i("++++++", path);
            OkHttpDownloadJsonUtil.downloadJson(this, path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
//                    Log.i("====",json);
                    if(json.length()<=5){
                        Toast.makeText(LoginActivity.this,"帐号或密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        intent.putExtra("data", json);
                        startActivity(intent);
                    }
                }
            });
//            RequestParams params = new RequestParams(path);
//            x.http().get(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Log.i("====",result);
//                    if(result.length()<=5){
//                        Toast.makeText(LoginActivity.this,"帐号或密码错误，请重新输入",Toast.LENGTH_SHORT).show();
//                    }else {
//                        Intent intent = new Intent();
//                        intent.setClass(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("data", result);
//                        startActivity(intent);
//                    }
//
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                        ex.getLocalizedMessage();
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });

        } else {
            Toast.makeText(LoginActivity.this, "帐号或密码长度有误", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:    //登录
                login();
                break;
            case R.id.btnRetrievePassword:    //忘记密码
//                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.tv_quick_sign_up:    //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            default:
                break;
        }
    }
}

