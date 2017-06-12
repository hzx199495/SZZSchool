package com.shizhanzhe.szzschool;

import android.content.Intent;
import android.os.Bundle;
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
    @ViewInject(R.id.center)
    RadioButton center;
    private FragmentCenter fragmentCenter;
    private FragmentForum fragmentForum;
    private FragmentUser fragmentUser;
    private FragmentKCCenter fragmentKCCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
//        new UpdateManager(this).checkUpdate(true);
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        LoginBean loginData = gson.fromJson(data, LoginBean.class);
        String username = loginData.getUsername();
        String img = loginData.getHeadimg();
        String uid = loginData.getId();
        String token = loginData.getToken();
        String money=loginData.getMoney();
        String vip = loginData.getVip();

        MyApplication.username=username;
        MyApplication.myid=uid;
        MyApplication.token=token;
        MyApplication.img=img;
        MyApplication.money=money;

        fragmentUser = new FragmentUser().newInstance(username,img);
        fragmentCenter = new FragmentCenter();
        fragmentKCCenter = new FragmentKCCenter();
        fragmentForum = new FragmentForum();


        transaction.add(R.id.fragment, fragmentUser);
        transaction.add(R.id.fragment, fragmentKCCenter);
        transaction.add(R.id.fragment, fragmentForum);
        transaction.add(R.id.fragment, fragmentCenter);
        transaction.commit();
        rg.setOnCheckedChangeListener(this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getApplicationContext(), SearchActivity.class);
                startActivity(intent1);
            }
        });
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft=manager.beginTransaction();
                ft.show(fragmentCenter);
                ft.commit();
                center.setChecked(true);
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.center:
                transaction.show(fragmentCenter);
                transaction.hide(fragmentKCCenter);
                transaction.hide(fragmentUser);
                transaction.hide(fragmentForum);
                break;
            case R.id.fl:
                transaction.hide(fragmentCenter);
                transaction.show(fragmentKCCenter);
                transaction.hide(fragmentUser);
                transaction.hide(fragmentForum);
                break;
            case R.id.course:
                transaction.hide(fragmentCenter);
                transaction.hide(fragmentKCCenter);
                transaction.show(fragmentUser);
                transaction.hide(fragmentForum);
                break;
            case R.id.project:
                transaction.hide(fragmentCenter);
                transaction.hide(fragmentKCCenter);
                transaction.hide(fragmentUser);
                transaction.show(fragmentForum);
                break;
        }
        transaction.commit();
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

}
