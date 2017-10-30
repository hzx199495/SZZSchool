package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKLVAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by hasee on 2016/12/29.
 * 版块
 */
@ContentView(R.layout.activity_bk)
public class ForumBKActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    @ViewInject(R.id.bk_title)
    TextView title;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.bkrg)
    RadioGroup rg;
    @ViewInject(R.id.newlist)
    RadioButton newlist;
    @ViewInject(R.id.hotlist)
    RadioButton hotlist;
    @ViewInject(R.id.bk_lv)
    ListView lv;

    @ViewInject(R.id.fab_add)
    FloatingActionButton fab_add;
    @ViewInject(R.id.state_layout)
    StateLayout state_layout;
    private List<BKBean> list;
    private String qx = "";
    private int page = 1;
    private String fid;
    private int type;
    private RefreshLayout swipeLayout;
    private String uid;
    private String txId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        Intent intent = getIntent();
        fid = intent.getStringExtra("fid");
        String name = intent.getStringExtra("name");
        txId = getIntent().getStringExtra("txId");
        title.setText(name);

        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        type = 1;
        init();
        setListener();
        getData();
        state_layout.setTipText(StateLayout.EMPTY, "");
        state_layout.showLoadingView();
        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
            @Override
            public void refreshClick() {
                state_layout.showLoadingView();
                getData();
            }

            @Override
            public void loginClick() {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {
                    SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String vip = preferences.getString("vip", "");
                    if (vip.contains("1") || qx.contains("1")) {
                        String title = list.get(position).getSubject();
                        String name = list.get(position).getRealname();
                        String time = list.get(position).getDateline();
                        String pid = list.get(position).getPid();
                        String logo = list.get(position).getLogo();
                        String rep = list.get(position).getAlltip();
                        String authorid = list.get(position).getAuthorid();

                        OkHttpDownloadJsonUtil.downloadJson(ForumBKActivity.this, "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                            @Override
                            public void onsendJson(String json) {

                            }
                        });
                        Intent intent = new Intent(ForumBKActivity.this, ForumItemActivity.class);
                        intent.putExtra("pid", pid);
                        intent.putExtra("title", title);
                        intent.putExtra("name", name);
                        intent.putExtra("img", logo);
                        intent.putExtra("time", time);
                        intent.putExtra("rep", rep);
                        intent.putExtra("fid", fid);
                        intent.putExtra("authorid", authorid);
                        startActivity(intent);
                    } else {
                        buyPro();
                    }
                } else {

                    Toast.makeText(ForumBKActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForumBKActivity.this, LoginActivity.class));
                }

            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    SharedPreferences preferences = ForumBKActivity.this.getSharedPreferences("userjson", Context.MODE_PRIVATE);
                    String jy = preferences.getString("jy", "");
                    String vip = preferences.getString("vip", "");
                    if (jy.contains("0")) {
                        new SVProgressHUD(ForumBKActivity.this).showErrorWithStatus("已被禁言，无法发帖");
                    } else if (vip.contains("1") || qx.contains("1")) {
                        flag = true;
                        Intent i = new Intent();
                        i.setClass(ForumBKActivity.this, PostActivity.class);
                        i.putExtra("fid", fid);
                        startActivity(i);
                    } else {
                        buyPro();
                    }
                } else {

                    Toast.makeText(ForumBKActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForumBKActivity.this, LoginActivity.class));
                }

            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.newlist:
                        newlist.setTextColor(Color.BLUE);
                        hotlist.setTextColor(Color.BLACK);
                        type = 1;
                        page = 1;
                        state_layout.showLoadingView();
                        getData();
                        break;
                    case R.id.hotlist:
                        newlist.setTextColor(Color.BLACK);
                        hotlist.setTextColor(Color.BLUE);
                        type = 2;
                        page = 1;
                        state_layout.showLoadingView();
                        getData();
                        break;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void init() {
        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    //购买权限
    void bought(String fid, String uid) {
        OkHttpDownloadJsonUtil.downloadJson(ForumBKActivity.this, "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("1")) {
                    qx = "1";
                }
            }
        });
    }

    ForumBKLVAdapter adapter;

    void getData() {
        OkHttpDownloadJsonUtil.downloadJson(ForumBKActivity.this, Path.FORUMBK(fid, page, type), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        state_layout.showNoNetworkView();
                        return;
                    } else if (json.equals("1")) {
                        state_layout.showTimeoutView();
                        return;
                    }
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd")
                            .create();
                    list = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        adapter = new ForumBKLVAdapter(ForumBKActivity.this, list);
                        lv.setAdapter(adapter);
                        state_layout.showContentView();
                    } else {
                        state_layout.showEmptyView();
                    }
                } catch (Exception e) {
                    state_layout.showErrorView();
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                page = 1;
                getData();
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setLoading(false);
                page++;
                OkHttpDownloadJsonUtil.downloadJson(ForumBKActivity.this, Path.FORUMBK(fid, page, type), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd")
                                .create();
                        List<BKBean> lists = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                        }.getType());
                        if (lists.size() == 0) {
                            Toast.makeText(ForumBKActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
                        } else {
                            for (BKBean b :
                                    lists) {
                                list.add(b);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, 2000);
    }

    boolean flag = false;

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            flag = false;
            onRefresh();
        }
        bought(fid, uid);
    }

    void buyPro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForumBKActivity.this).setTitle("无权限")
                .setMessage("未购买该体系,是否前往购买")
                .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(ForumBKActivity.this, DetailActivity.class);
                        intent.putExtra("id", txId);
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
}
