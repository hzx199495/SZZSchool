package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumCommentAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.video.PolyvTalkSendActivity;
import com.shizhanzhe.szzschool.widge.MyImageGetter;
import com.shizhanzhe.szzschool.widge.MyTagHandler;

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

import static com.shizhanzhe.szzschool.adapter.ForumCommentAdapter.getDateTimeFromMillisecond;

/**
 * Created by hasee on 2017/1/3.
 */
@ContentView(R.layout.activity_forumitem)
public class ForumItemActivity extends Activity {
    @ViewInject(R.id.top_title)
    TextView top;
    @ViewInject(R.id.text_author)
    TextView tvname;
    @ViewInject(R.id.avatar)
    ImageView avatar;
    @ViewInject(R.id.text_title)
    TextView itemtitle;
    @ViewInject(R.id.text_timeline)
    TextView edtime;
    @ViewInject(R.id.item_content)
    TextView content;
    @ViewInject(R.id.text_replies)
    TextView text_replies;
    @ViewInject(R.id.back)
    ImageView back;
    @ViewInject(R.id.scroll)
    ScrollView scroll;
    @ViewInject(R.id.ds)
    Button ds;
    private String fid;
    private String pid;
    // 讨论的listView
    private MyListView lv_talk;
    // 讨论的布局
    private RelativeLayout rl_bot;
    // 话题，发送的信息
    private String topic, sendMsg;
    // 加载中控件
    private ProgressBar pb_loading;
    // 空数据控件,重新加载控件
    private TextView tv_empty, tv_reload;
    String questionid;
    ForumCommentBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        findIdAndNew();

        Intent intent = getIntent();
        fid = intent.getStringExtra("fid");
        pid = intent.getStringExtra("pid");
        String title = intent.getStringExtra("title");
        String img = intent.getStringExtra("img");
        String replies = intent.getStringExtra("rep");
        String name = intent.getStringExtra("name");
        String time = intent.getStringExtra("time");
        final String authorid = intent.getStringExtra("authorid");
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ForumItemActivity.this, RewardActivity.class);
                intent1.putExtra("fid",fid);
                intent1.putExtra("authorid",authorid);
                startActivity(intent1);
            }
        });
        initView();
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
                MyImageGetter imageGetter = new MyImageGetter(getApplicationContext(), content);
                MyTagHandler tagHandler = new MyTagHandler(getApplicationContext());
                Spanned spanned = Html.fromHtml(json, imageGetter, tagHandler);
                content.setText(spanned);
                content.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scroll.smoothScrollTo(0,20);
        lv_talk.setFocusable(false);
    }

    private void findIdAndNew() {
        lv_talk = (MyListView) findViewById(R.id.lv_talk);
        rl_bot = (RelativeLayout) findViewById(R.id.rl_bot);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
    }

    private void initView() {
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCOMMENT(pid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {

                pb_loading.setVisibility(View.GONE);
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                }.getType());
                adapter = new ForumCommentAdapter(ForumItemActivity.this, list);
                lv_talk.setAdapter(adapter);
                lv_talk.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bean = list.get(position);
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
                tv_reload.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pb_loading.setVisibility(View.VISIBLE);
                        tv_reload.setVisibility(View.GONE);
                        initView();
                    }
                });
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
                .url(Path.FORUMCOMMENTREPLY(MyApplication.myid, pid, fid, MyApplication.token))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "发表讨论失败，请重试！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tv_empty.setVisibility(View.GONE);
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void addNewAnswer() {
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body = new FormBody.Builder()
                .add("con", sendMsg)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request = new Request.Builder()
                .url(Path.FORUMCOMMENTUSERREPLY(MyApplication.myid, questionid, pid, fid, MyApplication.token))
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "回复失败，请重试！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tv_empty.setVisibility(View.GONE);
                Looper.prepare();
                Toast.makeText(ForumItemActivity.this, "回复成功！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }
//    private void initViews() {
//
//        mListData = (MyGridView) findViewById(R.id.list_data);
//        mLytCommentVG = (TextView) findViewById(R.id.comment_vg_lyt);
//        mLytEdittextVG = (NoTouchLinearLayout) findViewById(R.id.edit_vg_lyt);
//        mCommentEdittext = (EditText) findViewById(R.id.edit_comment);
//        mSendBut = (Button) findViewById(R.id.but_comment_send);
//
//
//        ClickListener cl = new ClickListener();
//        mSendBut.setOnClickListener(cl);
//        mLytCommentVG.setOnClickListener(cl);
//
//    }
//
//    /**
//     * 获取回复列表数据
//     */
//    private List<ForumCommentBean.ManReplyBean> getReplyData() {
//        List<ForumCommentBean.ManReplyBean> replyList = new ArrayList<>();
//        return replyList;
//    }
//    /**
//     * 发表评论
//     */
//    private void publishComment() {
//        ForumCommentBean commentBean = new ForumCommentBean();
//        commentBean.setAuthor(myname);
//        commentBean.setComment(comment);
//        commentBean.setMan_reply(getReplyData());
//        commentBean.setLogo(MyApplication.img);
//        Calendar calendar = Calendar.getInstance();
//        Long currentMillisecond = calendar.getTimeInMillis();
//        commentBean.setDateline(currentMillisecond/1000);
//        adapter.addData(list);
//        OkHttpClient client = new OkHttpClient();
//        okhttp3.RequestBody body=new FormBody.Builder()
//                .add("con", comment)
//                .build();
//        //在构建Request对象时，调用post方法，传入RequestBody对象
//        Request request=new Request.Builder()
//                .url("http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+MyApplication.myid+"&rpid=0&pid="+pid+"&fid="+fid+"&token="+MyApplication.token)
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("fail",e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
//
//    }
//    /**
//     * 回复评论
//     */
//    private void replyComment() {
//        ForumCommentBean.ManReplyBean replyBean = new ForumCommentBean.ManReplyBean();
//        replyBean.setAuthor(myname);
//        replyBean.setComment(comment);
//        adapter.getReplyComment(replyBean, position);
//        adapter.notifyDataSetChanged();
//        OkHttpClient client = new OkHttpClient();
//        okhttp3.RequestBody body=new FormBody.Builder()
//                .add("con", comment)
//                .build();
//        //在构建Request对象时，调用post方法，传入RequestBody对象
//        Request request=new Request.Builder()
//                .url("http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+MyApplication.myid+"&rpid="+list.get(position).getRpid()+"&pid="+pid+"&fid="+fid+"&token="+MyApplication.token)
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("fail",e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
//    }
//
//    private Handler handlercom = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what) {
//                case 10:
//                    isReply = true;
//                    position = (Integer) msg.obj;
//                    mLytCommentVG.setVisibility(View.GONE);
//                    mLytEdittextVG.setVisibility(View.VISIBLE);
//                    onFocusChange(true);
//                    break;
//                case 11:
//                    isReply = false;
//                    position = (Integer)msg.obj;
//                    break;
//
//            }
//
//        }
//    };
//    /**
//     * 显示或隐藏输入法
//     */
//    private void onFocusChange(boolean hasFocus) {
//        final boolean isFocus = hasFocus;
//        (new Handler()).postDelayed(new Runnable() {
//            public void run() {
//                InputMethodManager imm = (InputMethodManager)
//                        mCommentEdittext.getContext().getSystemService(INPUT_METHOD_SERVICE);
//                if (isFocus) {
//                    //显示输入法
//                    mCommentEdittext.requestFocus();//获取焦点
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                } else {
//                    //隐藏输入法
//                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
//                    mLytCommentVG.setVisibility(View.VISIBLE);
//                    mLytEdittextVG.setVisibility(View.GONE);
//                }
//            }
//        }, 100);
//    }
//
//    /**
//     * 点击屏幕其他地方收起输入法
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//                onFocusChange(false);
//
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        // 必不可少，否则所有的组件都不会有TouchEvent了
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            return true;
//        }
//        return onTouchEvent(ev);
//    }
//
//    /**
//     * 隐藏或者显示输入框
//     */
//    public boolean isShouldHideInput(View v, MotionEvent event) {
//        if (v != null && (v instanceof EditText)) {
//            int[] leftTop = {0, 0};
//            //获取输入框当前的location位置
//            /**
//             *这堆数值是算我的下边输入区域的布局的，
//             * 规避点击输入区域也会隐藏输入区域
//             */
//
//            v.getLocationInWindow(leftTop);
//            int left = leftTop[0] - 50;
//            int top = leftTop[1] - 50;
//            int bottom = top + v.getHeight() + 300;
//            int right = left + v.getWidth() + 120;
//            if (event.getX() > left && event.getX() < right
//                    && event.getY() > top && event.getY() < bottom) {
//                // 点击的是输入框区域，保留点击EditText的事件
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断对话框中是否输入内容
//     */
//    private boolean isEditEmply() {
//        comment = mCommentEdittext.getText().toString().trim();
//        if (comment.equals("")) {
//            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        mCommentEdittext.setText("");
//        return true;
//    }
//    /**
//     * 事件点击监听器
//     */
//    private final class ClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.but_comment_send:        //发表评论按钮
//                    if (isEditEmply()) {        //判断用户是否输入内容
//                        if (isReply) {
//                            replyComment();
//                        } else {
//                            publishComment();
//                        }
//                        mLytCommentVG.setVisibility(View.VISIBLE);
//                        mLytEdittextVG.setVisibility(View.GONE);
//                        onFocusChange(false);
//                    }
//                    break;
//                case R.id.comment_vg_lyt:        //底部评论按钮
//                    isReply = false;
//                    mLytEdittextVG.setVisibility(View.VISIBLE);
//                    mLytCommentVG.setVisibility(View.GONE);
//                    onFocusChange(true);
//                    break;
//            }
//        }
//    }
//    /**
//     * 将毫秒转化成固定格式的时间
//     * 时间格式: yyyy-MM-dd HH:mm:ss
//     *
//     * @param millisecond
//     * @return
//     */
//    public static String getDateTimeFromMillisecond(long millisecond){
//        System.setProperty("user.timezone", "Asia/Shanghai");
//        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
//        TimeZone.setDefault(tz);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date(millisecond*1000);
//        String dateStr = simpleDateFormat.format(date);
//        return dateStr;
//    }
}
