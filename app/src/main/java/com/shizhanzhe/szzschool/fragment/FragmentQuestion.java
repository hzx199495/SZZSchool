package com.shizhanzhe.szzschool.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shizhanzhe.szzschool.Bean.QuestionCenterBean;
import com.shizhanzhe.szzschool.Bean.QuestionProBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.DetailActivity;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.QuestionBaseActivity;
import com.shizhanzhe.szzschool.adapter.QuestionCenterAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by hk on 2017/7/31.
 */
@ContentView(R.layout.fragment_question)
public class FragmentQuestion extends Fragment {
    private TextView tv3;
    private String con;
    private MyGridView lv;
    //    private StateLayout state_layout;
    @ViewInject(R.id.empty)
    QMUIEmptyView empty;
    @ViewInject(R.id.scroll)
    ScrollView scroll;
    private QMUITipDialog dialog;
    LinearLayout ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (MyGridView) view.findViewById(R.id.questioncenter_lv);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        dialog = new QMUITipDialog.Builder(getContext()).setIconType(1).setTipWord("正在加载").create();
        ll = (LinearLayout) view.findViewById(R.id.ll);
//        state_layout.showLoadingView();
//        state_layout.setRefreshListener(new StateLayout.OnViewRefreshListener() {
//            @Override
//            public void refreshClick() {
//                state_layout.showLoadingView();
//                initData();
//            }
//
//            @Override
//            public void loginClick() {
//
//            }
//        });

        initData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {
                    try {
                        Gson gson = new Gson();
                        List<QuestionProBean> list = gson.fromJson(con, new TypeToken<List<QuestionProBean>>() {
                        }.getType());
                        QuestionProBean questionProBean = list.get(position);
                        SharedPreferences preferences = getActivity().getSharedPreferences("userjson", Context.MODE_PRIVATE);
                        String vip = preferences.getString("vip", "");
                        if (vip.equals("1")) {
                            Intent intent = new Intent(getActivity(), QuestionBaseActivity.class);
                            intent.putExtra("bean", questionProBean);
                            startActivity(intent);
                        } else {
                            if (questionProBean.getChavideo().get(0).getVideo() == null) {
                                final String sid = list.get(position).getSid();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("无权限")
                                        .setMessage("未购买该体系,是否前往购买")
                                        .setPositiveButton("立即前往", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setClass(getActivity(), DetailActivity.class);
                                                intent.putExtra("id", sid);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create().show();
                            } else {
                                Intent intent = new Intent(getActivity(), QuestionBaseActivity.class);
                                intent.putExtra("bean", questionProBean);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });
    }

    private void init(String num) {
        String s = String.format(this.getResources().getString(R.string.question_activity_text_3), num, "24");
        Spannable span = new SpannableString(s);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 12, 12 + num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv3.setText(span);
    }

    private void initData() {
        dialog.show();
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.QUESTION_NUMBER_PATH(),
                new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        init(json);
                        toContent();
                    }
                });
    }

    private void toContent() {

        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getActivity()).QUESTION_CONTENT_PATH(),
                new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            if (json.equals("0")) {
                                dialog.dismiss();
                                empty.show(false, "", "网络异常", "重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        initData();
                                    }
                                });
                                return;
                            } else if (json.equals("1")) {
                                dialog.dismiss();
                                empty.show(false, "", "网络超时", "重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        initData();
                                    }
                                });
                                return;
                            }
                            con = json;
                            Gson gson = new Gson();
                            List<QuestionCenterBean> list = gson.fromJson(json, new TypeToken<List<QuestionCenterBean>>() {
                            }.getType());
                            lv.setAdapter(new QuestionCenterAdapter(getActivity(), list));
                            dialog.dismiss();
                            scroll.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            dialog.dismiss();
                            empty.show(false, "", "数据异常", "重试", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initData();
                                }
                            });
                        }

                    }
                });
    }
}
