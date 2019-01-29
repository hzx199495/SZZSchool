package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.BannerBean;
import com.shizhanzhe.szzschool.Bean.ForumBean;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.Bean.THBean;
import com.shizhanzhe.szzschool.Bean.WLTG;
import com.shizhanzhe.szzschool.Bean.XKT;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.EveryProjectActivity;
import com.shizhanzhe.szzschool.activity.ForumItemActivity;
import com.shizhanzhe.szzschool.activity.ForumTypeActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.ProjectListActivity;
import com.shizhanzhe.szzschool.activity.UserZHActivity;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
import com.shizhanzhe.szzschool.adapter.GVAdapter;
import com.shizhanzhe.szzschool.adapter.TGAdapter;
import com.shizhanzhe.szzschool.adapter.THAdapter;
import com.shizhanzhe.szzschool.adapter.WLTGAdapter;
import com.shizhanzhe.szzschool.adapter.XKTAdapter;
import com.shizhanzhe.szzschool.utils.GlideImageLoader;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvPlayerActivity;
import com.shizhanzhe.szzschool.widge.VpSwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by hasee on 2016/10/26.
 */
@ContentView(R.layout.fragment_center)
public class FragmentCenter extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.gridview_th)
    MyGridView gv_th;
    @ViewInject(R.id.gridview_zyts)
    MyGridView gv_zyts;
    @ViewInject(R.id.gridview_tg)
    MyGridView gv_tg;
    @ViewInject(R.id.gv_forum)
    MyListView gv_forum;
    @ViewInject(R.id.th_more)
    TextView th_more;
    @ViewInject(R.id.zyts_more)
    TextView zyts_more;
    @ViewInject(R.id.banner)
    Banner banner;
    @ViewInject(R.id.center_swip)
    VpSwipeRefreshLayout swip;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.iv)
    ImageView iv;

    private QMUITipDialog dialog;
    private List<ProBean.TgBean> tg;
    private List<ForumBean.SzanBean> szan;
    private View rootview;
    private String every;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            // 是否设置为圆角，弧度为多少，当弧度为90时显示的是一个圆
            .displayer(new RoundedBitmapDisplayer(15))
            .showImageOnLoading(R.drawable.img_load)
            .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
            .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
            .build();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = x.view().inject(this, inflater, null);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        final String vip = preferences.getString("vip", "");
        getData();
        if (MyApplication.isLogin) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MyApplication.every = every;
                        JSONObject object = new JSONObject(every);
                        Intent intent = EveryProjectActivity.newIntent(getContext(), EveryProjectActivity.PlayMode.portrait, object.optString("vid"));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        th_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProjectListActivity.class);
                intent.putExtra("listth", (Serializable) th);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        zyts_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProjectListActivity.class);
                intent.putExtra("listts", (Serializable) ts);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
        gv_forum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {
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
                                if (szan.get(position).getFid().equals("76")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
                                            .setMessage("未参与头脑风暴活动,请联系助教老师微信:szz892")
                                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    builder.create().show();
                                } else {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
//                                            .setMessage("未购买该体系,是否前往购买")
//                                            .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Intent intent = new Intent();
//                                                    intent.setClass(getActivity(), DetailActivity.class);
//                                                    intent.putExtra("id", proid);
//                                                    startActivity(intent);
//                                                }
//                                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    builder.create().show();
                                }

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


        swip.setOnRefreshListener(this);
        swip.setColorSchemeResources(R.color.blue2, R.color.red, R.color.green_color, R.color.dimgray);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                swip.setRefreshing(false);
            }
        }, 3000);
    }

    List<ProBean.TxBean> ts;
    List<THBean.ShowBean> th;

    public void getData() {
        empty.hide();
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
                    tg = gson.fromJson(json, ProBean.class).getTg();
//                    yx = new ArrayList<>();
                    ts = new ArrayList<>();
                    for (ProBean.TxBean bean : list
                            ) {
                        if (bean.getCatid().equals("41")) {
//                            yx.add(bean);
                        } else if (bean.getCatid().equals("42")) {
                            ts.add(bean);
                        }
                    }
                    List newts = ts.subList(0, 4);
                    GVAdapter tsAdapter = new GVAdapter(newts, getContext());
                    gv_zyts.setAdapter(tsAdapter);
                    gv_zyts.setFocusable(false);
                    SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String ktagent = preferences.getString("ktagent", "");
                    TGAdapter tgAdapter = new TGAdapter(null, tg, getContext(), ktagent);
                    gv_tg.setAdapter(tgAdapter);
                    gv_tg.setFocusable(false);
                    TH();
                    setBanner();
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
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.EVERY(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


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
                    every = json;
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

        gv_th.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailActivity.class);
                String proid = th.get(position).getId();
                intent.putExtra("id", proid);
                startActivity(intent);

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

    private void TH() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.TH(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
                    final THBean thBean = gson.fromJson(json, THBean.class);
                    th = thBean.getShow();
                    List newth = th.subList(0, 4);
                    gv_th.setAdapter(new THAdapter(newth, getContext()));
                    gv_th.setFocusable(false);
                    getForum();
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
    }

    private void getForum() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.FORUMHOME(), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(final String json) {
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
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    szan = gson.fromJson(json, ForumBean.class).getSzan();
                    ForumLVAdapter lvadapter = new ForumLVAdapter(getContext(), szan);
                    gv_forum.setAdapter(lvadapter);
                    gv_forum.setFocusable(false);
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
    }

    String qx = "";

    //权限
    private void bought(String fid, String uid) {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {

                } catch (Exception e) {
                    Toast.makeText(getContext(), "获取权限失败", Toast.LENGTH_SHORT).show();
                }
                if (json.contains("1")) {
                    qx = "1";
                }
            }
        });
    }

    private void setBanner() {
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), "https://shizhanzhe.com/index.php?m=pcdata.haibao1", new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {

                    Gson gson = new Gson();
                    final List<BannerBean> bean = gson.fromJson(json, new TypeToken<List<BannerBean>>() {
                    }.getType());
                    ArrayList<String> images = new ArrayList<>();
                    for (BannerBean b : bean
                            ) {
                        images.add(b.getImg());
                    }
                    banner.setDelayTime(3000);
                    banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            if (bean.get(position - 1).getId() != 0) {
                                Intent intent = new Intent();
                                intent.setClass(getActivity(), DetailActivity.class);
                                String proid = bean.get(position - 1).getId() + "";
                                intent.putExtra("id", proid);
                                startActivity(intent);
                            }

                        }

                    });
                    banner.setFocusable(true);
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
