package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;

import com.shizhanzhe.szzschool.Bean.UpdateBean;
import com.shizhanzhe.szzschool.MainActivity;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.SharedUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;


/**
 * 欢迎页
 */
public class WelcomeActivity extends Activity {
    DbManager manager = DatabaseOpenHelper.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message arg0) {
                // TODO Auto-generated method stub\
                //是第一次启动
                if(SharedUtils.isFirstStart(getBaseContext()))
                {
                    try {
                        manager.save(new UpdateBean(1.0));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(getApplicationContext(), WelcomeGuideActivity.class));
                    //修改成不是第一次启动
                    SharedUtils.putIsFirstStart(getBaseContext(), false);
                }
                else
                {
                    SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    final String uname = preferences.getString("uname", "");
                    String upawd = preferences.getString("upawd", "");
                    if (TextUtils.isEmpty(uname) && TextUtils.isEmpty(upawd)) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        String path = Path.UZER(uname, upawd);
                        MyApplication.path = path;
                        OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), path, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {
                                MyApplication.zh = uname;
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), MainActivity.class);
                                intent.putExtra("data", json);
                                startActivity(intent);
                            }
                        });
                    }

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
