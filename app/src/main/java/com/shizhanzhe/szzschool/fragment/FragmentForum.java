package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.ForumBKActivity;
import com.shizhanzhe.szzschool.activity.ForumItemActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.adapter.ForumBKAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragment_forum)
public class FragmentForum extends Fragment {
    @ViewInject(R.id.gv_bk)
    MyGridView bk;
    @ViewInject(R.id.gv_forum)
    MyListView gv;
    private List<ForumBean.LtmodelBean> ltmodel;
    private List<ForumBean.SzanBean> szan;
@ViewInject(R.id.state_layout)
    StateLayout state_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state_layout.showLoadingView();
        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                getdata();
            }

            @Override
            public void loginClick() {

            }
        });
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        final String vip = preferences.getString("vip", "");
        getdata();
        bk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String fid = ltmodel.get(position).getFid();
                String name = ltmodel.get(position).getName();
                String txId = ltmodel.get(position).getSystemid();
                if (vip.equals("1")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ForumBKActivity.class);
                    intent.putExtra("fid", fid);
                    intent.putExtra("name", name);
                    intent.putExtra("txId", txId);
                    startActivity(intent);
                } else {
                    if (MyApplication.isLogin) {
                        if (fid.equals("58")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
                                    .setMessage("未开通VIP，前往账户中心开通")
                                    .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getContext(), UserZHActivity.class));
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), ForumBKActivity.class);
                            intent.putExtra("fid", fid);
                            intent.putExtra("name", name);
                            intent.putExtra("txId", txId);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            }

        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {

                    final String proid = txid.get(szan.get(position).getFid());
                    if (vip.equals("1")) {
                        String title = szan.get(position).getSubject();
                        String name = szan.get(position).getRealname();
                        String time = szan.get(position).getDateline();
                        String pid = szan.get(position).getPid();
                        String fid = szan.get(position).getFid();
                        String logo = szan.get(position).getLogo();
                        String rep = szan.get(position).getAlltip();
                        OkHttpDownloadJsonUtil.downloadJson(getContext(), "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {

                            }
                        });
                        Intent intent = new Intent(getActivity(), ForumItemActivity.class);
                        intent.putExtra("pid", pid);
                        intent.putExtra("title", title);
                        intent.putExtra("name", name);
                        intent.putExtra("img", logo);
                        intent.putExtra("time", time);
                        intent.putExtra("rep", rep);
                        intent.putExtra("fid", fid);
                        startActivity(intent);
                    } else {
                        if (!szan.get(position).getFid().contains("58")) {
                            bought(szan.get(position).getFid(), uid);
                            if (qx.contains("1")) {
                                String title = szan.get(position).getSubject();
                                String name = szan.get(position).getRealname();
                                String time = szan.get(position).getDateline();
                                String pid = szan.get(position).getPid();
                                String logo = szan.get(position).getLogo();
                                String rep = szan.get(position).getAlltip();
                                String authorid = szan.get(position).getAuthorid();

                                OkHttpDownloadJsonUtil.downloadJson(getContext(), "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                                    @Override
                                    public void onsendJson(String json) {

                                    }
                                });
                                Intent intent = new Intent(getContext(), ForumItemActivity.class);
                                intent.putExtra("pid", pid);
                                intent.putExtra("title", title);
                                intent.putExtra("name", name);
                                intent.putExtra("img", logo);
                                intent.putExtra("time", time);
                                intent.putExtra("rep", rep);
                                intent.putExtra("fid", szan.get(position).getFid());
                                intent.putExtra("authorid", authorid);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
                                        .setMessage("未购买该体系,是否前往购买")
                                        .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setClass(getActivity(), DetailActivity.class);
                                                intent.putExtra("id", proid);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create().show();
                            }
                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
                                    .setMessage("未开通VIP，前往账户中心开通")
                                    .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(getContext(), UserZHActivity.class));
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();

                        }
                    }
                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
        gv.setFocusable(false);
    }

    HashMap<String, String> txid;

    private void getdata() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    ltmodel = gson.fromJson(json, ForumBean.class).getLtmodel();
                    szan = gson.fromJson(json, ForumBean.class).getSzan();
                    txid = new HashMap<String, String>();
                    for (ForumBean.LtmodelBean bean : ltmodel
                            ) {
                        txid.put(bean.getFid(), bean.getSystemid());
                    }
                    ForumBKAdapter bkadapter = new ForumBKAdapter(getContext(), ltmodel);
                    ForumLVAdapter lvadapter = new ForumLVAdapter(getContext(), szan);
                    bk.setAdapter(bkadapter);
                    gv.setAdapter(lvadapter);
                    state_layout.showContentView();
                } catch (Exception e) {
                    state_layout.showErrorView();
                }

            }
        });
    }

    String qx = "";

    //购买权限
    void bought(String fid, String uid) {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {

                }catch (Exception e){
                    Toast.makeText(getContext(), "获取权限失败", Toast.LENGTH_SHORT).show();
                }
                if (json.contains("1")) {
                    qx = "1";
                }
            }
        });
    }
}
