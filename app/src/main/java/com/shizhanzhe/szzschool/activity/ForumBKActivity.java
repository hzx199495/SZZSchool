package com.shizhanzhe.szzschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.fragment.FragmentBK;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by hasee on 2016/12/29.
 * 版块
 */
@ContentView(R.layout.activity_bk)
public class ForumBKActivity extends FragmentActivity {
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
    FragmentBK newfragmentBK;
    FragmentBK hotfragmentBK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);

        Intent intent = getIntent();
        final String fid = intent.getStringExtra("fid");
        String name = intent.getStringExtra("name");
        title.setText(name);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentBK fragment = FragmentBK.newInstance(fid, 1);
        transaction.add(R.id.ll_bk, fragment);
        transaction.commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FragmentManager manager = getSupportFragmentManager();
                 FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId){
                    case R.id.newlist:
                        if (newfragmentBK==null) {
                            newfragmentBK = FragmentBK.newInstance(fid, 1);
                        }
                        newlist.setTextColor(Color.BLUE);
                        hotlist.setTextColor(Color.BLACK);
                        transaction.replace(R.id.ll_bk, newfragmentBK);
                        transaction.commit();

                        break;
                    case R.id.hotlist:
                        if (hotfragmentBK==null) {
                            hotfragmentBK = FragmentBK.newInstance(fid, 1);
                        }
                        newlist.setTextColor(Color.BLACK);
                        hotlist.setTextColor(Color.BLUE);
                         hotfragmentBK = FragmentBK.newInstance(fid, 2);
                        transaction.replace(R.id.ll_bk, hotfragmentBK);
                        transaction.commit();
                        break;
                }
            }
        });

//        bought(fid,uid);
//        getData();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SharedPreferences preferences =getSharedPreferences("userjson", Context.MODE_PRIVATE);
//                String vip = preferences.getString("vip", "");
//                if (vip.contains("1")||qx.contains("1")){
//                    String title = list.get(position).getSubject();
//                    String name = list.get(position).getRealname();
//                    String time = list.get(position).getDateline();
//                    String pid = list.get(position).getPid();
//                    String logo = list.get(position).getLogo();
//                    String rep = list.get(position).getAlltip();
//                    String authorid = list.get(position).getAuthorid();
//
//                    OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), "https://shizhanzhe.com/index.php?m=pcdata.add_num&pc=1&uid=" + uid + "&pid=" + pid + "+&token=" + token, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                        @Override
//                        public void onsendJson(String json) {
//
//                        }
//                    });
//                    Intent intent = new Intent(ForumBKActivity.this, ForumItemActivity.class);
//                    intent.putExtra("pid", pid);
//                    intent.putExtra("title", title);
//                    intent.putExtra("name", name);
//                    intent.putExtra("img", logo);
//                    intent.putExtra("time", time);
//                    intent.putExtra("rep", rep);
//                    intent.putExtra("fid", fid);
//                    intent.putExtra("authorid", authorid);
//                    startActivity(intent);
//                }else{
//                        new SVProgressHUD(ForumBKActivity.this).showInfoWithStatus("未购买该体系,无法查看");
//                }
//
//            }
//        });
//        fab_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
//                String jy = preferences.getString("jy","");
//                String vip = preferences.getString("vip", "");
//                if (jy.contains("0")){
//                    new SVProgressHUD(ForumBKActivity.this).showErrorWithStatus("已被禁言，无法发帖");
//                }else if (vip.contains("1")||qx.contains("1")){
//                    flag=true;
//                    Intent i = new Intent();
//                    i.setClass(ForumBKActivity.this, PostActivity.class);
//                    i.putExtra("fid", fid);
//                    startActivity(i);
//                }else {
//                    new SVProgressHUD(ForumBKActivity.this).showInfoWithStatus("未购买该体系,无法发帖");
//                }
//
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    RefreshLayout swipeLayout;
//
//    /**
//     * 初始化布局
//     */
//    @SuppressLint({"InlinedApi", "InflateParams"})
//    private void init() {
//        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
//        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
//    }
//
//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setOnLoadListener(this);
//    }
//
//    //购买权限
//    void bought(String fid,String uid) {
//        OkHttpDownloadJsonUtil.downloadJson(this, "https://shizhanzhe.com/index.php?m=pcdata.quanxian&pc=1&fid=" + fid + "&uid=" + uid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//            @Override
//            public void onsendJson(String json) {
//                if (json.contains("1")) {
//                    qx = "1";
//                }
//            }
//        });
//    }
//
//    ForumBKLVAdapter adapter;
//
//    void getData() {
//        mSVProgressHUD = new SVProgressHUD(this);
//        mSVProgressHUD.showWithStatus("加载中...");
//        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMBK(fid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//            @Override
//            public void onsendJson(String json) {
//                Gson gson = new GsonBuilder()
//                        .setDateFormat("yyyy-MM-dd")
//                        .create();
//                list = gson.fromJson(json, new TypeToken<List<BKBean>>() {
//                }.getType());
//                adapter = new ForumBKLVAdapter(ForumBKActivity.this, list);
//                lv.setAdapter(adapter);
//                mSVProgressHUD.dismiss();
//            }
//        });
//    }
//
//    @Override
//    public void onRefresh() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//                page = 1;
//                getData();
//                swipeLayout.setRefreshing(false);
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onLoad() {
//        swipeLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeLayout.setLoading(false);
//                page++;
//                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.FORUMBK(fid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                    @Override
//                    public void onsendJson(String json) {
//                        Gson gson = new GsonBuilder()
//                                .setDateFormat("yyyy-MM-dd")
//                                .create();
//                        List<BKBean> lists = gson.fromJson(json, new TypeToken<List<BKBean>>() {
//                        }.getType());
//                        for (BKBean b :
//                                lists) {
//                            list.add(b);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        }, 2000);
//    }
//
//boolean flag=false;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (flag){
//            flag=false;
//            onRefresh();
//        }
//
//    }
}
