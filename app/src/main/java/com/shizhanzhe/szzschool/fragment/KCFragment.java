package com.shizhanzhe.szzschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.adapter.THAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/6/12.
 */
@ContentView(R.layout.fragment_kc)
public class KCFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gv)
    MyGridView gv;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.swipe_container)
    VpSwipeRefreshLayout swip;
    private List<ProBean.TgBean> tg;
    private QMUITipDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
    }

    void getData() {
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).CENTER(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                        return;
                    }
                    Gson gson = new Gson();
                    tg = gson.fromJson(json, ProBean.class).getTg();
                    SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String ktagent = preferences.getString("ktagent", "");
                    TGAdapter tgAdapter = new TGAdapter(null, tg, getContext(), ktagent);
                    gv.setNumColumns(1);
                    gv.setAdapter(tgAdapter);
                    dialog.dismiss();
                } catch (Exception e) {
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                swip.setRefreshing(false);
            }
        }, 2000);
    }
}
