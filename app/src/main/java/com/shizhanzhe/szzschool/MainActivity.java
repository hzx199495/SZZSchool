package com.shizhanzhe.szzschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SearchActivity;
import com.shizhanzhe.szzschool.fragment.FragmentCenter;
import com.shizhanzhe.szzschool.fragment.FragmentForum;
import com.shizhanzhe.szzschool.fragment.FragmentKCCenter;
import com.shizhanzhe.szzschool.fragment.FragmentUser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;


@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg)
    RadioGroup rg;
    @ViewInject(R.id.search_tv)
    TextView tv;
    @ViewInject(R.id.img_main)
    ImageView goMain;
    @ViewInject(R.id.main)
    RadioButton main;
    private FragmentCenter fragmentCenter;
    private FragmentForum fragmentForum;
    private FragmentUser fragmentUser;
    private FragmentKCCenter fragmentKCCenter;
    private FragmentManager manager;
    private Fragment nowFragment;
    String username;
    String img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
//        new UpdateManager(this).checkUpdate(true);
        manager = getSupportFragmentManager();


        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        LoginBean loginData = gson.fromJson(data, LoginBean.class);
         username = loginData.getUsername();
        img = loginData.getHeadimg();
        String uid = loginData.getId();
        String token = loginData.getToken();
        String money=loginData.getMoney();
        String vip = loginData.getVip();


        MyApplication.myid=uid;
        MyApplication.token=token;

        MyApplication.money=money;
        FragmentTransaction ft=manager.beginTransaction();
        if(nowFragment==null){
            nowFragment=new FragmentCenter();
        }
        ft.replace(R.id.fragment,nowFragment);
        ft.commit();
        rg.setOnCheckedChangeListener(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft=manager.beginTransaction();
                ft.show(fragmentCenter);
                ft.commit();
                main.setChecked(true);
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main:
                if(fragmentCenter==null){
                    fragmentCenter=new FragmentCenter();
                }
                switchContent(nowFragment,fragmentCenter);
                break;
            case R.id.kc:
                if(fragmentKCCenter==null){
                    fragmentKCCenter=new FragmentKCCenter();
                }
               switchContent(nowFragment,fragmentKCCenter);
                break;
            case R.id.course:
                if(fragmentUser==null){
                    fragmentUser=new FragmentUser().newInstance(username,img);
                }
                switchContent(nowFragment,fragmentUser);
                break;
            case R.id.forum:
                if(fragmentForum==null){
                    fragmentForum=new FragmentForum();
                }
                switchContent(nowFragment,fragmentForum);
                break;
        }
    }
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.fragment, to).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
        nowFragment=to;
    }
}
