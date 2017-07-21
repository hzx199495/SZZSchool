package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.Bean.ProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumBKLVAdapter;
import com.shizhanzhe.szzschool.adapter.ForumLVAdapter;
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
public class ForumBKActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    @ViewInject(R.id.bk_title)
    TextView title;
    @ViewInject(R.id.bk_lv)
    ListView lv;
    @ViewInject(R.id.puttext)
    TextView puttext;
    @ViewInject(R.id.back)
    ImageView back;
    List<BKBean> list;
    String qx = "";
    int page = 1;
    String fid;
    SVProgressHUD mSVProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
        final String uid = preferences.getString("uid", "");
        final String token = preferences.getString("token", "");
        init();
        setListener();
        final Intent intent = getIntent();
        fid = intent.getStringExtra("fid");
        String name = intent.getStringExtra("name");
        title.setText(name);
        bought(fid,uid);
        getData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (qx.contains("1")) {
                    String title = list.get(position).getSubject();
                    String name = list.get(position).getRealname();
                    String time = list.get(position).getDateline();
                    String pid = list.get(position).getPid();
                    String logo = list.get(position).getLogo();
                    String rep = list.get(position).getAlltip();
                    String authorid = list.get(position).getAuthorid();

                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                        @Override
                        public void onsendJson(String json) {

                        }
                    });
                    flag=true;
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
                    new SVProgressHUD(ForumBKActivity.this).showInfoWithStatus("未购买该体系,无法查看");
                }
            }
        });
        puttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
                String jy = preferences.getString("jy","");
                if (jy.contains("0")){
                    new SVProgressHUD(ForumBKActivity.this).showErrorWithStatus("已被禁言，无法发帖");
                }else{
                    Intent i = new Intent();
                    i.setClass(ForumBKActivity.this, PostActivity.class);
                    i.putExtra("fid", fid);
                    startActivity(i);
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

    RefreshLayout swipeLayout;

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
    void bought(String fid,String uid) {
        OkHttpDownloadJsonUtil.downloadJson(this, "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
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
        mSVProgressHUD = new SVProgressHUD(this);
        mSVProgressHUD.showWithStatus("加载中...");
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMBK(fid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();
                list = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                }.getType());
                adapter = new ForumBKLVAdapter(ForumBKActivity.this, list);
                lv.setAdapter(adapter);
                mSVProgressHUD.dismiss();
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
                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.FORUMBK(fid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd")
                                .create();
                        List<BKBean> lists = gson.fromJson(json, new TypeToken<List<BKBean>>() {
                        }.getType());
                        for (BKBean b :
                                lists) {
                            list.add(b);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 2000);
    }
boolean flag=false;
    @Override
    protected void onResume() {
        super.onResume();
        if (flag){
            flag=false;
            onRefresh();
        }

    }
}
