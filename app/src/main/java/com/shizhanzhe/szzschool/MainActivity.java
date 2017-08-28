package com.shizhanzhe.szzschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.SearchActivity;
import com.shizhanzhe.szzschool.fragment.FragmentCenter;
import com.shizhanzhe.szzschool.fragment.FragmentForum;
import com.shizhanzhe.szzschool.fragment.FragmentKCCenter;
import com.shizhanzhe.szzschool.fragment.FragmentQuestion;
import com.shizhanzhe.szzschool.fragment.FragmentUser;
import com.shizhanzhe.szzschool.update.UpdateManager;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.zackratos.ultimatebar.UltimateBar;


@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    // 定义4个Fragment对象
    private FragmentCenter fg1;
    private FragmentForum fg4;
    private FragmentUser fg5;
    private FragmentKCCenter fg2;
    private FragmentQuestion fg3;
    private FragmentManager fragmentManager;
    @ViewInject(R.id.search_tv)
    TextView tv;

    // 定义每个选项中的相关控件
    @ViewInject(R.id.first_layout)
    RelativeLayout firstLayout;
    @ViewInject(R.id.second_layout)
    RelativeLayout secondLayout;
    @ViewInject(R.id.third_layout)
    RelativeLayout thirdLayout;
    @ViewInject(R.id.fourth_layout)
    RelativeLayout fourthLayout;
    @ViewInject(R.id.fifth_layout)
    RelativeLayout fifthLayout;
    @ViewInject(R.id.first_image)
    ImageView firstImage;
    @ViewInject(R.id.second_image)
    ImageView secondImage;
    @ViewInject(R.id.third_image)
    ImageView thirdImage;
    @ViewInject(R.id.fourth_image)
    ImageView fourthImage;
    @ViewInject(R.id.fifth_image)
    ImageView fifthImage;
    @ViewInject(R.id.first_text)
    TextView firstText;
    @ViewInject(R.id.second_text)
    TextView secondText;
    @ViewInject(R.id.third_text)
    TextView thirdText;
    @ViewInject(R.id.fourth_text)
    TextView fourthText;
    @ViewInject(R.id.fifth_text)
    TextView fifthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.top));



        new UpdateManager(this).checkUpdate(true);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        fragmentManager=getSupportFragmentManager();

        firstLayout.setOnClickListener(this);
        secondLayout.setOnClickListener(this);
        thirdLayout.setOnClickListener(this);
        fourthLayout.setOnClickListener(this);
        fifthLayout.setOnClickListener(this);
        setChioceItem(0); // 初始化页面加载时显示第一个选项卡
        MyApplication.getInstance().addActivity(this);
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            case R.id.fifth_layout:
                setChioceItem(4);
            default:
                break;
        }
    }
    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3,4
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
                firstText.setTextColor(getResources().getColor(R.color.blue2));
                firstImage.setImageResource(R.drawable.ic_home_home_hover);
// 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new FragmentCenter();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
                secondText.setTextColor(getResources().getColor(R.color.blue2));
                secondImage.setImageResource(R.drawable.ic_home_more_hover);

                if (fg2 == null) {
                    fg2 = new FragmentKCCenter();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                thirdText.setTextColor(getResources().getColor(R.color.blue2));
                thirdImage.setImageResource(R.drawable.about_question);
                if (fg3 == null) {
                    fg3 = new FragmentQuestion();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:
                fourthText.setTextColor(getResources().getColor(R.color.blue2));
                fourthImage.setImageResource(R.drawable.ic_home_forum_hover);
                if (fg4 == null) {
                    fg4 = new FragmentForum();
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                break;
            case 4:
                fifthText.setTextColor(getResources().getColor(R.color.blue2));
                fifthImage.setImageResource(R.drawable.ic_home_mycourse_hover);
                if (fg5 == null) {
                    fg5 = new FragmentUser();
                    fragmentTransaction.add(R.id.content, fg5);
                } else {
                    fragmentTransaction.show(fg5);
                }
                break;
        }
        fragmentTransaction.commit(); // 提交
    }
    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
        firstText.setTextColor(getResources().getColor(R.color.huise));
        secondText.setTextColor(getResources().getColor(R.color.huise));
        thirdText.setTextColor(getResources().getColor(R.color.huise));
        fourthText.setTextColor(getResources().getColor(R.color.huise));
        fifthText.setTextColor(getResources().getColor(R.color.huise));
        firstImage.setImageResource(R.drawable.ic_home_home);
        secondImage.setImageResource(R.drawable.ic_home_more);
        thirdImage.setImageResource(R.drawable.about_question2);
        fourthImage.setImageResource(R.drawable.ic_home_forum);
        fifthImage.setImageResource(R.drawable.ic_home_mycourse);

    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
        if (fg5 != null) {
            fragmentTransaction.hide(fg5);
        }
    }
}
