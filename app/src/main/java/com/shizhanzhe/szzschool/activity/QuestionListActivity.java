package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.QuestionListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.QuestionListAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/4.
 */
@ContentView(R.layout.activity_questionlist)
public class QuestionListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    @ViewInject(R.id.questionlist)
    ListView lv;
    @ViewInject(R.id.toptitle)
    TextView toptitle;
    @ViewInject(R.id.rg)
    RadioGroup rg;
    @ViewInject(R.id.makequestion)
    FloatingActionButton  makequestion;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.nodata)
    TextView nodata;
    @ViewInject(R.id.recommend)
    RadioButton re;
    @ViewInject(R.id.time)
    RadioButton time;


    private String videoId;
    private String name;
    private String sid;
    private String pid;
    private SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        init();
        setListener();
        svProgressHUD = new SVProgressHUD(this);
        videoId = getIntent().getStringExtra("videoId");
        name = getIntent().getStringExtra("name");
        sid = getIntent().getStringExtra("sid");
        pid = getIntent().getStringExtra("pid");
        getData();
        toptitle.setText(name);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuestionListActivity.this, VideoQuestionAcitvity.class);
                intent.putExtra("name", name);
                intent.putExtra("qid", list.get(position).getId());
                startActivity(intent);
            }
        });
        makequestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionListActivity.this, SendQuestionActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.recommend:
                        re.setTextColor(Color.WHITE);
                        time.setTextColor(Color.BLACK);
                        type = "";
                        getData();
                        break;
                    case R.id.time:
                        re.setTextColor(Color.BLACK);
                        time.setTextColor(Color.WHITE);
                        type = "1";
                        getData();
                        break;
                }
            }
        });

    }

    int page = 1;
    List<QuestionListBean> list;
    QuestionListAdapter adapter;
    String type;

    void getData() {
        svProgressHUD.showWithStatus("加载中...");
        OkHttpDownloadJsonUtil.downloadJson(QuestionListActivity.this, new Path(QuestionListActivity.this).QUESTIONLIST(type, videoId, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
                }.getType());
                if (list.size()>0) {
                    nodata.setVisibility(View.GONE);
                    adapter = new QuestionListAdapter(QuestionListActivity.this, list, name);
                    lv.setAdapter(adapter);
                }else {
                    nodata.setVisibility(View.VISIBLE);
                }
                svProgressHUD.dismiss();
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
                OkHttpDownloadJsonUtil.downloadJson(QuestionListActivity.this, new Path(QuestionListActivity.this).QUESTIONLIST("1", videoId, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new Gson();
                        List<QuestionListBean> lists = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
                        }.getType());
                        for (QuestionListBean b :
                                lists) {
                            list.add(b);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
            OkHttpDownloadJsonUtil.downloadJson(QuestionListActivity.this, new Path(QuestionListActivity.this).SENDQUESTION("", sid, pid, videoId, data.getStringExtra("msg")), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                @Override
                public void onsendJson(String json) {
                    if (json.contains("1")) {
                        Toast.makeText(QuestionListActivity.this, "提问成功", Toast.LENGTH_SHORT).show();
                        onRefresh();
                    } else {
                        Toast.makeText(QuestionListActivity.this, "提问失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}