package com.shizhanzhe.szzschool.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.sub.vlms.entity.PolyvCoursesInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.CommentBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import java.util.List;

public class PolyvTalkFragment extends Fragment {
    // fragmentView
    private View view;
    // 讨论的listView
    private ListView lv_talk;
    // 讨论的布局
    private RelativeLayout rl_bot;
    // 点击的父索引
    private int position;
    // 话题，发送的信息
    private String topic, sendMsg;
    // 加载中控件
    private ProgressBar pb_loading;
    // 空数据控件,重新加载控件
    private TextView tv_empty, tv_reload;

    CommentBean bean;
    String questionunameid;
    String questionid;
    private void addNewQuestion() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.SENDQUESTION(list.get(0).getClassid(), list.get(0).getSid(), list.get(0).getPid(), MyApplication.videoitemid, sendMsg, MyApplication.zh, MyApplication.username, MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("1")){
                    tv_empty.setVisibility(View.GONE);
                    initView();
                    Toast.makeText(getContext(), "发送成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "发表讨论失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addNewAnswer() {
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.ANSWERQUESTION(MyApplication.videoitemid, questionunameid,sendMsg,questionid,MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                if (json.contains("1")){
                    tv_empty.setVisibility(View.GONE);
                    initView();
                    Toast.makeText(getContext(), "回复成功！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "回复失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findIdAndNew() {
        lv_talk = (ListView) view.findViewById(R.id.lv_talk);
        rl_bot = (RelativeLayout) view.findViewById(R.id.rl_bot);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
    }
    List<CommentBean> list;
    PolyvTalkListViewAdapter adapter;
    private void initView() {
        // fragment在onCreate之后才可以获取
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.COMMENT(MyApplication.videoitemid, MyApplication.myid, MyApplication.token), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                pb_loading.setVisibility(View.GONE);
                Gson gson = new Gson();
                 list = gson.fromJson(json, new TypeToken<List<CommentBean>>() {
                }.getType());
                adapter = new PolyvTalkListViewAdapter(getActivity(), list);
                lv_talk.setAdapter(adapter);
            }
        });
        lv_talk.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bean = list.get(position);
                Intent intent = new Intent(getActivity(), PolyvTalkEdittextActivity.class);
                intent.putExtra("questionid", bean.getId());
                intent.putExtra("questionunameid", bean.getUid());
                intent.putExtra("nickname", bean.getUsername());
                getActivity().startActivityForResult(intent, 13);
                getActivity().overridePendingTransition(R.anim.polyv_activity_alpha_in, 0);

            }
        });

        rl_bot.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PolyvTalkSendActivity.class);
                startActivityForResult(intent, 13);
            }
        });
        tv_reload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pb_loading.setVisibility(View.VISIBLE);
                tv_reload.setVisibility(View.GONE);
                initView();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(R.layout.polyv_fragment_tab_talk, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIdAndNew();
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (bean == null) {
            findIdAndNew();
            initView();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 19:
                // 回答
                sendMsg = data.getStringExtra("sendMsg");
                questionunameid= data.getStringExtra("questionunameid");
                questionid=data.getStringExtra("questionid");
                addNewAnswer();
                break;

            case 12:
                // 问题
                sendMsg = data.getStringExtra("sendMsg");
                addNewQuestion();
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null)
            ((ViewGroup) view.getParent()).removeView(view);
    }
}
