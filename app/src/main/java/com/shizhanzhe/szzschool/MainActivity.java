package com.shizhanzhe.szzschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.activity.SearchActivity;
import com.shizhanzhe.szzschool.fragment.FragmentCenter;
import com.shizhanzhe.szzschool.fragment.FragmentFl;
import com.shizhanzhe.szzschool.fragment.FragmentMyProject;
import com.shizhanzhe.szzschool.fragment.FragmentUser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg)
    RadioGroup rg;
    @ViewInject(R.id.search_tv)
    TextView tv;


    private FragmentCenter fragmentCenter;
    private FragmentMyProject fragmentMyProject;
    private FragmentUser fragmentUser;
    private FragmentFl fragmentFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Gson gson = new Gson();
        LoginBean loginData = gson.fromJson(data, LoginBean.class);
        String username = loginData.getUsername();
        String img = loginData.getHeadimg();
        String uid = loginData.getId();
        String token = loginData.getToken();
        String vip = loginData.getVip();
        fragmentUser = new FragmentUser().newInstance(username,img);
        fragmentCenter = new FragmentCenter().newInstance(uid,token);
        fragmentFl = new FragmentFl().newInstance(uid,token);
        fragmentMyProject = new FragmentMyProject().newInstance(uid,token);

        transaction.add(R.id.fragment, fragmentCenter);
        transaction.add(R.id.fragment, fragmentUser);
        transaction.add(R.id.fragment, fragmentFl);
        transaction.add(R.id.fragment, fragmentMyProject);
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
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.center:
                transaction.show(fragmentCenter);
                transaction.hide(fragmentFl);
                transaction.hide(fragmentUser);
                transaction.hide(fragmentMyProject);
                break;
            case R.id.fl:
                transaction.hide(fragmentCenter);
                transaction.show(fragmentFl);
                transaction.hide(fragmentUser);
                transaction.hide(fragmentMyProject);
                break;
            case R.id.course:
                transaction.hide(fragmentCenter);
                transaction.hide(fragmentFl);
                transaction.show(fragmentUser);
                transaction.hide(fragmentMyProject);
                break;
            case R.id.project:
                transaction.hide(fragmentCenter);
                transaction.hide(fragmentFl);
                transaction.hide(fragmentUser);
                transaction.show(fragmentMyProject);
                break;
        }
        transaction.commit();
    }
}
