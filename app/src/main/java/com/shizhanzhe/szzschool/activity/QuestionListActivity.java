package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.QuestionListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.QuestionListAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/4.
 * 问答列表
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
    FloatingActionButton makequestion;
    @ViewInject(R.id.back)
    ImageView back;

    @ViewInject(R.id.recommend)
    RadioButton re;
    @ViewInject(R.id.time)
    RadioButton time;
    //    @ViewInject(R.id.state_layout)
//    StateLayout state_layout;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.iv)
    ImageView iv;
    private QMUITipDialog dialog;
    private String videoId;
    private String name;
    private String sid;
    private String pid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        init();
        setListener();
        lv.setEmptyView(iv);
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
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
                intent.putExtra("type", "2");
                intent.putExtra("txId", sid);
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
                        re.setTextColor(Color.BLUE);
                        time.setTextColor(Color.BLACK);
                        type = "";
                        getData();
                        break;
                    case R.id.time:
                        re.setTextColor(Color.BLACK);
                        time.setTextColor(Color.BLUE);
                        type = "1";
                        getData();
                        break;
                }
            }
        });
//        state_layout.setTipText(StateLayout.EMPTY,"");
//        state_layout.showLoadingView();
//        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
//            @Override
//            public void refreshClick() {
//                state_layout.showLoadingView();
//                getData();
//            }
//
//            @Override
//            public void loginClick() {
//
//            }
//        });
    }

    private int page = 1;
    private List<QuestionListBean> list;
    private QuestionListAdapter adapter;
    private String type;

    void getData() {
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(QuestionListActivity.this, new Path(QuestionListActivity.this).QUESTIONLIST(type, videoId, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


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
                    list = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
                    }.getType());
                    if (list.size() > 0) {
                        adapter = new QuestionListAdapter(QuestionListActivity.this, list, name);
                        lv.setAdapter(adapter);
//                            state_layout.showContentView();

                    } else {
                        empty.show("", "暂无提问");
                    }
                    dialog.dismiss();
                    swipeLayout.setVisibility(View.VISIBLE);
                } catch (Exception e) {
//                    state_layout.showErrorView();
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

    private RefreshLayout swipeLayout;

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
                        try {


                            Gson gson = new Gson();
                            List<QuestionListBean> lists = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
                            }.getType());
                            if (lists.size() == 0) {
                                Toast.makeText(getApplicationContext(), "已经到底了", Toast.LENGTH_SHORT).show();
                            } else {
                                for (QuestionListBean b :
                                        lists) {
                                    list.add(b);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            Toast.makeText(QuestionListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
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
                    try {


                        if (json.contains("1")) {
                            Toast.makeText(QuestionListActivity.this, "提问成功", Toast.LENGTH_SHORT).show();
                            onRefresh();
                        } else {
                            Toast.makeText(QuestionListActivity.this, "提问失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(QuestionListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
