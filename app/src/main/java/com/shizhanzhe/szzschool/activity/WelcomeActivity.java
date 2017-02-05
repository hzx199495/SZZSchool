package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.shizhanzhe.szzschool.Bean.UpdateBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.db.DatabaseOpenHelper;
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
//        new Handler().postDelayed(r, 1000);// 1秒后关闭，并跳转到主页面
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
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finish();
                return false;
            }

        }).sendEmptyMessageDelayed(0, 1500);
    }

}
