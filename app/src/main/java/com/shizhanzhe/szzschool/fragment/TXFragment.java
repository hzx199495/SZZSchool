package com.shizhanzhe.szzschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.Bean.WLTG;
import com.shizhanzhe.szzschool.Bean.XKT;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.adapter.THAdapter;
import com.shizhanzhe.szzschool.adapter.WLTGAdapter;
import com.shizhanzhe.szzschool.adapter.XKTAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz9527 on 2018/7/10.
 */
@ContentView(R.layout.fragment_tx)
public class TXFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gridview_wltg)
    MyGridView gv_wltg;
    @ViewInject(R.id.gridview_wlyx)
    MyGridView gv_wlyx;
    @ViewInject(R.id.gridview_zyts)
    MyGridView gv_zyts;
    @ViewInject(R.id.center_swip)
    VpSwipeRefreshLayout swip;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;

    private List<WLTG> wltg;
    private List<ProBean.TxBean> yx;
    private List<ProBean.TxBean> ts;
    private QMUITipDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        getData();
        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
    }

    public void getData() {
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
                    List<ProBean.TxBean> list = gson.fromJson(json, ProBean.class).getTx();
                    yx = new ArrayList<>();
                    ts = new ArrayList<>();
                    for (ProBean.TxBean bean : list
                            ) {
                        if (bean.getCatid().equals("41")) {
                            yx.add(bean);
                        } else if (bean.getCatid().equals("42")) {
                            ts.add(bean);
                        }
                    }

                    GVAdapter yxAdapter = new GVAdapter(yx, getContext());
                    GVAdapter tsAdapter = new GVAdapter(ts, getContext());
                    gv_wlyx.setAdapter(yxAdapter);
                    gv_zyts.setAdapter(tsAdapter);
                    dialog.dismiss();
                    swip.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }
            }
        });

        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.WLTG(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


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
                    wltg = gson.fromJson(json, new TypeToken<List<WLTG>>() {
                    }.getType());
                    WLTGAdapter wltgAdapter = new WLTGAdapter(wltg, getContext());
                    gv_wltg.setAdapter(wltgAdapter);
                    dialog.dismiss();
                    swip.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getData();
                        }
                    });
                }
            }
        });


        gv_wlyx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (yx.get(position).getStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    String proid = yx.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                } else if (yx.get(position).getStatus().equals("1")) {
                    Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gv_wltg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (wltg.get(position).getStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    String proid = wltg.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                } else if (wltg.get(position).getStatus().equals("1")) {
                    Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gv_zyts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (ts.get(position).getStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    String proid = ts.get(position).getId();
                    intent.putExtra("id", proid);
                    startActivity(intent);
                } else if (ts.get(position).getStatus().equals("1")) {
                    Toast.makeText(getContext(), "课程未开放", Toast.LENGTH_SHORT).show();
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
