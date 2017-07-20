package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.utils.DataCleanManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hasee on 2016/11/28.
 */
@ContentView(R.layout.activity_sz)
public class SZActivity extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    @ViewInject(R.id.cb_msg)
    CheckBox msg;
    @ViewInject(R.id.tv_cache)
    TextView cache;
    @ViewInject(R.id.user_exit)
    TextView exit;
    @ViewInject(R.id.ll_clean_cache)
    LinearLayout cleanCache;
    @ViewInject(R.id.xgmm)
    LinearLayout xgmm;
    @ViewInject(R.id.back)
    ImageView back;

    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        msg.setOnCheckedChangeListener(this);
        try {
            cache.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cleanCache.setOnClickListener(this);
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        xgmm.setOnClickListener(this);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            msg.setChecked(!flag);
            if(!flag){
                JPushInterface.stopPush(getApplicationContext());
            }else{
                JPushInterface.resumePush(getApplicationContext());
        }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_clean_cache:
                DataCleanManager.clearAllCache(getApplicationContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            cache.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
                            new SVProgressHUD(SZActivity.this).showSuccessWithStatus("缓存已清空");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case R.id.user_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(SZActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认退出当前账号？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("uname");
                        editor.remove("upawd");
                        editor.commit();
                        startActivity(new Intent(SZActivity.this,LoginActivity.class));
                        MyApplication.getInstance().exit();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();

                break;
            case R.id.xgmm:
                startActivity(new Intent(SZActivity.this,XMActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
