package com.shizhanzhe.szzschool.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.easefun.polyvsdk.sub.vlms.entity.PolyvCoursesInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.BKBean;
import com.shizhanzhe.szzschool.Bean.CommentBean;
import com.shizhanzhe.szzschool.Bean.NoteBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.ForumItemActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.VideoNoteActivity;
import com.shizhanzhe.szzschool.adapter.NoteAdapter;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import java.util.List;

public class PolyvTalkFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    // fragmentView
    private View view;
    //    // 讨论的listView
//    private ListView lv_talk;
//    // 讨论的布局
//    private RelativeLayout rl_bot;
//    // 点击的父索引
//    private int position;
//    // 话题，发送的信息
//    private String topic, sendMsg;
    // 加载中控件
    private ProgressBar pb_loading;
//    // 空数据控件,重新加载控件
//    private TextView tv_empty, tv_reload, nodata;
//
//    CommentBean bean;
//    String questionunameid;
//    String questionid;
//    private String teacher;
//    private String uid;
//
//    private void addNewQuestion() {
//        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).SENDQUESTION(MyApplication.videoclassid, MyApplication.txId, MyApplication.videotypeid, MyApplication.videoitemid, sendMsg), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//            @Override
//            public void onsendJson(String json) {
//                if (json.contains("1")) {
//                    tv_empty.setVisibility(View.GONE);
//                    initView();
//                    new SVProgressHUD(getActivity()).showSuccessWithStatus("发表成功！");
//                } else {
//                    new SVProgressHUD(getActivity()).showErrorWithStatus("发表评论失败，请重试！");
//                }
//            }
//        });
//    }
//
//    private void addNewAnswer() {
//        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).ANSWERQUESTION(MyApplication.videoitemid, questionunameid, sendMsg, questionid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//            @Override
//            public void onsendJson(String json) {
//
//                if (json.contains("1")) {
//                    tv_empty.setVisibility(View.GONE);
//                    initView();
//                    new SVProgressHUD(getActivity()).showSuccessWithStatus("回复成功！");
//                } else {
//                    new SVProgressHUD(getActivity()).showErrorWithStatus("回复失败，请重试！");
//                }
//            }
//        });
//    }
//
//    private void findIdAndNew() {
//        lv_talk = (ListView) view.findViewById(R.id.lv_talk);
//        rl_bot = (RelativeLayout) view.findViewById(R.id.rl_bot);
//        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
//        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
//        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
//        nodata = (TextView) view.findViewById(R.id.nodata);
//    }
//
//    List<CommentBean> list;
//    PolyvTalkListViewAdapter adapter;
//
//    private void initView() {
//        // fragment在onCreate之后才可以获取
//        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).COMMENT(MyApplication.videoitemid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//
//            @Override
//            public void onsendJson(String json) {
//                pb_loading.setVisibility(View.GONE);
//                Gson gson = new Gson();
//                list = gson.fromJson(json, new TypeToken<List<CommentBean>>() {
//                }.getType());
//                if (list.size() > 0) {
//                    adapter = new PolyvTalkListViewAdapter(getActivity(), list);
//                    lv_talk.setAdapter(adapter);
//                    nodata.setVisibility(View.GONE);
//                } else {
//                    nodata.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//        lv_talk.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                bean = list.get(position);
//                if (uid.contains(bean.getUid()) || teacher.contains("1")) {
//                    Intent intent = new Intent(getActivity(), PolyvTalkEdittextActivity.class);
//                    intent.putExtra("questionid", bean.getId());
//                    intent.putExtra("questionunameid", bean.getUid());
//                    intent.putExtra("nickname", bean.getUsername());
//                    getActivity().startActivityForResult(intent, 13);
//                    getActivity().overridePendingTransition(R.anim.polyv_activity_alpha_in, 0);
//                } else {
//                    new SVProgressHUD(getActivity()).showInfoWithStatus("无权限回复!");
//                }
//
//
//            }
//        });
//

    //    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        if (view == null)
//            view = inflater.inflate(R.layout.polyv_fragment_tab_talk, container, false);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
//        teacher = preferences.getString("teacher", "");
//        uid = preferences.getString("uid", "");
//        findIdAndNew();
//        initView();
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (bean == null) {
//            findIdAndNew();
//            initView();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode) {
//            case 19:
//                // 回答
//                sendMsg = data.getStringExtra("sendMsg");
//                questionunameid = data.getStringExtra("questionunameid");
//                questionid = data.getStringExtra("questionid");
//                addNewAnswer();
//                break;
//
//            case 12:
//                // 问题
//                sendMsg = data.getStringExtra("sendMsg");
//                addNewQuestion();
//                break;
//        }
//
//    }
    private ListView lv_bj;
    // 讨论的布局
    private FloatingActionButton rl_bj;
    // 空数据控件,重新加载控件
    private TextView tv_empty, tv_reload, nodata;
    List<NoteBean> list;
    NoteAdapter adapter;
    RefreshLayout swipeLayout;
    int page = 1;

    public static PolyvTalkFragment newInstance(String txId) {

        Bundle args = new Bundle();
        args.putString("txId",txId);
        PolyvTalkFragment fragment = new PolyvTalkFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.polyv_fragment_note, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListener();
        findIdAndNew();
        initView();
        rl_bj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoNoteActivity.class);
                intent.putExtra("txId",getArguments().getString("txId"));
                startActivity(intent);
            }
        });
    }

    private void findIdAndNew() {
        lv_bj = (ListView) view.findViewById(R.id.lv_bj);
        rl_bj = (FloatingActionButton) view.findViewById(R.id.rl_bj);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
        nodata = (TextView) view.findViewById(R.id.nodata);
    }

    private void initView() {
        // fragment在onCreate之后才可以获取
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).NOTELIST(MyApplication.videoitemid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

            @Override
            public void onsendJson(String json) {
                try {


                pb_loading.setVisibility(View.GONE);
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<NoteBean>>() {
                }.getType());
                if (list.size()>0){
                    nodata.setVisibility(View.GONE);
                    adapter = new NoteAdapter(getContext(), list,0);
                    lv_bj.setAdapter(adapter);
                }else {
                    nodata.setVisibility(View.VISIBLE);
                }
                }catch (Exception e){}
            }
        });
    }

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void init() {
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setOnLoadListener(this);
    }



//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (view != null)
//            ((ViewGroup) view.getParent()).removeView(view);
//    }

    @Override
    public void onRefresh() {
        swipeLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                page = 1;
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
                swipeLayout.setLoading(false);
                page++;
                OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getContext()).NOTELIST(MyApplication.videoitemid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {

                    @Override
                    public void onsendJson(String json) {
                        Gson gson = new Gson();
                        List<NoteBean> lists = gson.fromJson(json, new TypeToken<List<NoteBean>>() {
                        }.getType());
                        for (NoteBean b :
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
    public void onResume() {
        super.onResume();
        initView();
    }

}
