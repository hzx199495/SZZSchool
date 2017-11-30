package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.tencent.bugly.beta.Beta;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by hasee on 2016/11/28.
 * 系统设置
 */
@ContentView(R.layout.activity_sz)
public class SZActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.cb_play_vido_tip)
    CheckBox receiver;
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
    @ViewInject(R.id.ll_content)
    LinearLayout contentQQ;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.ll_verson)
    LinearLayout ll_verson;
    @ViewInject(R.id.tv_verson)
    TextView verson;
    @ViewInject(R.id.ll_about)
    LinearLayout ll_about;
    private int  msgflag = 1;
    private int  reflag=1;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        verson.setText(getVersionName(this));
        SharedPreferences preferences = getSharedPreferences("userset", Context.MODE_PRIVATE);
        int msgisopen = preferences.getInt("msg", 0);
        int receiverisopen = preferences.getInt("receiver",0);
        editor = preferences.edit();
        if (msgisopen==1){
            msgflag=1;
            msg.setChecked(true);
        }else if (msgisopen==2){
            msgflag=2;
            msg.setChecked(false);
        }
        if (receiverisopen==1){
            reflag=1;
            receiver.setChecked(true);
        }else if (receiverisopen==2){
            reflag=2;
            receiver.setChecked(false);
        }
        msg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (msgflag==1) {
                        msgflag = 2;
                        msg.setChecked(false);
//                        JPushInterface.stopPush(getApplicationContext());
                    }else{
                        msgflag = 2;
                        msg.setChecked(true);
//                        JPushInterface.resumePush(getApplicationContext());
                    }
            }
        });
        receiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (reflag==1) {
                    reflag = 2;
                    receiver.setChecked(false);
                    editor.putInt("isreceiver",2);
                }else{
                    reflag = 2;
                    receiver.setChecked(true);
                    editor.putInt("isreceiver",1);
                }
                editor.commit();
            }
        });
        try {
            cache.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cleanCache.setOnClickListener(this);
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        xgmm.setOnClickListener(this);
        ll_verson.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        contentQQ.setOnClickListener(this);
        MyApplication.getInstance().addActivity(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                SharedPreferences sharedPreferences2 = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                AlertDialog.Builder builder = new AlertDialog.Builder(SZActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认退出当前账号？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        editor.clear();
                        editor2.clear();
                        editor.commit();
                        editor2.commit();
                        MyApplication.isLogin = false;
                        startActivity(new Intent(SZActivity.this, LoginActivity.class));
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
                startActivity(new Intent(SZActivity.this, XMActivity.class));
                break;
            case R.id.ll_content:
                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=3547263117";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "请检查是否安装QQ", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_verson:
                Beta.checkUpgrade();
                break;
            case R.id.ll_about:
                startActivity(new Intent(SZActivity.this, AboutActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * @return 当前应用的版本号
     */
    public String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (msgflag==1) {
            editor.putInt("msg",msgflag);
        }else{
            editor.putInt("msg",msgflag);
        }
        if (reflag==1) {
            editor.putInt("receiver",reflag);
        }else{
            editor.putInt("receiver",reflag);
        }
        editor.commit();
    }
}
