package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.MyCTBean;
import com.shizhanzhe.szzschool.Bean.MyKTBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.TGDetailActivity;
import com.shizhanzhe.szzschool.adapter.MyCTAdapter;
import com.shizhanzhe.szzschool.adapter.MyKTAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/6/13.
 */
@ContentView(R.layout.fragment_kc)
public class MyTGFragment extends Fragment {
    @ViewInject(R.id.lv)
    ListView lv;

    private int type;
    @ViewInject(R.id.state_layout)
    StateLayout state_layout;
    @ViewInject(R.id.ll)
    LinearLayout ll;

    public static MyTGFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        MyTGFragment fragment = new MyTGFragment();
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
        state_layout.setTipText(StateLayout.EMPTY," ");
        state_layout.showLoadingView();
        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                state_layout.showLoadingView();
                getData(type);
            }

            @Override
            public void loginClick() {

            }
        });
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        getData(type);
    }


    void getData(int type) {
        if (type == 1) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYKT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                @Override
                public void onsendJson(String json) {
                    try {
                        if (json.equals("0")){
                            state_layout.showNoNetworkView();
                            return;
                        }else if (json.equals("1")){
                            state_layout.showTimeoutView();
                            return;
                        }
                        Gson gson = new Gson();
                        final List<MyKTBean> kclist = gson.fromJson(json, new TypeToken<List<MyKTBean>>() {
                        }.getType());
                        if (kclist != null && kclist.size() > 0) {
                            lv.setAdapter(new MyKTAdapter(getContext(), kclist));
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), TGDetailActivity.class);
                                    intent.putExtra("kt", 1);
                                    intent.putExtra("tuanid", kclist.get(position).getTuanid());
                                    intent.putExtra("type", 1);
                                    getContext().startActivity(intent);
                                    state_layout.showCustomView(ll);

                                }
                            });
                        }else {
                            state_layout.showEmptyView();
                        }


                    } catch (Exception e) {

                        state_layout.showErrorView();
                    }

                }
            });

        } else if (type == 2) {
            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).MYCT(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                @Override
                public void onsendJson(String json) {
                    try {
                        Gson gson = new Gson();
                        final List<MyCTBean> ctlist = gson.fromJson(json, new TypeToken<List<MyCTBean>>() {
                        }.getType());
                        if (ctlist != null && ctlist.size() > 0) {
                            lv.setAdapter(new MyCTAdapter(getContext(), ctlist));
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), TGDetailActivity.class);
                                    intent.putExtra("ct", 1);
                                    intent.putExtra("tuanid", ctlist.get(position).getTuanid());
                                    intent.putExtra("type", 2);
                                    getContext().startActivity(intent);
                                    state_layout.showCustomView(ll);
                                }
                            });
                        }else {
                            state_layout.showEmptyView();
                        }
                    } catch (Exception e) {
//                        Toast.makeText(getContext(), "数据异常", Toast.LENGTH_SHORT).show();
                        state_layout.showErrorView();
                    }
                }
            });
        }
    }


}




