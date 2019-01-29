package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumCommentAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;
import com.shizhanzhe.szzschool.utils.StatusBarUtil;
import com.shizhanzhe.szzschool.video.PolyvTalkSendActivity;
import com.shizhanzhe.szzschool.widge.HtmlTextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2017/1/3.
 * 帖子详情
 */
@ContentView(R.layout.activity_forumitem)
public class ForumItemActivity extends Activity implements RefreshLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.top_title)
    TextView top;
    @ViewInject(R.id.back)
    ImageView back;
    //    @ViewInject(R.id.state_layout)
//    StateLayout state_layout;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.ll)
    LinearLayout ll;
    private QMUITipDialog dialog;
    private TextView tvname;
    private ImageView avatar;
    private TextView itemtitle;
    private TextView edtime;
    private HtmlTextView content;
    private TextView text_replies;
    private ImageView ds;

    private RefreshLayout swipeLayout;
    private String fid;
    private String pid;
    // 讨论的listView
    private ListView lv_talk;
    // 讨论的布局
    private RelativeLayout rl_bot;
    // 话题，发送的信息
    private String topic, sendMsg;
    // 加载中控件
    private String questionid;
    private ForumCommentBean bean;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(this,R.color.white); }
        findIdAndNew();
        init();
        setListener();
        View head = getLayoutInflater().inflate(R.layout.header_forum, null);
        Intent intent = getIntent();
        fid = intent.getStringExtra("fid");
        pid = intent.getStringExtra("pid");
        String title = intent.getStringExtra("title");
        String img = intent.getStringExtra("img");
        String replies = intent.getStringExtra("rep");
        String name = intent.getStringExtra("name");
        String time = intent.getStringExtra("time");
        final String authorid = intent.getStringExtra("authorid");
        lv_talk.addHeaderView(head);
        initView();
        setHeader();
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ForumItemActivity.this, RewardActivity.class);
                intent1.putExtra("pid", pid);
                startActivity(intent1);
            }
        });


        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_load) // resource
                // or
                // drawable
                .showImageForEmptyUri(R.drawable.img_load) // resource or
                // drawable
                .showImageOnFail(R.drawable.img_load) // resource or drawable
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisk(true)
                .build();
        top.setText(title);
        itemtitle.setText(title);
        tvname.setText(name);
        text_replies.setText(replies + "个回复");
        try {
            if (!"".equals(img) && img.contains("http")) {
                ImageLoader.getInstance().displayImage(img, avatar, options);
            } else {
                ImageLoader.getInstance().displayImage(Path.IMG(img), avatar, options);
            }
        } catch (Exception e) {
            SharedPreferences preferences = getSharedPreferences("userjson", Context.MODE_PRIVATE);
            String myimg = preferences.getString("img", "");
            if (myimg.contains("http")) {
                ImageLoader.getInstance().displayImage(myimg, avatar, options);
            } else {
                ImageLoader.getInstance().displayImage(Path.IMG(myimg), avatar, options);
            }
        }
        edtime.setText(time);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_talk.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bean = list.get(position - 1);
                Intent intent = new Intent(ForumItemActivity.this, ForumTalkEdittextActivity.class);
                intent.putExtra("questionid", bean.getId());
                intent.putExtra("nickname", bean.getAuthor());
                ForumItemActivity.this.startActivityForResult(intent, 13);
                ForumItemActivity.this.overridePendingTransition(R.anim.polyv_activity_alpha_in, 0);

            }
        });

        rl_bot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumItemActivity.this, PolyvTalkSendActivity.class);
                startActivityForResult(intent, 13);
            }
        });

    }

    private void setHeader() {
        tvname = (TextView) findViewById(R.id.text_author);
        avatar = (ImageView) findViewById(R.id.avatar);
        itemtitle = (TextView) findViewById(R.id.text_title);
        edtime = (TextView) findViewById(R.id.text_timeline);
        content = (HtmlTextView) findViewById(R.id.item_content);
        text_replies = (TextView) findViewById(R.id.text_replies);
        ds = (ImageView) findViewById(R.id.ds);
    }

    private void findIdAndNew() {
        lv_talk = (ListView) findViewById(R.id.lv_talk);
        rl_bot = (RelativeLayout) findViewById(R.id.rl_bot);

    }

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void init() {
        dialog = new QMUITipDialog.Builder(this).setIconType(1).setTipWord("正在加载").create();
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

    private void initView() {
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCOMMENT(pid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initView();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initView();
                            }
                        });
                        return;
                    }
                    Gson gson = new Gson();
                    list = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                    }.getType());
                    adapter = new ForumCommentAdapter(ForumItemActivity.this, list);
                    lv_talk.setAdapter(adapter);
                    getData();
                } catch (Exception e) {
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initView();
                        }
                    });
                }

            }
        });
    }

    private List<ForumCommentBean> list;
    private ForumCommentAdapter adapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 19:
                // 回答
                sendMsg = data.getStringExtra("sendMsg");
                questionid = data.getStringExtra("questionid");
                addNewAnswer();
                break;

            case 12:
                // 问题
                sendMsg = data.getStringExtra("sendMsg");
                addNewQuestion();
                break;
        }

    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 99) {
                page = 1;
                initView();
            }
            super.handleMessage(msg);
        }
    };

    private void addNewQuestion() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("con", sendMsg)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request = new Request.Builder()
                .url(new Path(this).FORUMCOMMENTREPLY(pid, fid))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "发表评论失败！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myHandler.sendEmptyMessage(99);
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "发表成功！", Toast.LENGTH_LONG).show();
                Looper.loop();

            }
        });
    }

    private void addNewAnswer() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("con", sendMsg)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request = new Request.Builder()
                .url(new Path(this).FORUMCOMMENTUSERREPLY(questionid, pid, fid))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "回复失败！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myHandler.sendEmptyMessage(99);
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "回复成功！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新
                page = 1;
                initView();
                swipeLayout.setRefreshing(false);
            }
        }, 2000);

    }

    void getData() {
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCONTENT(pid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                try {
                    if (json.equals("0")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initView();
                            }
                        });
                        return;
                    } else if (json.equals("1")) {
                        dialog.dismiss();
                        empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initView();
                            }
                        });
                        return;
                    }
                    if (!json.contains(".pdf")) {
                        content.setHtmlFromString(json, false);
                    } else {
                        String a = json.replace("href=\"", "href=\"https://www.shizhanzhe.com");
                        String s = a.replace("src=\"", "src=\"https://www.shizhanzhe.com");
                        Document doc = Jsoup.parse(s);
                        String html = doc.html();
                        content.setHtmlFromString(html, false);
                    }
                    dialog.dismiss();
                    ll.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    dialog.dismiss();
                    empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initView();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新

                swipeLayout.setLoading(false);
                page++;
                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.FORUMCOMMENT(pid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {

                        Gson gson = new Gson();
                        List<ForumCommentBean> lists = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                        }.getType());
                        if (lists.size() == 0) {
                            Toast.makeText(ForumItemActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
                        } else {
                            for (ForumCommentBean b :
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

}
