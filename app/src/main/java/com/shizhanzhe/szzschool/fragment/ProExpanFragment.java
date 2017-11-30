package com.shizhanzhe.szzschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.ProjectDetailActivity;
import com.shizhanzhe.szzschool.adapter.ExpanAdapter;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;
import com.shizhanzhe.szzschool.widge.MyExpandaleListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.shizhanzhe.szzschool.activity.MyApplication.position;
import static com.shizhanzhe.szzschool.activity.MyApplication.userType;

/**
 * Created by zz9527 on 2017/11/6.
 */
@ContentView(R.layout.fragment_proexpan)
public class ProExpanFragment extends Fragment {
    @ViewInject(R.id.expand_list)
    MyExpandaleListView expanView;

    public static ProExpanFragment newInstance(String json, String sid) {

        Bundle args = new Bundle();
        args.putString("json", json);
        args.putString("sid", sid);
        ProExpanFragment fragment = new ProExpanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getContext().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String json = getArguments().getString("json");
        Gson gson = new Gson();
        final List<ProDeatailBean.CiBean> list = gson.fromJson(json, ProDeatailBean.class).getCi();
        final ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
        final ExpanAdapter adapter = new ExpanAdapter(getContext(), list, tx.getCatid());
        expanView.setAdapter(adapter);
        //        设置分组项的点击监听事件
        expanView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (userType == 1) {
                    return false;
                } else {
                    if (i == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
                // 请务必返回 false，否则分组不会展开
            }
        });
        if (tx.getCatid().contains("41")) {
            expanView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
                    intent.putExtra("name", tx.getStitle());
                    intent.putExtra("json", json);
                    startActivity(intent);
                    return true;
                }
            });

        } else {
            expanView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                    if (userType == 1) {
                        Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
                        intent.putExtra("name", tx.getStitle());
                        intent.putExtra("json", json);
                        startActivity(intent);
                    } else {
                        final List<ProDeatailBean.CiBean.ChoiceKcBean> choice_kc = list.get(0).getChoice_kc();
                        if (MyApplication.isLogin) {
                            if (groupPosition == 0 && choice_kc.size() > 11 && childPosition <= 11) {
                                position = childPosition;
                                MyApplication.videotypeid = choice_kc.get(childPosition).getId();
                                MyApplication.videotype = groupPosition + 1;
                                MyApplication.videoitemid = choice_kc.get(childPosition).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(childPosition).getMv_url(), json);
                                startActivity(intent);
                            } else if (groupPosition == 0 && choice_kc.size() < 11 && childPosition <= 6) {
                                position = childPosition;
                                MyApplication.videotypeid = choice_kc.get(childPosition).getId();
                                MyApplication.videotype = groupPosition + 1;
                                MyApplication.videoitemid = choice_kc.get(childPosition).getId();
                                Intent intent = PolyvPlayerActivity.newIntent(getContext(), PolyvPlayerActivity.PlayMode.portrait, choice_kc.get(childPosition).getMv_url(), json);
                                startActivity(intent);
                            }

                        } else {
                            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    }

                    return true;
                }

            });
        }
    }
}
