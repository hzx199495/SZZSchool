package com.shizhanzhe.szzschool.fragment;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shizhanzhe.szzschool.Bean.QuestionCenterBean;
import com.shizhanzhe.szzschool.R;
import com.shizhanzhe.szzschool.activity.LoginActivity;
import com.shizhanzhe.szzschool.activity.MyApplication;
import com.shizhanzhe.szzschool.activity.QuestionBaseActivity;
import com.shizhanzhe.szzschool.adapter.QuestionCenterAdapter;
import com.shizhanzhe.szzschool.utils.MyGridView;
import com.shizhanzhe.szzschool.utils.OkHttpDownloadJsonUtil;
import com.shizhanzhe.szzschool.utils.Path;

import org.json.JSONArray;
import org.json.JSONException;
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
    TextView tv3;
    String con;
    MyGridView lv;
    SVProgressHUD mSVProgressHUD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("加载中...");
        return x.view().inject(this, inflater, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (MyGridView) view.findViewById(R.id.questioncenter_lv);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        mSVProgressHUD.show();
        initData();
        toContent();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {
                    try {
                        JSONArray arr = null;
                        arr = new JSONArray(con);
                        String str=arr.optJSONObject(position).toString();
                        JSONObject obj = new JSONObject(str);
                        JSONArray a = obj.getJSONArray("chavideo");
                        if(a.optJSONObject(0).optJSONArray("video")==null){
                            new SVProgressHUD(getContext()).showInfoWithStatus("未购买该体系,无法查看");
                        }else {
                            Intent intent = new Intent(getActivity(), QuestionBaseActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("con", str);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
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
        span.setSpan(new AbsoluteSizeSpan(70), 12, 12 + num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 12, 12 + num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(70), 21 + num.length(), 23 + num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 21 + num.length(), 23 + num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv3.setText(span);
    }

    private void initData() {
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), Path.QUESTION_NUMBER_PATH(),
                new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        init(json);
                    }
                });
    }

    private void toContent() {
        OkHttpDownloadJsonUtil.downloadJson(getActivity(), new Path(getActivity()).QUESTION_CONTENT_PATH(),
                new OkHttpDownloadJsonUtil.onOkHttpDownloadListener() {
                    @Override
                    public void onsendJson(String json) {
                        try {
                            con = json;
                            Gson gson = new Gson();
                            List<QuestionCenterBean> list = gson.fromJson(json, new TypeToken<List<QuestionCenterBean>>() {
                            }.getType());
                            lv.setAdapter(new QuestionCenterAdapter(getActivity(), list));
                            mSVProgressHUD.dismiss();
                        } catch (Exception e) {
                        }

                    }
                });
    }
}
