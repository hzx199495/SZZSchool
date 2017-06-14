package com.shizhanzhe.szzschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.ForumCommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.adapter.ForumCommentAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.NoTouchLinearLayout;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.widge.MyImageGetter;
import com.shizhanzhe.szzschool.widge.MyTagHandler;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2017/1/3.
 */
@ContentView(R.layout.activity_forumitem)
public class ForumItemActivity extends Activity {
    @ViewInject(R.id.top_title)
    TextView top;
    @ViewInject(R.id.item_title)
    TextView itemtitle;
    @ViewInject(R.id.item_editortime)
    TextView edtime;
    @ViewInject(R.id.item_content)
    TextView content;
    private String fid;
    private String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        initViews();
        Intent intent = getIntent();
        fid = intent.getStringExtra("fid");
        pid = intent.getStringExtra("pid");
        String title = intent.getStringExtra("title");
        String name = intent.getStringExtra("name");
        long time = intent.getLongExtra("time",0);
        top.setText(title);
        itemtitle.setText(title);
        edtime.setText("发表人："+name+" 时间："+getDateTimeFromMillisecond(time));
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCONTENT(pid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Log.i("===",json);
                MyImageGetter imageGetter = new MyImageGetter(getApplicationContext(), content);
                MyTagHandler tagHandler = new MyTagHandler(getApplicationContext());
                Spanned spanned = Html.fromHtml(json, imageGetter, tagHandler);
                content.setText(spanned);
                content.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
        OkHttpDownloadJsonUtil.downloadJson(this, Path.FORUMCOMMENT(pid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {


            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<ForumCommentBean>>() {
                }.getType());
                adapter = new ForumCommentAdapter(getApplicationContext(), list, R.layout.forumcomment_item, handlercom);
                mListData.setAdapter(adapter);
            }
        });
    }

    private MyGridView mListData;
    private TextView mLytCommentVG;
    private NoTouchLinearLayout mLytEdittextVG;
    private EditText mCommentEdittext;
    private Button mSendBut;
    private List<ForumCommentBean> list;
    private List<ForumCommentBean.ManReplyBean> replyList;
    private ForumCommentAdapter adapter;
    String myname = MyApplication.username;
    String myid=MyApplication.myid;
    private int count;                    //记录评论ID
    private String comment = "";        //记录对话框中的内容
    private int position;                //记录回复评论的索引
    private boolean isReply;            //是否是回复，true代表回复
    private void initViews() {

        mListData = (MyGridView) findViewById(R.id.list_data);
        mLytCommentVG = (TextView) findViewById(R.id.comment_vg_lyt);
        mLytEdittextVG = (NoTouchLinearLayout) findViewById(R.id.edit_vg_lyt);
        mCommentEdittext = (EditText) findViewById(R.id.edit_comment);
        mSendBut = (Button) findViewById(R.id.but_comment_send);

        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);
        mLytCommentVG.setOnClickListener(cl);

    }

    /**
     * 获取回复列表数据
     */
    private List<ForumCommentBean.ManReplyBean> getReplyData() {
        List<ForumCommentBean.ManReplyBean> replyList = new ArrayList<>();
        return replyList;
    }
    /**
     * 发表评论
     */
    private void publishComment() {
        ForumCommentBean commentBean = new ForumCommentBean();
        commentBean.setAuthor(myname);
        commentBean.setComment(comment);
        commentBean.setMan_reply(getReplyData());
        commentBean.setLogo(MyApplication.img);
        Calendar calendar = Calendar.getInstance();
        Long currentMillisecond = calendar.getTimeInMillis();
        commentBean.setDateline(currentMillisecond/1000);
        adapter.addData(list);
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body=new FormBody.Builder()
                .add("con", comment)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request=new Request.Builder()
                .url("http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+MyApplication.myid+"&rpid=0&pid="+pid+"&fid="+fid+"&token="+MyApplication.token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fail",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
    /**
     * 回复评论
     */
    private void replyComment() {
        ForumCommentBean.ManReplyBean replyBean = new ForumCommentBean.ManReplyBean();
        replyBean.setAuthor(myname);
        replyBean.setComment(comment);
        adapter.getReplyComment(replyBean, position);
        adapter.notifyDataSetChanged();
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody body=new FormBody.Builder()
                .add("con", comment)
                .build();
        //在构建Request对象时，调用post方法，传入RequestBody对象
        Request request=new Request.Builder()
                .url("http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+MyApplication.myid+"&rpid="+list.get(position).getRpid()+"&pid="+pid+"&fid="+fid+"&token="+MyApplication.token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fail",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private Handler handlercom = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 10:
                    isReply = true;
                    position = (Integer) msg.obj;
                    mLytCommentVG.setVisibility(View.GONE);
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    break;
                case 11:
                    isReply = false;
                    position = (Integer)msg.obj;
                    break;

            }

        }
    };
    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        mCommentEdittext.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    mCommentEdittext.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
                    mLytCommentVG.setVisibility(View.VISIBLE);
                    mLytEdittextVG.setVisibility(View.GONE);
                }
            }
        }, 100);
    }

    /**
     * 点击屏幕其他地方收起输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 50;
            int top = leftTop[1] - 50;
            int bottom = top + v.getHeight() + 300;
            int right = left + v.getWidth() + 120;
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = mCommentEdittext.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        mCommentEdittext.setText("");
        return true;
    }
    /**
     * 事件点击监听器
     */
    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_send:        //发表评论按钮
                    if (isEditEmply()) {        //判断用户是否输入内容
                        if (isReply) {
                            replyComment();
                        } else {
                            publishComment();
                        }
                        mLytCommentVG.setVisibility(View.VISIBLE);
                        mLytEdittextVG.setVisibility(View.GONE);
                        onFocusChange(false);
                    }
                    break;
                case R.id.comment_vg_lyt:        //底部评论按钮
                    isReply = false;
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    mLytCommentVG.setVisibility(View.GONE);
                    onFocusChange(true);
                    break;
            }
        }
    }
    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(long millisecond){
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond*1000);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
