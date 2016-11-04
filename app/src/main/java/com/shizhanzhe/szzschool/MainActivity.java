package com.shizhanzhe.szzschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.LoginBean;
import com.shizhanzhe.szzschool.fragment.FragmentCenter;
import com.shizhanzhe.szzschool.fragment.FragmentMore;
import com.shizhanzhe.szzschool.fragment.FragmentUser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg)
    RadioGroup rg;


    private FragmentCenter fragmentCenter;
    private FragmentMore fragmentMore;
    private FragmentUser fragmentUser;

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
        //拼接后半部分地址
        String ut = "uid=" + uid + "&token=" + token;
//        Log.i("=====", ut + "______" + username + "____" + img + "____" + uid + "____" + token+"___"+vip);

        fragmentUser = new FragmentUser().newInstance(username, img, vip, uid, ut);
        fragmentCenter = new FragmentCenter().newInstance(ut);
        fragmentMore = new FragmentMore();

        transaction.add(R.id.fragment, fragmentCenter);
        transaction.add(R.id.fragment, fragmentUser);
        transaction.add(R.id.fragment, fragmentMore);
        transaction.commit();
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.center:
                transaction.show(fragmentCenter);
                transaction.hide(fragmentUser);
                transaction.hide(fragmentMore);
                break;
            case R.id.course:
                transaction.hide(fragmentCenter);
                transaction.show(fragmentUser);
                transaction.hide(fragmentMore);
                break;
            case R.id.more:
                transaction.hide(fragmentCenter);
                transaction.hide(fragmentUser);
                transaction.show(fragmentMore);
                break;
        }
        transaction.commit();
    }
}
