package com.shizhanzhe.szzschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.TabAdapter;
import com.shizhanzhe.szzschool.fragment.TabLayoutFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2017/6/27.
 */
@ContentView(R.layout.activity_videolist)
public class ProjectDetailActivity extends FragmentActivity {
    @ViewInject(R.id.tab)
    TabLayout tab;
    @ViewInject(R.id.projectName)
    TextView name;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.viewpager)
    ViewPager viewpager;
    public static final String[] tabTitle = new String[]{"理论", "实战", "拓展", "融合", "精彩直播"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            fragments.add(TabLayoutFragment.newInstance(i + 1));
        }
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(getIntent().getStringExtra("name"));
    }

}

