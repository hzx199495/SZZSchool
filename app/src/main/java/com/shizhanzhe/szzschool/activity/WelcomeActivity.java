package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.Bean.UpdateBean;
import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;

import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.DbManager;
import org.xutils.ex.DbException;


/**
 * 欢迎页
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        SharedPreferences firstPreferences = getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        final SharedPreferences.Editor firstEditor = firstPreferences.edit();
        final int first = firstPreferences.getInt("first", 0);
        new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message arg0) {
                // TODO Auto-generated method stub\


                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    final String uname = preferences.getString("uname", "");
                    String upawd = preferences.getString("upawd", "");
                    if (TextUtils.isEmpty(uname) && TextUtils.isEmpty(upawd)) {
                        if (first==0){
                            firstEditor.putInt("first",1);
                            firstEditor.commit();
                            startActivity(new Intent(getApplicationContext(), WelcomeGuideActivity.class));
                        }else {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    } else {
                        String path = Path.UZER(uname, upawd);
                        OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = preferences.edit();
                                Gson gson = new Gson();
                                LoginBean bean = gson.fromJson(json, LoginBean.class);
                                editor2.putString("username", bean.getUsername());
                                editor2.putString("uid", bean.getId());
                                editor2.putString("mobile", bean.getMobile());
                                editor2.putString("token", bean.getToken());
                                editor2.putString("vip", bean.getVip());
                                editor2.putString("money", bean.getMoney());
                                editor2.putString("ktagent", bean.getKaiagent());
                                editor2.putString("teacher", bean.getIs_teacher());
                                editor2.putString("jy", bean.getJyan());
                                editor2.putString("img", bean.getHeadimg());
                                editor2.commit();
                                MyApplication.isLogin=true;
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });


                }
                return false;
            }

        }).sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
