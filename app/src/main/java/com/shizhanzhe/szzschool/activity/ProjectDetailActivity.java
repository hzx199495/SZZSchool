package com.shizhanzhe.szzschool.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ExpanAdapter;
import com.shizhanzhe.szzschool.adapter.TabAdapter;
import com.shizhanzhe.szzschool.fragment.TabLayoutFragment;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.mob.MobSDK.getContext;
import static com.shizhanzhe.szzschool.activity.MyApplication.position;
import static com.shizhanzhe.szzschool.activity.MyApplication.userType;

/**
 * Created by zz9527 on 2017/6/27.
 * 课程视频
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
    @ViewInject(R.id.expand_list)
    ExpandableListView expanView;
    public static  List<String> tabTitle=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        expanView.setVisibility(View.VISIBLE);
        MyApplication.userType=1;
        tabTitle.clear();
        viewpager.setVisibility(View.VISIBLE);
        tab.setVisibility(View.VISIBLE);
        final String json = getIntent().getStringExtra("json");
        Gson gson = new Gson();
        ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
        List<ProDeatailBean.CiBean> ci = gson.fromJson(json, ProDeatailBean.class).getCi();

        if (tx.getCatid().equals("41")){
            for (ProDeatailBean.CiBean bean:ci
                    ){
                tabTitle.add(bean.getCtitle());
            }
        }else{
            tabTitle.add("理论");
            tabTitle.add("精彩直播");
        }
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitle.size(); i++) {
            fragments.add(TabLayoutFragment.newInstance(i + 1,json));
        }
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments,tabTitle);
        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        if (tabTitle.size()<5){
            tab.setTabMode(TabLayout.MODE_FIXED);
        }


//        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
//        final String vip = preferences.getString("vip", "");
//        Gson gson = new Gson();
//        final List<ProDeatailBean.CiBean> list = gson.fromJson(json, ProDeatailBean.class).getCi();
//        ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
//        String txId = tx.getId();
//        final String isbuy = tx.getIsbuy();
//        final ExpanAdapter adapter = new ExpanAdapter(this, list,txId);
//        expanView.setAdapter(adapter);
//        //        设置分组项的点击监听事件
//        expanView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                // 请务必返回 false，否则分组不会展开
//                return false;
//            }
//        });
////        设置子选项点击监听事件
//        expanView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
//                final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(groupPosition).getChoice_kc();
//                if (MyApplication.isLogin) {
//                    if (vip.equals("1") || isbuy.equals("1")) {
//                        if (choice_kc.get(childPosition).getGrade().contains("2") || choice_kc.get(childPosition).getGrade().contains("1")) {
//                            position = childPosition;
//                            MyApplication.videotypeid = choice_kc.get(childPosition).getId();
//                            MyApplication.videotype = groupPosition;
//                            MyApplication.videoitemid = choice_kc.get(childPosition).getId();
//                            Intent intent = PolyvPlayerActivity.newIntent(ProjectDetailActivity.this, PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(childPosition).getMv_url(),json);
//                            startActivity(intent);
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ProjectDetailActivity.this);
//                            builder.setTitle("实战者学院提示");
//                            builder.setMessage("不建议越级观看，确认继续");
//                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    position = childPosition;
//                                    MyApplication.videotypeid = choice_kc.get(childPosition).getId();
//                                    MyApplication.videotype = groupPosition;
//                                    MyApplication.videoitemid = choice_kc.get(childPosition).getId();
//                                    Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(position).getMv_url(),json);
//                                    getContext().startActivity(intent);
//                                }
//                            });
//                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//
//                            });
//                            builder.create().show();
//                        }
//                    } else {
//                        new SVProgressHUD(ProjectDetailActivity.this).showErrorWithStatus("未购买课程无法学习", SVProgressHUD.SVProgressHUDMaskType.None);
//                    }
//                } else {
//                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getContext(), LoginActivity.class));
//                }
//                return true;
//            }
//        });
//        // 这里是控制只有一个group展开的效果
//        expanView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                for (int i = 0; i < adapter.getGroupCount(); i++) {
//                    if (groupPosition != i) {
//                        expanView.collapseGroup(i);
//                    }
//                }
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(getIntent().getStringExtra("name"));
    }

}

