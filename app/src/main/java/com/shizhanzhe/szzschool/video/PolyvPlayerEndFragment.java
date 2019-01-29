package com.shizhanzhe.szzschool.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shizhanzhe.szzschool.Bean.ProDeatailBean;
import com.shizhanzhe.szzschool.Bean.QuestionBean;
import com.shizhanzhe.szzschool.Bean.QuestionListBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.QuestionListActivity;
import com.shizhanzhe.szzschool.activity.SendQuestionActivity;
import com.shizhanzhe.szzschool.adapter.QuestionListAdapter;
import com.shizhanzhe.szzschool.adapter.QuestionReplyAdapter;
import com.shizhanzhe.szzschool.utils.Data;
import com.shizhanzhe.szzschool.utils.MyListView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;
import com.shizhanzhe.szzschool.utils.RefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by zz9527 on 2017/9/1.
 */
@ContentView(R.layout.polyvplayerendfragment)
public class PolyvPlayerEndFragment extends Fragment {
    @ViewInject(R.id.video_total)
    TextView video_total;
    @ViewInject(R.id.video_title)
    TextView video_title;
    @ViewInject(R.id.video_intro)
    TextView video_intro;
    @ViewInject(R.id.frame_end)
    LinearLayout frame_end;
    @ViewInject(R.id.frame_top)
    RelativeLayout frame_top;
    @ViewInject(R.id.makequestion)
    ImageView makequestion;
    @ViewInject(R.id.ll_question)
    LinearLayout ll_question;

    PolyvPlayerTabFragment tabFragment;
    PolyvPlayerViewPagerFragment viewPagerFragment;
    private String stitle;

    public static PolyvPlayerEndFragment newInstance(String json) {

        Bundle args = new Bundle();
        args.putString("json", json);
        PolyvPlayerEndFragment fragment = new PolyvPlayerEndFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, null);

    }

    String teacher;
    String uid;
    String json;
    String sid;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
        teacher = preferences.getString("teacher", "");
        uid = preferences.getString("uid", "");
        Gson gson = new Gson();
        json = Data.getData();
        ProDeatailBean.TxBean tx = gson.fromJson(json, ProDeatailBean.class).getTx();
        stitle = tx.getStitle();
        sid=tx.getId();
        final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        tabFragment = PolyvPlayerTabFragment.newInstance(stitle);
        viewPagerFragment = PolyvPlayerViewPagerFragment.newInstance(tx.getId());
        ft.add(R.id.fl_tab, tabFragment, "tabFragment");
        ft.add(R.id.fl_viewpager, viewPagerFragment, "viewPagerFragment");
        ft.commit();

        video_total.setText("共" + tx.getKeshi() + "课时");
        video_title.setText(tx.getStitle());
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(tx.getSys_hours());
        //简介
        video_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (introPop != null) {
                    introPop.showAsDropDown(frame_top);
                } else {
                    introPop = popWindow(R.layout.pop_intro);
                    introPop.setBackgroundDrawable(null);
                    introPop.setOutsideTouchable(true);
                    introPop.showAsDropDown(frame_top);
                    Gson gson = new Gson();
                    TextView tv_intro = (TextView) introPop.getContentView().findViewById(R.id.tv_intro);
                    tv_intro.setText(gson.fromJson(json, ProDeatailBean.class).getTx().getIntroduce());
                    introPop.getContentView().findViewById(R.id.intro_back).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            introPop.dismiss();

                        }
                    });
                }

            }
        });

        //问答
        makequestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuestionListActivity.class);
                intent.putExtra("videoId", MyApplication.videoitemid);
                intent.putExtra("name",  MyApplication.videoname);
                intent.putExtra("sid", sid);
                intent.putExtra("pid", MyApplication.videotypeid);
                startActivity(intent);
//                if (listPop != null) {
//                    listPop.showAsDropDown(frame_top);
//                } else {
//                    listPop = popWindow(R.layout.pop_question);
//                    listPop.setBackgroundDrawable(null);
//                    listPop.setOutsideTouchable(true);
//                    listPop.showAsDropDown(frame_top);
//                    listPop.getContentView().findViewById(R.id.ques_back).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            listPop.dismiss();
//                        }
//                    });
//                init(listPop.getContentView());
//                    setListener();
//                getData();
//                    Floatmakequestion.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            quesFlg=true;
//                            Intent intent = new Intent(getContext(), SendQuestionActivity.class);
//                            intent.putExtra("type", "1");
//                            startActivityForResult(intent, 1);
//                        }
//                    });
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quespop = popWindow(R.layout.pop_quesdetail);
//                            quespop.showAsDropDown(frame_top);
//                            quespop.getContentView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    quespop.dismiss();
//                                }
//                            });
//                            Mylv = (MyListView) quespop.getContentView().findViewById(R.id.videoquestion);
//                            iv = (ImageView) quespop.getContentView().findViewById(R.id.iv);
//                            title = (TextView) quespop.getContentView().findViewById(R.id.questionTitle);
//                            questioner = (TextView) quespop.getContentView().findViewById(R.id.questioner);
//                            time = (TextView) quespop.getContentView().findViewById(R.id.time);
//                            noreply = (TextView) quespop.getContentView().findViewById(R.id.noreply);
//                            rl_bot = (TextView) quespop.getContentView().findViewById(R.id.rl_bot);
//                            final ImageView dz = (ImageView) quespop.getContentView().findViewById(R.id.dz);
//                            dz.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    OkHttpDownloadJsonUtil.downloadJson(getContext(), "https://shizhanzhe.com/index.php?m=pcdata.dozan&qid=" + qid, new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                                        @Override
//                                        public void onsendJson(String json) {
//                                            if (json.contains("1")) {
//                                                dz.setImageResource(R.drawable.dz_yes);
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                            qid = list.get(position).getId();
//                            detail_load = (ProgressBar) quespop.getContentView().findViewById(R.id.detail_load);
//                            Mylv.setFocusable(false);
//                            getQUData(qid);
//                            rl_bot.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (uid.contains(bean.getQinfo().get(0).getUid()) || teacher.contains("1")) {
//                                        quesdetaFlg = true;
//                                        Intent intent = new Intent(getContext(), SendQuestionActivity.class);
//                                        intent.putExtra("type", "回复");
//                                        intent.putExtra("coid", bean.getQinfo().get(0).getCoid());
//                                        intent.putExtra("uid", bean.getQinfo().get(0).getUid());
//                                        intent.putExtra("qid", qid);
//                                        startActivity(intent);
//                                    } else {
//                                        Toast.makeText(getContext(), "无权限回复!", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//                        }
//
//                    });
//
//                }
//            }


            }
        });
    }

    String qid;
    MyListView Mylv;
    ImageView iv;
    TextView title;
    TextView questioner;
    TextView time;
    TextView noreply;
    TextView rl_bot;
    ProgressBar detail_load;

    boolean quesFlg = false;
    boolean quesdetaFlg = false;
    PopupWindow introPop;
    PopupWindow listPop;
    PopupWindow quespop;
    int page = 1;
    List<QuestionListBean> list;
    QuestionListAdapter adapter;

    void getData() {
        load.setVisibility(View.VISIBLE);
        OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).QUESTIONLIST("", MyApplication.videoitemid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                list = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
                }.getType());
                if (list.size() > 0) {
                    nodata.setVisibility(View.GONE);
                    adapter = new QuestionListAdapter(getContext(), list, stitle);
                    lv.setAdapter(adapter);
                } else {
                    nodata.setVisibility(View.VISIBLE);
                }
                load.setVisibility(View.GONE);
            }
        });
    }

    RefreshLayout swipeLayout;
    TextView nodata;
    ListView lv;
    FloatingActionButton Floatmakequestion;
    ProgressBar load;

    /**
     * 初始化布局
     */
    @SuppressLint({"InlinedApi", "InflateParams"})
    private void init(View view) {
        swipeLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.commom_sline_color_gray, R.color.blue2, R.color.red, R.color.green);
        nodata = (TextView) view.findViewById(R.id.nodata);
        lv = (ListView) view.findViewById(R.id.questionlist);
        Floatmakequestion = (FloatingActionButton) view.findViewById(R.id.makequestion);
        load = (ProgressBar) view.findViewById(R.id.load);
    }

//    /**
//     * 设置监听
//     */
//    private void setListener() {
//        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setOnLoadListener(this);
//    }
//
//    @Override
//    public void onRefresh() {
//        swipeLayout.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
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
//                OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).QUESTIONLIST("1", MyApplication.videoitemid, page), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                    @Override
//                    public void onsendJson(String json) {
//                        Gson gson = new Gson();
//                        List<QuestionListBean> lists = gson.fromJson(json, new TypeToken<List<QuestionListBean>>() {
//                        }.getType());
//                        if (lists.size() == 0) {
//                            Toast.makeText(getContext(), "已经到底了", Toast.LENGTH_SHORT).show();
//                        } else {
//                            for (QuestionListBean b :
//                                    lists) {
//                                list.add(b);
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//            }
//        }, 2000);
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (quesFlg) {
//            quesFlg = false;
//            onRefresh();
//        }
//        if (quesdetaFlg) {
//            quesdetaFlg=false;
//            getQUData(qid);
//        }
//    }

    QuestionBean bean;

    void getQUData(String qid) {
        detail_load.setVisibility(View.VISIBLE);
        OkHttpDownloadJsonUtil.downloadJson(getContext(), Path.QUESTIONDETAIL(qid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
            @Override
            public void onsendJson(String json) {
                Gson gson = new Gson();
                bean = gson.fromJson(json, QuestionBean.class);
                title.setText(bean.getQinfo().get(0).getAskquiz());
                ImageLoader.getInstance().displayImage(bean.getQinfo().get(0).getLogo(), iv);
                questioner.setText(bean.getQinfo().get(0).getRealname());
                time.setText(bean.getQinfo().get(0).getInputtime());
                List<QuestionBean.ReplyBean> replylist = bean.getReply();
                if (replylist.size() > 0) {
                    noreply.setVisibility(View.GONE);
                    QuestionReplyAdapter adapter = new QuestionReplyAdapter(getActivity(), replylist);
                    Mylv.setAdapter(adapter);
                } else {
                    noreply.setVisibility(View.VISIBLE);
                }
                detail_load.setVisibility(View.GONE);
            }
        });
    }

    PopupWindow popWindow(int resource) {
        LayoutInflater tTempLayout = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = tTempLayout.inflate(resource, null);
        PopupWindow Pop = new PopupWindow(getContext());
        Pop.setWidth(ll_question.getWidth());
        Pop.setHeight(ll_question.getHeight());
        Pop.setBackgroundDrawable(new ColorDrawable(0xffffff));
        Pop.setContentView(v);
        return Pop;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 11) {
//            OkHttpDownloadJsonUtil.downloadJson(getContext(), new Path(getContext()).ANSWERQUESTION(bean.getQinfo().get(0).getCoid(), bean.getQinfo().get(0).getUid(), data.getStringExtra("msg"), qid), new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
//                @Override
//                public void onsendJson(String json) {
//                    if (json.contains("1")) {
//                        getQUData(qid);
//                        Toast.makeText(getContext(), "回复成功！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "回复失败，请重试！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
}
