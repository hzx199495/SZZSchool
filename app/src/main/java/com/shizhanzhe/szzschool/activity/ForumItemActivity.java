package com.shizhanzhe.szzschool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumCommentAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;
import com.shizhanzhe.szzschool.video.PolyvTalkSendActivity;
import com.shizhanzhe.szzschool.widge.MyImageGetter;
import com.shizhanzhe.szzschool.widge.MyTagHandler;

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

    TextView tvname;
    ImageView avatar;
    TextView itemtitle;
    TextView edtime;
    TextView content;
    TextView text_replies;
    Button ds;

    RefreshLayout swipeLayout;
    private String fid;
    private String pid;
    // 讨论的listView
    private ListView lv_talk;
    // 讨论的布局
    private RelativeLayout rl_bot;
    // 话题，发送的信息
    private String topic, sendMsg;
    // 加载中控件
    String questionid;
    ForumCommentBean bean;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        findIdAndNew();
        init();
        setListener();
        View head=  getLayoutInflater().inflate(R.layout.header_forum, null);
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
                intent1.putExtra("fid",fid);
                intent1.putExtra("authorid",authorid);
                startActivity(intent1);
            }
        });


        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.polyv_avatar_def) // resource
                // or
                // drawable
                .showImageForEmptyUri(R.drawable.polyv_avatar_def) // resource or
                // drawable
                .showImageOnFail(R.drawable.polyv_avatar_def) // resource or drawable
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisk(true)
                .build();
        top.setText(title);
        itemtitle.setText(title);
        tvname.setText(name);
        text_replies.setText(replies + "个回复");
        if (!"".equals(img)&&img.contains("http")) {
            ImageLoader.getInstance().displayImage(img, avatar, options);
        } else {
            ImageLoader.getInstance().displayImage(Path.IMG(img), avatar, options);
        }
        edtime.setText(time);
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCONTENT(pid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {

                if (!json.contains(".pdf")) {
                    MyImageGetter imageGetter = new MyImageGetter(getApplicationContext(), content);
                    MyTagHandler tagHandler = new MyTagHandler(getApplicationContext());
                    Spanned spanned = Html.fromHtml(json, imageGetter, tagHandler);
                    content.setText(spanned);
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                }else {
                    String s = json.replace("href=\"", "href=\"https://www.shizhanzhe.com");
                    Document doc = Jsoup.parse(s);
                    String html = doc.html();
                    content.setText(Html.fromHtml(html));
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_talk.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bean = list.get(position-1);
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
    private void setHeader(){
        tvname= (TextView) findViewById(R.id.text_author);
        avatar= (ImageView) findViewById(R.id.avatar);
        itemtitle= (TextView) findViewById(R.id.text_title);
        edtime= (TextView) findViewById(R.id.text_timeline);
        content= (TextView) findViewById(R.id.item_content);
        text_replies= (TextView) findViewById(R.id.text_replies);
        ds= (Button) findViewById(R.id.ds);
    }
    private void findIdAndNew() {
        lv_talk = (ListView) findViewById(R.id.lv_talk);
        rl_bot = (RelativeLayout) findViewById(R.id.rl_bot);

    }
    /**
     * 初始化布局
     */
    @SuppressLint({ "InlinedApi", "InflateParams" })
    private void init() {
        swipeLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray,R.color.blue2,R.color.red,R.color.green);
    }
    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }

    private void initView() {
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCOMMENT(pid,page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                }.getType());
                adapter = new ForumCommentAdapter(ForumItemActivity.this, list);
                lv_talk.setAdapter(adapter);

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
                Toast.makeText(ForumItemActivity.this,"发表评论失败！",Toast.LENGTH_LONG);
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this,"发表成功！",Toast.LENGTH_LONG);
                Looper.loop();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        initView();
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
                Toast.makeText(ForumItemActivity.this,"回复失败！",Toast.LENGTH_LONG);
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this,"回复成功！",Toast.LENGTH_LONG);
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
                page=1;
                initView();
                swipeLayout.setRefreshing(false);
            }
        }, 2000);

    }

    @Override
    public void onLoad() {
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新数据  更新完后调用该方法结束刷新

                swipeLayout.setLoading(false);
                page++;
                OkHttpDownloadJsonUtil.downloadJson(getApplicationContext(), Path.FORUMCOMMENT(pid,page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {

                        Gson gson = new Gson();
                        List<ForumCommentBean> lists = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                        }.getType());
                        for (ForumCommentBean b :
                                lists) {
                            list.add(b);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }, 2000);
    }

}
